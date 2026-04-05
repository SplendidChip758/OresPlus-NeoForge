package com.splendidchip.oresplus.block.entity.custom;

import com.splendidchip.oresplus.block.custom.ItemPipeBlock;
import com.splendidchip.oresplus.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ItemPipeBlockEntity extends BlockEntity {
    private static final String NBT_PREFIX = "connection_mode_";
    private static final int TRANSFER_INTERVAL = 8;
    
    public enum ConnectionMode implements net.minecraft.util.StringRepresentable {
        NONE, INPUT, OUTPUT;
        
        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
        
        public ConnectionMode next() {
            return switch (this) {
                case NONE -> INPUT;
                case INPUT -> OUTPUT;
                case OUTPUT -> NONE;
            };
        }
    }
    
    private final ConnectionMode[] modes = new ConnectionMode[6];
    private int transferCooldown = 0;
    
    public ItemPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_PIPE_BLOCK_ENTITY.get(), pos, state);
        for (int i = 0; i < 6; i++) {
            modes[i] = ConnectionMode.NONE;
        }
    }
    
    public ConnectionMode getConnectionMode(Direction direction) {
        return modes[direction.get3DDataValue()];
    }
    
    public void cycleConnectionMode(Direction direction) {
        modes[direction.get3DDataValue()] = getConnectionMode(direction).next();
        setChanged();
        syncInputVisualState();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        syncInputVisualState();
    }

    private void syncInputVisualState() {
        if (level == null || level.isClientSide()) {
            return;
        }

        BlockState state = getBlockState();
        if (!(state.getBlock() instanceof ItemPipeBlock)) {
            return;
        }

        boolean northConnected = isSideConnected(Direction.NORTH);
        boolean eastConnected = isSideConnected(Direction.EAST);
        boolean southConnected = isSideConnected(Direction.SOUTH);
        boolean westConnected = isSideConnected(Direction.WEST);
        boolean upConnected = isSideConnected(Direction.UP);
        boolean downConnected = isSideConnected(Direction.DOWN);

        BlockState updatedState = state
                .setValue(ItemPipeBlock.NORTH, northConnected)
                .setValue(ItemPipeBlock.EAST, eastConnected)
                .setValue(ItemPipeBlock.SOUTH, southConnected)
                .setValue(ItemPipeBlock.WEST, westConnected)
                .setValue(ItemPipeBlock.UP, upConnected)
                .setValue(ItemPipeBlock.DOWN, downConnected)
                .setValue(ItemPipeBlock.NORTH_INPUT,
                        northConnected && shouldShowInputOverlay(Direction.NORTH))
                .setValue(ItemPipeBlock.EAST_INPUT,
                        eastConnected && shouldShowInputOverlay(Direction.EAST))
                .setValue(ItemPipeBlock.SOUTH_INPUT,
                        southConnected && shouldShowInputOverlay(Direction.SOUTH))
                .setValue(ItemPipeBlock.WEST_INPUT,
                        westConnected && shouldShowInputOverlay(Direction.WEST))
                .setValue(ItemPipeBlock.UP_INPUT,
                        upConnected && shouldShowInputOverlay(Direction.UP))
                .setValue(ItemPipeBlock.DOWN_INPUT,
                        downConnected && shouldShowInputOverlay(Direction.DOWN));

        if (updatedState != state) {
            level.setBlock(worldPosition, updatedState, 3);
        }
    }

    private boolean shouldShowInputOverlay(Direction direction) {
        return getConnectionMode(direction) == ConnectionMode.INPUT && !isPipeNeighbor(direction);
    }

    private boolean isSideConnected(Direction direction) {
        if (level == null) {
            return false;
        }

        if (isPipeNeighbor(direction)) {
            return true;
        }

        if (getConnectionMode(direction) == ConnectionMode.NONE) {
            return false;
        }

        BlockPos neighborPos = worldPosition.relative(direction);
        BlockEntity neighborEntity = level.getBlockEntity(neighborPos);
        return neighborEntity instanceof WorldlyContainer || neighborEntity instanceof Container;
    }

    private boolean isPipeNeighbor(Direction direction) {
        if (level == null) {
            return false;
        }

        BlockPos neighborPos = worldPosition.relative(direction);
        return level.getBlockEntity(neighborPos) instanceof ItemPipeBlockEntity;
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        for (Direction direction : Direction.values()) {
            String key = NBT_PREFIX + direction.getName();
            tag.putString(key, modes[direction.get3DDataValue()].getSerializedName());
        }
    }
    
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        for (Direction direction : Direction.values()) {
            String key = NBT_PREFIX + direction.getName();
            if (tag.contains(key)) {
                try {
                    modes[direction.get3DDataValue()] = ConnectionMode.valueOf(tag.getString(key).toUpperCase());
                } catch (IllegalArgumentException ignored) {
                    modes[direction.get3DDataValue()] = ConnectionMode.NONE;
                }
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, ItemPipeBlockEntity pipeEntity) {
        if (level.isClientSide()) {
            return;
        }

        pipeEntity.transferCooldown--;
        if (pipeEntity.transferCooldown > 0) {
            return;
        }

        pipeEntity.transferCooldown = TRANSFER_INTERVAL;

        for (Direction inputDir : Direction.values()) {
            if (pipeEntity.getConnectionMode(inputDir) != ConnectionMode.INPUT) {
                continue;
            }

            BlockPos sourcePos = pos.relative(inputDir);
            BlockEntity sourceEntity = level.getBlockEntity(sourcePos);
            if (sourceEntity instanceof ItemPipeBlockEntity) {
                continue;
            }

            ExtractTarget extractTarget = findExtractTarget(sourceEntity, inputDir.getOpposite());
            if (extractTarget == null) {
                continue;
            }

            InsertTarget destination = findDestination(level, pos, sourcePos, extractTarget.previewStack());
            if (destination == null) {
                continue;
            }

            ItemStack extracted = extractOne(extractTarget);
            if (extracted.isEmpty()) {
                continue;
            }

            if (insertOne(destination, extracted)) {
                return;
            }

            restoreExtractedItem(extractTarget, extracted);
        }
    }

    @Nullable
    private static ExtractTarget findExtractTarget(@Nullable BlockEntity entity, Direction fromDirection) {
        if (entity == null || entity instanceof ItemPipeBlockEntity) {
            return null;
        }

        if (entity instanceof WorldlyContainer worldlyContainer) {
            for (int slot : worldlyContainer.getSlotsForFace(fromDirection)) {
                ItemStack stack = worldlyContainer.getItem(slot);
                if (!stack.isEmpty() && worldlyContainer.canTakeItemThroughFace(slot, stack, fromDirection)) {
                    return new ExtractTarget(entity, slot, fromDirection, stack.copyWithCount(1));
                }
            }
            return null;
        }

        if (entity instanceof Container container) {
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack stack = container.getItem(slot);
                if (!stack.isEmpty()) {
                    return new ExtractTarget(entity, slot, fromDirection, stack.copyWithCount(1));
                }
            }
        }

        return null;
    }

    @Nullable
    private static InsertTarget findDestination(Level level, BlockPos startPipePos, BlockPos sourceInventoryPos, ItemStack stack) {
        ItemPipeNetwork network = ItemPipeNetwork.from(level, startPipePos);

        for (ItemPipeNetwork.OutputEndpoint endpoint : network.collectOutputEndpoints(sourceInventoryPos)) {
            BlockEntity neighborEntity = level.getBlockEntity(endpoint.targetPos());
            InsertTarget destination = findInsertTarget(neighborEntity, stack, endpoint.insertionFace());
            if (destination != null) {
                return destination;
            }
        }

        return null;
    }

    @Nullable
    private static InsertTarget findInsertTarget(@Nullable BlockEntity entity, ItemStack stack, Direction fromDirection) {
        if (entity == null || entity instanceof ItemPipeBlockEntity) {
            return null;
        }

        ItemStack singleItem = stack.copyWithCount(1);

        if (entity instanceof WorldlyContainer worldlyContainer) {
            for (int slot : worldlyContainer.getSlotsForFace(fromDirection)) {
                ItemStack existing = worldlyContainer.getItem(slot);
                if (canInsertIntoSlot(existing, singleItem)
                        && worldlyContainer.canPlaceItem(slot, singleItem)
                        && worldlyContainer.canPlaceItemThroughFace(slot, singleItem, fromDirection)) {
                    return new InsertTarget(entity, slot, fromDirection);
                }
            }
            return null;
        }

        if (entity instanceof Container container) {
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack existing = container.getItem(slot);
                if (canInsertIntoSlot(existing, singleItem) && container.canPlaceItem(slot, singleItem)) {
                    return new InsertTarget(entity, slot, fromDirection);
                }
            }
        }

        return null;
    }

    private static boolean canInsertIntoSlot(ItemStack existing, ItemStack incoming) {
        return existing.isEmpty()
                || (ItemStack.isSameItemSameComponents(existing, incoming)
                && existing.getCount() < existing.getMaxStackSize());
    }

    private static ItemStack extractOne(ExtractTarget target) {
        if (!(target.entity() instanceof Container container)) {
            return ItemStack.EMPTY;
        }

        ItemStack current = container.getItem(target.slot());
        if (current.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (target.entity() instanceof WorldlyContainer worldlyContainer
                && !worldlyContainer.canTakeItemThroughFace(target.slot(), current, target.face())) {
            return ItemStack.EMPTY;
        }

        ItemStack extracted = container.removeItem(target.slot(), 1);
        target.entity().setChanged();
        return extracted;
    }

    private static boolean insertOne(InsertTarget target, ItemStack stack) {
        if (stack.isEmpty() || !(target.entity() instanceof Container container)) {
            return false;
        }

        ItemStack singleItem = stack.copyWithCount(1);

        if (target.entity() instanceof WorldlyContainer worldlyContainer
                && !worldlyContainer.canPlaceItemThroughFace(target.slot(), singleItem, target.face())) {
            return false;
        }

        if (!container.canPlaceItem(target.slot(), singleItem)) {
            return false;
        }

        ItemStack existing = container.getItem(target.slot());
        if (existing.isEmpty()) {
            container.setItem(target.slot(), singleItem);
            target.entity().setChanged();
            return true;
        }

        if (ItemStack.isSameItemSameComponents(existing, singleItem)
                && existing.getCount() < existing.getMaxStackSize()) {
            existing.grow(1);
            target.entity().setChanged();
            return true;
        }

        return false;
    }

    private static void restoreExtractedItem(ExtractTarget target, ItemStack stack) {
        if (stack.isEmpty() || !(target.entity() instanceof Container container)) {
            return;
        }

        ItemStack existing = container.getItem(target.slot());
        if (existing.isEmpty()) {
            container.setItem(target.slot(), stack);
            target.entity().setChanged();
            return;
        }

        if (ItemStack.isSameItemSameComponents(existing, stack)
                && existing.getCount() < existing.getMaxStackSize()) {
            existing.grow(stack.getCount());
            target.entity().setChanged();
        }
    }

    private record ExtractTarget(BlockEntity entity, int slot, Direction face, ItemStack previewStack) {
    }

    private record InsertTarget(BlockEntity entity, int slot, Direction face) {
    }
}

