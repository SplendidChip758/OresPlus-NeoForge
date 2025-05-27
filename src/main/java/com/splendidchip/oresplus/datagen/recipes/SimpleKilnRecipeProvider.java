package com.splendidchip.oresplus.datagen.recipes;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.SimpleKilnRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class SimpleKilnRecipeProvider extends RecipeProvider {

    protected SimpleKilnRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        new SimpleKilnRecipeBuilder(
                new ItemStack((ItemLike) ModItems.REFRACTORY_BRICK),
                Ingredient.of(ModItems.UNFIRED_REFRACTORY_BRICK)
        )
                .cookTime(200)
                .experience(0.3f)
                .unlockedBy("has_unfired_refractory_brick", has(ModItems.UNFIRED_REFRACTORY_BRICK))
                .save(this.output);

        new SimpleKilnRecipeBuilder(
                new ItemStack(ModItems.COKE.get(), 2),
                Ingredient.of(Items.COAL)
        )
                .cookTime(200)
                .experience(0.2f)
                .unlockedBy("has_coal", has(Items.COAL))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "coke_from_coal")));

        new SimpleKilnRecipeBuilder(
                new ItemStack(ModItems.COKE.get(), 1),
                Ingredient.of(Items.CHARCOAL)
        )
                .cookTime(200)
                .experience(0.2f)
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "coke_from_charcoal")));

        new SimpleKilnRecipeBuilder(
                new ItemStack((ItemLike) ModItems.QUICK_LIME),
                Ingredient.of(ModItems.LIMESTONE_DUST)
        )
                .cookTime(300)
                .experience(0.2f)
                .unlockedBy("has_limestone_dust", has(ModItems.LIMESTONE_DUST))
                .save(this.output);

    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new SimpleKilnRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Simple Kiln Recipes";
        }
    }
    
}
