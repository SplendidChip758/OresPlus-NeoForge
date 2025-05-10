package com.splendidchip.oresplus.recipe;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeBookCategory {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES =
            DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, OresPlus.MOD_ID);

    public static final Supplier<RecipeBookCategory> GRINDER_CATEGORY = RECIPE_BOOK_CATEGORIES.register(
            "grinder_block",
            RecipeBookCategory::new
    );

    public static void register(IEventBus eventBus) {
        RECIPE_BOOK_CATEGORIES.register(eventBus);
    }

    public static final ExtendedRecipeBookCategory GRINDER_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};

}
