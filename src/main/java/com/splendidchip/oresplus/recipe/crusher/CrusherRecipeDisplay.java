package com.splendidchip.oresplus.recipe.crusher;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;


public record CrusherRecipeDisplay(SlotDisplay inputItem, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<CrusherRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.fieldOf("inputState").forGetter(CrusherRecipeDisplay::inputItem),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(CrusherRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(CrusherRecipeDisplay::craftingStation)
                    )
                    .apply(instance, CrusherRecipeDisplay::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, CrusherRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            CrusherRecipeDisplay::inputItem,
            SlotDisplay.STREAM_CODEC,
            CrusherRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            CrusherRecipeDisplay::craftingStation,
            CrusherRecipeDisplay::new
    );

    @Override
    public RecipeDisplay.Type<? extends RecipeDisplay> type() {
        // Return the registered type from below
        return ModRecipes.CRUSHER_RECIPE_DISPLAY.get();
    }

}
