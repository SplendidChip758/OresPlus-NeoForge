package com.splendidchip.oresplus.recipe.brickMold;

import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrickMoldRecipe implements CraftingRecipe {

    private final List<Ingredient> inputItems;
    private final ItemStack result;

    private PlacementInfo placement;

    public BrickMoldRecipe(List<Ingredient> inputitems, ItemStack result){
        this.inputItems = inputitems;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> foundItems = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                foundItems.add(stack);
            }
        }

        List<Ingredient> expected = new ArrayList<>(inputItems);

        for (ItemStack stack : foundItems) {
            boolean matched = false;
            for (int i = 0; i < expected.size(); i++) {
                if (expected.get(i).test(stack)) {
                    expected.remove(i);
                    matched = true;
                    break;
                }
            }
            if (!matched) return false; // extra unexpected item
        }

        return expected.isEmpty(); // all ingredients used
    }


    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return result.copy();
    }

    public ItemStack getResult() {
        return result;
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remains = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() == ModItems.BRICK_MOLD.get()) {
                ItemStack copy = stack.copy();
                copy.setDamageValue(copy.getDamageValue() + 1);

                if (copy.getDamageValue() < copy.getMaxDamage()) {
                    remains.set(i, copy);
                } else {
                    remains.set(i, ItemStack.EMPTY); // it broke
                }
            }
        }

        return remains;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ModRecipes.BRICK_MOLD_SERIALIZER.get();
    }

    @Override
    public RecipeType<CraftingRecipe> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (placement == null) {
            List<Optional<Ingredient>> optionalIngredients = inputItems.stream()
                    .map(Optional::of)
                    .toList();
            placement = PlacementInfo.createFromOptionals(optionalIngredients);
        }
        return placement;
    }

    @Override
    public List<RecipeDisplay> display() {
        List<SlotDisplay> ingredientDisplays = inputItems.stream()
                .map(Ingredient::display)
                .toList();

        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        ingredientDisplays,
                        new SlotDisplay.ItemStackSlotDisplay(result),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }
}
