package com.splendidchip.oresplus.recipe;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipe;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipeDisplay;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipeSerializer;
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

    //Crusher
    public static final Supplier<RecipeSerializer<CrusherRecipe>> CRUSHER_SERIALIZER =
            RECIPE_SERIALIZERS.register("crusher_block", CrusherRecipeSerializer::new);

    public static final Supplier<RecipeType<CrusherRecipe>> CRUSHER_TYPE =
            RECIPE_TYPES.register("crushing", registryName -> new RecipeType<CrusherRecipe>() {
                @Override
                public String toString() {
                    return registryName.toString();
                }
            });

    public static final Supplier<RecipeDisplay.Type<CrusherRecipeDisplay>> CRUSHER_RECIPE_DISPLAY = RECIPE_DISPLAY_TYPES.register(
            "crusher_block",
            () -> new RecipeDisplay.Type<>(CrusherRecipeDisplay.MAP_CODEC, CrusherRecipeDisplay.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        RECIPE_DISPLAY_TYPES.register(eventBus);
    }
}
