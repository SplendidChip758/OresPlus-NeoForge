package com.splendidchip.oresplus.datagen.recipes;

import com.splendidchip.oresplus.datagen.ModRecipeProvider;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.BrickMoldRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BrickMoldRecipeProvider extends RecipeProvider {

    protected BrickMoldRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        new BrickMoldRecipeBuilder(
                new ItemStack(ModItems.UNFIRED_REFRACTORY_BRICK.get()),
                List.of(
                        Ingredient.of(ModItems.REFRACTORY_CEMENT),
                        Ingredient.of(ModItems.REFRACTORY_CEMENT),
                        Ingredient.of(ModItems.BRICK_MOLD)
                )
        )
                .unlockedBy("has_refractory_cement", has(ModItems.REFRACTORY_CEMENT))
                .unlockedBy("has_brick_mold", has(ModItems.BRICK_MOLD))
                .save(this.output);

    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new BrickMoldRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Brick Mold Recipes";
        }
    }
    
}
