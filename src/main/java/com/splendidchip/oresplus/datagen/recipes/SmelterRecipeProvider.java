package com.splendidchip.oresplus.datagen.recipes;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.SmelterRecipeBuilder;
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

import java.util.concurrent.CompletableFuture;

public class SmelterRecipeProvider extends RecipeProvider {

    protected SmelterRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        new SmelterRecipeBuilder(
                new ItemStack(ModItems.PIG_IRON_INGOT.get()),
                Ingredient.of(ModItems.CRUSHED_HEMATITE),
                Ingredient.of(ModItems.LIMESTONE_DUST)
        )
                .cookTime(200)
                .experience(0.7f)
                .unlockedBy("has_crushed_hematite", has(ModItems.CRUSHED_HEMATITE))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "pig_iron_ingot_from_crushed_hematite")));

        new SmelterRecipeBuilder(
                new ItemStack(ModItems.PIG_IRON_INGOT.get()),
                Ingredient.of(ModItems.CRUSHED_MAGNETITE),
                Ingredient.of(ModItems.LIMESTONE_DUST)
        )
                .cookTime(200)
                .experience(0.7f)
                .unlockedBy("has_crushed_magnetite", has(ModItems.CRUSHED_MAGNETITE))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "pig_iron_ingot_from_crushed_magnetite")));

        new SmelterRecipeBuilder(
                new ItemStack(Items.IRON_INGOT),
                Ingredient.of(ModItems.PIG_IRON_INGOT)
        )
                .cookTime(200)
                .experience(0.7f)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        new SmelterRecipeBuilder(
                new ItemStack(ModItems.STEEL_INGOT.get()),
                Ingredient.of(Items.IRON_INGOT),
                Ingredient.of(ModItems.CARBON_DUST),
                Ingredient.of(ModItems.QUICK_LIME)
        )
                .cookTime(200)
                .experience(0.7f)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(this.output);

    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new SmelterRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Smelter Recipes";
        }
    }
}
