package com.splendidchip.oresplus.recipe.simpleKiln;

import com.splendidchip.oresplus.block.ModBlockItems;
import com.splendidchip.oresplus.recipe.ModRecipeBookCategory;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleKilnRecipe implements Recipe<SimpleKilnRecipeInput> {
    private final Ingredient inputItem;
    private final ItemStack result;
    private final int cookTime;

    private PlacementInfo info;

    public SimpleKilnRecipe(Ingredient inputItem, ItemStack result, int cookTime) {
        this.inputItem = inputItem;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(SimpleKilnRecipeInput input, Level level) {
        return this.inputItem.test(input.stack());
    }

    @Override
    public ItemStack assemble(SimpleKilnRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategory.SIMPLE_KILN_CATEGORY.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.info == null) {
            List<Optional<Ingredient>> ingredients = new ArrayList<>();
            ingredients.add(Optional.of(this.inputItem));
            this.info = PlacementInfo.createFromOptionals(ingredients);
        }
        return this.info;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new SimpleKilnRecipeDisplay(
                        this.inputItem.display(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(ModBlockItems.SIMPLE_KILN_BLOCK_ITEM.get())
                )
        );
    }

    public ItemStack getResult() {
        return result;
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public int getCookTime() {
        return cookTime;
    }

    @Override
    public RecipeType<? extends Recipe<SimpleKilnRecipeInput>> getType() {
        return ModRecipes.SIMPLE_KILN_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SimpleKilnRecipeInput>> getSerializer() {
        return ModRecipes.SIMPLE_KILN_SERIALIZER.get();
    }
}
