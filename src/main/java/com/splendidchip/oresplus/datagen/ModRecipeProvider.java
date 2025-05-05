package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;

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
                .save(this.output, "aluminum_ingot_smelting");

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
