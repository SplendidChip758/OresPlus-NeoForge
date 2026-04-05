package com.splendidchip.oresplus.block.entity.custom;

import com.splendidchip.oresplus.block.custom.SmelterIOBlock;
import com.splendidchip.oresplus.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class SmelterIOBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider {

    public enum Mode implements net.minecraft.util.StringRepresentable {
        INPUT, OUTPUT;
        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }

    public SmelterIOBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMELTER_IO_BLOCK_ENTITY.get(), pos, state);
    }

    public Mode getMode() {
        return getBlockState().getValue(SmelterIOBlock.MODE);
    }

    public void toggleMode() {
        if (level != null) {
            BlockState state = getBlockState();
            Mode current = state.getValue(SmelterIOBlock.MODE);
            Mode next = (current == Mode.INPUT) ? Mode.OUTPUT : Mode.INPUT;
            level.setBlock(worldPosition, state.setValue(SmelterIOBlock.MODE, next), 3);
            setChanged();
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.oresplus.smelter_io_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    // Helper to find the controller in the 3x3x3 multiblock
    public SmelterControllerBlockEntity findController() {
        if (level == null) return null;
        for (int dx = -1; dx <= 1; dx++)
        for (int dy = -1; dy <= 1; dy++)
        for (int dz = -1; dz <= 1; dz++) {
            BlockEntity be = level.getBlockEntity(worldPosition.offset(dx, dy, dz));
            if (be instanceof SmelterControllerBlockEntity controller) return controller;
        }
        return null;
    }

    // --- WorldlyContainer passthrough ---
    @Override
    public int getContainerSize() {
        SmelterControllerBlockEntity controller = findController();
        return controller != null ? controller.getContainerSize() : 0;
    }

    @Override
    public boolean isEmpty() {
        SmelterControllerBlockEntity controller = findController();
        return controller == null || controller.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        SmelterControllerBlockEntity controller = findController();
        if (controller == null) return ItemStack.EMPTY;
        if (getMode() == Mode.INPUT && (slot == 0 || slot == 1)) {
            return controller.getItem(slot); // input slots
        } else if (getMode() == Mode.OUTPUT && slot == 4) {
            return controller.getItem(slot); // output slot
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        SmelterControllerBlockEntity controller = findController();
        if (controller == null) return ItemStack.EMPTY;
        if (getMode() == Mode.OUTPUT && slot == 4) {
            return controller.removeItem(slot, amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        SmelterControllerBlockEntity controller = findController();
        if (controller == null) return ItemStack.EMPTY;
        if (getMode() == Mode.OUTPUT && slot == 4) {
            return controller.removeItemNoUpdate(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        SmelterControllerBlockEntity controller = findController();
        if (controller == null) return;
        if (getMode() == Mode.INPUT && (slot == 0 || slot == 1)) {
            controller.setItem(slot, stack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        SmelterControllerBlockEntity controller = findController();
        return controller != null && controller.stillValid(player);
    }

    @Override
    public void clearContent() {
        SmelterControllerBlockEntity controller = findController();
        if (controller != null) controller.clearContent();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (getMode() == Mode.INPUT) {
            return new int[]{0, 1}; // input slots
        } else {
            return new int[]{4}; // output slot
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return getMode() == Mode.INPUT && (index == 0 || index == 1);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return getMode() == Mode.OUTPUT && index == 4;
    }
}
