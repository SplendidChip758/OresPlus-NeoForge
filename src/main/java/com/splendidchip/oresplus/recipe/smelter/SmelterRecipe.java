package com.splendidchip.oresplus.recipe.smelter;

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

public class SmelterRecipe implements Recipe<SmelterRecipeInput> {
    private final Ingredient input1;
    private final Optional<Ingredient> input2;
    private final ItemStack result;
    private final Optional<Ingredient> flux;
    private final float experience;
    private final int cookTime;

    private PlacementInfo info;

    public SmelterRecipe(Ingredient input1, Optional<Ingredient> input2, Optional<Ingredient> flux, ItemStack result, int cookTime, float experience) {
        this.input1 = input1;
        this.input2 = input2;
        this.flux = flux;
        this.result = result;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(SmelterRecipeInput input, Level level) {
        ItemStack stack1 = input.input1();
        ItemStack stack2 = input.input2();
        ItemStack fluxStack = input.flux();
        // 1. Handle single input, no flux
        if (input2.isEmpty() && flux.isEmpty()) {
            if (input1.test(stack1) && stack2.isEmpty()) return true;
            if (input1.test(stack2) && stack1.isEmpty()) return true;
        }
        // 2. Single input + flux
        if (input2.isEmpty() && flux.isPresent()) {
            boolean inputMatch = (input1.test(stack1) && stack2.isEmpty()) || (input1.test(stack2) && stack1.isEmpty());
            boolean fluxMatch = flux.get().test(fluxStack);
            return inputMatch && fluxMatch;
        }
        // 3. Two inputs, no flux
        if (input2.isPresent() && flux.isEmpty()) {
            boolean forward = input1.test(stack1) && input2.get().test(stack2);
            boolean reversed = input1.test(stack2) && input2.get().test(stack1);
            return forward || reversed;
        }
        // 4. Two inputs and flux
        if (input2.isPresent() && flux.isPresent()) {
            boolean forward = input1.test(stack1) && input2.get().test(stack2);
            boolean reversed = input1.test(stack2) && input2.get().test(stack1);
            return (forward || reversed) && flux.get().test(fluxStack);
        }
        return false;
    }

    @Override
    public ItemStack assemble(SmelterRecipeInput smelterRecipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SmelterRecipeInput>> getSerializer() {
        return ModRecipes.SMELTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SmelterRecipeInput>> getType() {
        return ModRecipes.SMELTER_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (info == null) {
            List<Optional<Ingredient>> ingredients = new ArrayList<>();
            ingredients.add(Optional.of(input1));
            ingredients.add(input2);
            ingredients.add(flux);
            info = PlacementInfo.createFromOptionals(ingredients);
        }
        return info;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new SmelterRecipeDisplay(
                        this.input1.display(),
                        this.input2.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE),
                        this.flux.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE),
                        new SlotDisplay.ItemStackSlotDisplay(result),
                        new SlotDisplay.ItemSlotDisplay(ModBlockItems.SMELTER_CONTROLLER_BLOCK_ITEM.get())
                )
        );
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategory.SMELTER_CATEGORY.get();
    }

    public ItemStack getResult() {
        return result;
    }

    public Ingredient getInput1() {
        return input1;
    }

    public Optional<Ingredient> getInput2() {
        return input2;
    }

    public Optional<Ingredient> getFlux() {
        return flux;
    }

    public float getExperience() {
        return experience;
    }

    public int getCookTime() {
        return cookTime;
    }
}
