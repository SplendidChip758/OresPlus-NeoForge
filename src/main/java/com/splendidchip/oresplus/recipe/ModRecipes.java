package com.splendidchip.oresplus.recipe;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.recipe.grinder.GrinderRecipe;
import com.splendidchip.oresplus.recipe.grinder.GrinderRecipeDisplay;
import com.splendidchip.oresplus.recipe.grinder.GrinderRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, OresPlus.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, OresPlus.MOD_ID);

    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAY_TYPES =
            DeferredRegister.create(Registries.RECIPE_DISPLAY, OresPlus.MOD_ID);


    public static final Supplier<RecipeSerializer<GrinderRecipe>> GRINDER_SERIALIZER =
            RECIPE_SERIALIZERS.register("grinder_block", GrinderRecipeSerializer::new);

    public static final Supplier<RecipeType<GrinderRecipe>> GRINDER_TYPE =
            RECIPE_TYPES.register("grinding", registryName -> new RecipeType<GrinderRecipe>() {
                @Override
                public String toString() {
                    return registryName.toString();
                }
            });

    public static final Supplier<RecipeDisplay.Type<GrinderRecipeDisplay>> GRINDER_RECIPE_DISPLAY = RECIPE_DISPLAY_TYPES.register(
            "grinder_block",
            () -> new RecipeDisplay.Type<>(GrinderRecipeDisplay.MAP_CODEC, GrinderRecipeDisplay.STREAM_CODEC)
    );


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        RECIPE_DISPLAY_TYPES.register(eventBus);
    }
}
