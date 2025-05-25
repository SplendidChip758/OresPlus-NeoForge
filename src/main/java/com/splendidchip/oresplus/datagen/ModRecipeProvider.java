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

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModBlocks.SMELTER_CONTROLLER_BLOCK.get())
                .pattern("XXX")
                .pattern("XYX")
                .pattern("XXX")
                .define('X', ModBlocks.REFRACTORY_BRICKS)
                .define('Y', Blocks.FURNACE)
                .unlockedBy("has_refractory_bricks", has(ModBlocks.REFRACTORY_BRICKS))
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

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_SWORD.get())
                .pattern("  X")
                .pattern(" X ")
                .pattern("Y  ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_PICKAXE.get())
                .pattern("XXX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_SHOVEL.get())
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_AXE.get())
                .pattern("XX ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_HOE.get())
                .pattern("XX ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
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
