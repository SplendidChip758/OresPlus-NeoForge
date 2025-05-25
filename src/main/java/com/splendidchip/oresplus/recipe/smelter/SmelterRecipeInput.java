package com.splendidchip.oresplus.recipe.smelter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SmelterRecipeInput(ItemStack input1, ItemStack input2, ItemStack flux) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> input1;
            case 1 -> input2;
            case 2 -> flux;
            default -> throw new IllegalArgumentException("No item for index " + slot);
        };
    }

    @Override
    public int size() {
        return 3;
    }
}
