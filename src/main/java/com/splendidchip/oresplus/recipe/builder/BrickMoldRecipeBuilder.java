package com.splendidchip.oresplus.recipe.builder;

import com.splendidchip.oresplus.recipe.brickMold.BrickMoldRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class BrickMoldRecipeBuilder extends SimpleRecipeBuilder {
    private final List<Ingredient> ingredients;

    public BrickMoldRecipeBuilder(ItemStack result, List<Ingredient> ingredients) {
        super(result);
        this.ingredients = ingredients;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        BrickMoldRecipe recipe = new BrickMoldRecipe(this.ingredients, this.result);
        output.accept(key, recipe, advancement.build(key.location().withPrefix("recipes/")));
    }
}

