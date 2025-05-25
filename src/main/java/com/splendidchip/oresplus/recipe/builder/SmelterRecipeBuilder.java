package com.splendidchip.oresplus.recipe.builder;

import com.splendidchip.oresplus.recipe.smelter.SmelterRecipe;
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
import java.util.Optional;

public class SmelterRecipeBuilder extends SimpleRecipeBuilder {
    private final Ingredient input1;
    private final Optional<Ingredient> input2;
    private final Optional<Ingredient> flux;
    private int cookTime = 200;
    private float experience = 0.0f;

    public SmelterRecipeBuilder(ItemStack result, Ingredient input1) {
        super(result);
        this.input1 = input1;
        this.input2 = Optional.empty();
        this.flux = Optional.empty();
    }

    public SmelterRecipeBuilder(ItemStack result, Ingredient input1, Ingredient flux) {
        super(result);
        this.input1 = input1;
        this.input2 = Optional.empty();
        this.flux = Optional.of(flux);
    }

    public SmelterRecipeBuilder(ItemStack result, Ingredient input1, Ingredient input2, Ingredient flux) {
        super(result);
        this.input1 = input1;
        this.input2 = Optional.of(input2);
        this.flux = Optional.of(flux);
    }

    public SmelterRecipeBuilder cookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public SmelterRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        SmelterRecipe recipe = new SmelterRecipe(this.input1, this.input2, this.flux, this.result, this.cookTime, this.experience);
        output.accept(key, recipe, advancement.build(key.location().withPrefix("recipes/")));
    }
}
