package com.splendidchip.oresplus.datagen.recipes;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.CrusherRecipeBuilder;
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

public class CrusherRecipeProvider extends RecipeProvider {

    protected CrusherRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.CRUSHED_BAUXITE, 2),
                Ingredient.of(ModItems.RAW_BAUXITE)
        )
                .unlockedBy("has_raw_bauxite", has(ModItems.RAW_BAUXITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.SALT, 2),
                Ingredient.of(ModItems.RAW_SALT)
        )
                .unlockedBy("has_raw_salt", has(ModItems.RAW_SALT))
                .save(this.output);

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.CRUSHED_HEMATITE, 2),
                Ingredient.of(ModItems.RAW_HEMATITE)
        )
                .unlockedBy("has_raw_hematite", has(ModItems.RAW_HEMATITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.CRUSHED_MAGNETITE, 2),
                Ingredient.of(ModItems.RAW_MAGNETITE)
        )
                .unlockedBy("has_raw_magnetite", has(ModItems.RAW_MAGNETITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.LIMESTONE_DUST, 4),
                Ingredient.of(ModBlocks.LIMESTONE_BLOCK)
        )
                .unlockedBy("has_limestone_block", has(ModItems.LIMESTONE_DUST))
                .save(this.output);

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.CARBON_DUST, 2),
                Ingredient.of(Items.COAL)
        )
                .unlockedBy("has_coal", has(Items.COAL))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "carbon_dust_from_coal")));

        new CrusherRecipeBuilder(
                new ItemStack((ItemLike) ModItems.CARBON_DUST, 2),
                Ingredient.of(Items.CHARCOAL)
        )
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "carbon_dust_from_charcoal")));
    }


    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new CrusherRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Crusher Recipes";
        }
    }
}
