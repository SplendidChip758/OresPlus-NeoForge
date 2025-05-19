package com.splendidchip.oresplus.block.entity;

import com.splendidchip.oresplus.recipe.ModRecipes;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipe;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipeInput;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipe;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipeInput;
import com.splendidchip.oresplus.screen.custom.SimpleKilnMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SimpleKilnBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final ContainerData data;
    private int burnTime = 0;
    private int cookTime = 0;
    private int cookTimeTotal = 200;

    public SimpleKilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIMPLE_KILN_BLOCK_ENTITY.get(), pos, state);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SimpleKilnBlockEntity.this.burnTime;
                    case 1 -> SimpleKilnBlockEntity.this.cookTime;
                    case 2 -> SimpleKilnBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SimpleKilnBlockEntity.this.burnTime = value;
                    case 1 -> SimpleKilnBlockEntity.this.cookTime = value;
                    case 2 -> SimpleKilnBlockEntity.this.cookTimeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oresplus.simple_kiln_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SimpleKilnMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("burnTime", burnTime);
        tag.putInt("cookTime", cookTime);
        tag.putInt("cookTimeTotal", cookTimeTotal);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        burnTime = tag.getInt("burnTime");
        cookTime = tag.getInt("cookTime");
        cookTimeTotal = tag.getInt("cookTimeTotal");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SimpleKilnBlockEntity blockEntity) {
        boolean isBurning = blockEntity.burnTime > 0;
        boolean hasChanged = false;

        if (blockEntity.burnTime > 0) {
            blockEntity.burnTime--;
        }

        Optional<RecipeHolder<SimpleKilnRecipe>> recipe = blockEntity.getCurrentRecipe();

        if (blockEntity.burnTime > 0 && recipe.isPresent() && blockEntity.hasRecipe()) {
            ItemStack fuelStack = blockEntity.itemHandler.getStackInSlot(FUEL_SLOT);
            if (fuelStack.getItem() == Items.COAL || fuelStack.getItem() == Items.CHARCOAL) {
                blockEntity.burnTime = 200;
                fuelStack.shrink(1);
                hasChanged = true;
            }
        }

        if (blockEntity.burnTime > 0 && recipe.isPresent() && blockEntity.hasRecipe()) {
            RecipeHolder<SimpleKilnRecipe> holder = recipe.get();
            blockEntity.cookTimeTotal = holder.value().getCookTime();

            blockEntity.cookTime++;
            if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                blockEntity.cookTime = 0;
                blockEntity.craftItem();
                hasChanged = true;
            }
        }

        if (hasChanged) {
            setChanged(level, pos, state);
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<SimpleKilnRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().getResult();

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<SimpleKilnRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResult();
        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output)
                && !itemHandler.getStackInSlot(INPUT_SLOT).isEmpty();
    }

    private Optional<RecipeHolder<SimpleKilnRecipe>> getCurrentRecipe() {
        return level.getServer().getRecipeManager()
                .getRecipeFor(ModRecipes.SIMPLE_KILN_TYPE.get(), new SimpleKilnRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
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

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT};
        } else if (side == Direction.UP) {
            return new int[]{INPUT_SLOT};
        } else {
            return new int[]{INPUT_SLOT};
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return index == INPUT_SLOT;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == OUTPUT_SLOT;
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = itemHandler.extractItem(slot, amount, false);
        setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
