package com.splendidchip.oresplus.recipe.simpleKiln;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record SimpleKilnRecipeDisplay(SlotDisplay inputItem, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {

    public static final MapCodec<SimpleKilnRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SlotDisplay.CODEC.fieldOf("input").forGetter(SimpleKilnRecipeDisplay::inputItem),
                    SlotDisplay.CODEC.fieldOf("result").forGetter(SimpleKilnRecipeDisplay::result),
                    SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(SimpleKilnRecipeDisplay::craftingStation)
            ).apply(instance, SimpleKilnRecipeDisplay::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleKilnRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            SimpleKilnRecipeDisplay::inputItem,
            SlotDisplay.STREAM_CODEC,
            SimpleKilnRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            SimpleKilnRecipeDisplay::craftingStation,
            SimpleKilnRecipeDisplay::new
    );

    @Override
    public RecipeDisplay.Type<? extends RecipeDisplay> type() {
        return ModRecipes.SIMPLE_KILN_RECIPE_DISPLAY.get();
    }
}