package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.recipe.builder.BrickMoldRecipeBuilder;
import com.splendidchip.oresplus.recipe.builder.CrusherRecipeBuilder;
import com.splendidchip.oresplus.recipe.builder.SimpleKilnRecipeBuilder;
import com.splendidchip.oresplus.recipe.builder.SmelterRecipeBuilder;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.BRICK_MOLD.get())
                .pattern("XXX")
                .pattern("X X")
                .pattern("XXX")
                .define('X', Items.STICK)
                .unlockedBy("has_stick", has(Items.STICK)).save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.CRUSHER_BLOCK.get())
                .pattern("X X")
                .pattern("Y Y")
                .pattern("XXX")
                .define('X', Blocks.COBBLESTONE)
                .define('Y', Blocks.GRINDSTONE)
                .unlockedBy("has_grindstone", has(Items.GRINDSTONE))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.SIMPLE_KILN_BLOCK.get())
                .pattern("XXX")
                .pattern("XYX")
                .pattern("ZZZ")
                .define('X', ModBlocks.LIMESTONE_BLOCK)
                .define('Y', Blocks.FURNACE)
                .define('Z', Blocks.COBBLESTONE)
                .unlockedBy("has_limestone_block", has(ModBlocks.LIMESTONE_BLOCK))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.SIMPLE_SMELTER_BLOCK.get())
                .pattern("X X")
                .pattern("XYX")
                .pattern("ZZZ")
                .define('X', ModItems.REFRACTORY_BRICK)
                .define('Y', Blocks.FURNACE)
                .define('Z', Blocks.COBBLESTONE)
                .unlockedBy("has_refractory_bricks", has(ModItems.REFRACTORY_BRICK))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.SMELTER_CONTROLLER_BLOCK.get())
                .pattern("XZX")
                .pattern("ZYZ")
                .pattern("XZX")
                .define('X', ModItems.REFRACTORY_BRICK)
                .define('Y', Blocks.FURNACE)
                .define('Z', ModItems.PIG_IRON_INGOT)
                .unlockedBy("has_refractory_bricks", has(ModItems.REFRACTORY_BRICK))
                .save(this.output);

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

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.UPGRADE_MODULE_CASING.get())
                .pattern("XYX")
                .pattern("Y Y")
                .pattern("XYX")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.REDSTONE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.SMELTER_UPGRADE_CORE.get())
                .pattern("XYX")
                .pattern("YZY")
                .pattern("XYX")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.REDSTONE)
                .define('Z', Items.BLAZE_POWDER)
                .unlockedBy("has_refractory_brick", has(ModItems.REFRACTORY_BRICK))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.SMELTER_UPGRADE_MODULE.get())
                .requires(ModItems.UPGRADE_MODULE_CASING)
                .requires(ModItems.SMELTER_UPGRADE_CORE)
                .unlockedBy("has_refractory_brick", has(ModItems.REFRACTORY_BRICK))
                .save(this.output);

        // Steel Armor
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.STEEL_HELMET.get())
                .pattern("XXX")
                .pattern("X X")
                .define('X', ModItems.STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.STEEL_CHESTPLATE.get())
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ModItems.STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.STEEL_LEGGINGS.get())
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .define('X', ModItems.STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.COMBAT, ModItems.STEEL_BOOTS.get())
                .pattern("X X")
                .pattern("X X")
                .define('X', ModItems.STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Recipes";
        }
    }
}
