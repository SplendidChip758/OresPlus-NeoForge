package com.splendidchip.oresplus.screen.custom;

import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.block.entity.SmelterControllerBlockEntity;
import com.splendidchip.oresplus.recipe.ModRecipes;
import com.splendidchip.oresplus.screen.ModMenuTypes;
import com.splendidchip.oresplus.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SmelterMenu extends AbstractContainerMenu {
    public final SmelterControllerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public SmelterMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public SmelterMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.SMELTER_MENU.get(), containerId);
        this.blockEntity = (SmelterControllerBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        // Slots: input1, input2, flux, fuel, output
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 44, 17)); // input 1
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 68, 17)); // input 2
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 26, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.FLUXES);
            }
        }); // flux
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 3, 56, 53){  // fuel
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getBurnTime(ModRecipes.SIMPLE_KILN_TYPE.get(), level.fuelValues()) > 0;
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 4, 116, 35){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                if (player instanceof ServerPlayer serverPlayer) {
                    blockEntity.awardUsedRecipesAndPopExperience(serverPlayer);
                }
            }
        });

        addDataSlots(data);
    }

    public int getLitProgress() {
        int burnTime = data.get(0);
        int burnTimeTotal = data.get(3);
        return burnTimeTotal == 0 ? 0 : burnTime * 13 / burnTimeTotal;
    }

    public int getScaledProgress() {
        int cookTime = data.get(1);
        int cookTimeTotal = data.get(2);
        return cookTimeTotal == 0 ? 0 : cookTime * 24 / cookTimeTotal;
    }

    public int getBurnTime() {
        return data.get(0);
    }

    public int getBurnTimeTotal() {
        return data.get(3);
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 5;

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.SMELTER_CONTROLLER_BLOCK.get());
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }
}
