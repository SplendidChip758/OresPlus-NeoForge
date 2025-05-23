package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.BrickMoldRecipeBuilder;
import com.splendidchip.oresplus.recipe.builder.CrusherRecipeBuilder;
import com.splendidchip.oresplus.recipe.builder.SimpleKilnRecipeBuilder;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
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

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.REFRACTORY_CEMENT.get())
                .requires(ModItems.CRUSHED_BAUXITE)
                .requires(ModItems.LIMESTONE_DUST)
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_crushed_bauxite", this.has(ModItems.CRUSHED_BAUXITE))
                .unlockedBy("has_limestone_dust", this.has(ModItems.LIMESTONE_DUST))
                .unlockedBy("has_clay_ball", this.has(Items.CLAY_BALL))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.REFRACTORY_BRICKS.get())
                .pattern("XX ")
                .pattern("XX ")
                .define('X', ModItems.REFRACTORY_BRICK)
                .unlockedBy("has_refractory_brick", has(ModItems.REFRACTORY_BRICK))
                .save(this.output);

        //Cooking/Smelting
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

        //Custom Recipe
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
