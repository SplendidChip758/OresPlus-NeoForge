package com.splendidchip.oresplus.block.entity;

import com.splendidchip.oresplus.block.custom.ItemPipeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemPipeBlockEntity extends BlockEntity {
    private int cooldown = 0;

    public ItemPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_PIPE_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ItemPipeBlockEntity entity) {
        if (level.isClientSide()) return;
        if (entity.cooldown > 0) {
            entity.cooldown--;
            return;
        }
        Direction dir = state.getValue(ItemPipeBlock.FACING);
        BlockEntity fromBE = level.getBlockEntity(pos.relative(dir.getOpposite()));
        BlockEntity toBE = level.getBlockEntity(pos.relative(dir));
        if (!(fromBE instanceof Container from && toBE instanceof Container to)) {
            return;
        }
        int[] fromSlots = getSlots(from, dir, true);
        for (int slot : fromSlots) {
            ItemStack stack = from.getItem(slot);
            if (stack.isEmpty()) continue;
            if (from instanceof WorldlyContainer wf && !wf.canTakeItemThroughFace(slot, stack, dir)) continue;
            ItemStack moved = stack.copy();
            moved.setCount(1);
            if (insertItem(to, moved, dir.getOpposite())) {
                stack.shrink(1);
                from.setChanged();
                entity.cooldown = 8;
                return;
            }
        }
    }

    private static int[] getSlots(Container container, Direction side, boolean extracting) {
        if (container instanceof WorldlyContainer world) {
            return world.getSlotsForFace(side);
        } else {
            int[] slots = new int[container.getContainerSize()];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
            return slots;
        }
    }

    private static boolean insertItem(Container to, ItemStack stack, Direction side) {
        int[] slots;
        if (to instanceof WorldlyContainer world) {
            slots = world.getSlotsForFace(side);
        } else {
            slots = new int[to.getContainerSize()];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
        }
        for (int slot : slots) {
            if (to instanceof WorldlyContainer world && !world.canPlaceItemThroughFace(slot, stack, side)) continue;
            ItemStack existing = to.getItem(slot);
            if (existing.isEmpty()) {
                to.setItem(slot, stack);
                to.setChanged();
                return true;
            } else if (ItemStack.isSameItemSameTags(existing, stack) && existing.getCount() < existing.getMaxStackSize()) {
                existing.grow(1);
                to.setChanged();
                return true;
            }
        }
        return false;
    }
}

