package com.splendidchip.oresplus.datagen.recipes;

import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ToolRecipeProvider extends RecipeProvider {
    protected ToolRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        //Pig Iron Tools
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

        //Steel Tools
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_SWORD.get())
                .pattern("  X")
                .pattern(" X ")
                .pattern("Y  ")
                .define('X', ModItems.STEEL_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_PICKAXE.get())
                .pattern("XXX")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.STEEL_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_SHOVEL.get())
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.STEEL_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_AXE.get())
                .pattern("XX ")
                .pattern("XY ")
                .pattern(" Y ")
                .define('X', ModItems.STEEL_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_HOE.get())
                .pattern("XX ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', ModItems.STEEL_INGOT)
                .define('Y', Items.STICK)
                .unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.PIG_IRON_WRENCH.get())
                .pattern("X X")
                .pattern(" X ")
                .pattern(" X ")
                .define('X', ModItems.PIG_IRON_INGOT)
                .unlockedBy("has_pig_iron_ingot", has(ModItems.PIG_IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_WRENCH.get())
                .pattern("X X")
                .pattern(" X ")
                .pattern(" X ")
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
            return new ToolRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "OresPlus Tool Recipes";
        }
    }
}
