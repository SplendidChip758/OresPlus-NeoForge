package com.splendidchip.oresplus.recipe.builder;

import com.splendidchip.oresplus.recipe.grinder.GrinderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class GrinderRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient inputItem;

    public GrinderRecipeBuilder(ItemStack result, Ingredient inputItem) {
        super(result);
        this.inputItem = inputItem;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        // Build the advancement.
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        // Our factory parameters are the result, the block state, and the ingredient.
        GrinderRecipe recipe = new GrinderRecipe(this.inputItem, this.result);
        // Pass the id, the recipe, and the recipe advancement into the RecipeOutput.
        output.accept(key, recipe, advancement.build(key.location().withPrefix("recipes/")));
    }
}