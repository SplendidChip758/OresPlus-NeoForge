package com.splendidchip.oresplus.recipe.builder;

import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class SimpleKilnRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient input;
    private int cookTime = 200;

    public SimpleKilnRecipeBuilder(ItemStack result, Ingredient input) {
        super(result);
        this.input = input;
    }

    public SimpleKilnRecipeBuilder cookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        SimpleKilnRecipe recipe = new SimpleKilnRecipe(this.input, this.result, this.cookTime);
        output.accept(key, recipe, advancement.build(key.location().withPrefix("recipes/")));
    }
}

