package com.splendidchip.oresplus.recipe;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.recipe.brickMold.BrickMoldRecipe;
import com.splendidchip.oresplus.recipe.brickMold.BrickMoldRecipeSerializer;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipe;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipeDisplay;
import com.splendidchip.oresplus.recipe.crusher.CrusherRecipeSerializer;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipe;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipeDisplay;
import com.splendidchip.oresplus.recipe.simpleKiln.SimpleKilnRecipeSerializer;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipe;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipeDisplay;
import com.splendidchip.oresplus.recipe.smelter.SmelterRecipeSerializer;
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

    //Simple Kiln
    public static final Supplier<RecipeSerializer<SimpleKilnRecipe>> SIMPLE_KILN_SERIALIZER =
            RECIPE_SERIALIZERS.register("simple_kiln_block", SimpleKilnRecipeSerializer::new);

    public static final Supplier<RecipeType<SimpleKilnRecipe>> SIMPLE_KILN_TYPE =
            RECIPE_TYPES.register("firing", registryName -> new RecipeType<SimpleKilnRecipe>() {
                @Override
                public String toString() {
                    return registryName.toString();
                }
            });

    public static final Supplier<RecipeDisplay.Type<SimpleKilnRecipeDisplay>> SIMPLE_KILN_RECIPE_DISPLAY = RECIPE_DISPLAY_TYPES.register(
            "simple_kiln_block",
            () -> new RecipeDisplay.Type<>(SimpleKilnRecipeDisplay.MAP_CODEC, SimpleKilnRecipeDisplay.STREAM_CODEC)
    );

    //Brick Mold
    public static final Supplier<RecipeSerializer<BrickMoldRecipe>> BRICK_MOLD_SERIALIZER =
            RECIPE_SERIALIZERS.register("brick_mold", BrickMoldRecipeSerializer::new);

    //Smelter
    public static final Supplier<RecipeSerializer<SmelterRecipe>> SMELTER_SERIALIZER =
            RECIPE_SERIALIZERS.register("smelter_controller_block", SmelterRecipeSerializer::new);

    public static final Supplier<RecipeType<SmelterRecipe>> SMELTER_TYPE =
            RECIPE_TYPES.register("smelting", registryName -> new RecipeType<SmelterRecipe>() {
                @Override
                public String toString() {
                    return registryName.toString();
                }
            });

    public static final Supplier<RecipeDisplay.Type<SmelterRecipeDisplay>> SMELTER_RECIPE_DISPLAY = RECIPE_DISPLAY_TYPES.register(
            "smelter_controller_block",
            () -> new RecipeDisplay.Type<>(SmelterRecipeDisplay.MAP_CODEC, SmelterRecipeDisplay.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        RECIPE_DISPLAY_TYPES.register(eventBus);
    }
}
