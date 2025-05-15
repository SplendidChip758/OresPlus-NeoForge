package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.CrusherRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.ALUMINUM_BLOCK.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModItems.ALUMINUM_INGOT.get())
                .unlockedBy("has_aluminum_ingot", has(ModItems.ALUMINUM_INGOT)).save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.CRUSHER_BLOCK.get())
                .pattern("X X")
                .pattern("XYX")
                .pattern("XXX")
                .define('X', Blocks.COBBLESTONE)
                .define('Y', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(this.output);

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.ALUMINA),
                RecipeCategory.MISC,
                ModItems.ALUMINUM_INGOT,
                0.25f,
                200)
                .unlockedBy("has_alumina", this.has(ModItems.ALUMINA))
                .save(this.output, "aluminum_ingot_smelting");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.ALUMINA),
                        RecipeCategory.MISC,
                        ModItems.ALUMINUM_INGOT,
                        0.25f,
                        100)
                .unlockedBy("has_alumina", this.has(ModItems.ALUMINA))
                .save(this.output, "aluminum_ingot_blasting");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(Items.COAL),
                        RecipeCategory.MISC,
                        ModItems.COKE,
                        0.25f,
                        100)
                .unlockedBy("has_coal", this.has(ModItems.COKE))
                .save(this.output, "coke_blasting");

        new CrusherRecipeBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack((ItemLike) ModItems.CRUSHED_BAUXITE, 2),
                Ingredient.of(ModItems.RAW_BAUXITE)
        )
                .unlockedBy("has_raw_bauxite", has(ModItems.RAW_BAUXITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack((ItemLike) ModItems.SALT, 2),
                Ingredient.of(ModItems.RAW_SALT)
        )
                .unlockedBy("has_raw_salt", has(ModItems.RAW_SALT))
                .save(this.output);

        new CrusherRecipeBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack((ItemLike) ModItems.CRUSHED_HEMATITE, 2),
                Ingredient.of(ModItems.RAW_HEMATITE)
        )
                .unlockedBy("has_raw_hematite", has(ModItems.RAW_HEMATITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack((ItemLike) ModItems.CRUSHED_MAGNETITE, 2),
                Ingredient.of(ModItems.RAW_MAGNETITE)
        )
                .unlockedBy("has_raw_magnetite", has(ModItems.RAW_MAGNETITE))
                .save(this.output);

        new CrusherRecipeBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack((ItemLike) ModItems.LIMESTONE_DUST, 4),
                Ingredient.of(ModBlocks.LIMESTONE_BLOCK)
        )
                .unlockedBy("has_limestone_block", has(ModItems.LIMESTONE_DUST))
                .save(this.output);

    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
