package com.splendidchip.oresplus.recipe.crusher;

import com.splendidchip.oresplus.recipe.ModRecipeBookCategory;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrusherRecipe implements Recipe<CrusherRecipeInput> {

    private final Ingredient inputItem;
    private final ItemStack result;

    private PlacementInfo info;

    public CrusherRecipe(Ingredient inputItem, ItemStack result) {
        this.inputItem = inputItem;
        this.result = result;

    }

    @Override
    public boolean matches(CrusherRecipeInput input, Level level) {
        return this.inputItem.test(input.stack());
    }

    @Override
    public ItemStack assemble(CrusherRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategory.CRUSHER_CATEGORY.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        // This delegate is in case the ingredient is not fully populated at this point in time
        // Tags and recipes are loaded at the same time, which is why this might be the case.
        if (this.info == null) {
            // Use optional ingredient as the block state may have an item representation
            List<Optional<Ingredient>> ingredients = new ArrayList<>();
            ingredients.add(Optional.of(this.inputItem));

            // Create placement info
            this.info = PlacementInfo.createFromOptionals(ingredients);
        }

        return this.info;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new CrusherRecipeDisplay(
                        this.inputItem.display(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(Items.GRASS_BLOCK)
                )
        );
    }

    @Override
    public RecipeType<? extends Recipe<CrusherRecipeInput>> getType() {
        return ModRecipes.CRUSHER_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<CrusherRecipeInput>> getSerializer() {
        return ModRecipes.CRUSHER_SERIALIZER.get();
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public ItemStack getResult() {
        return result;
    }

}
