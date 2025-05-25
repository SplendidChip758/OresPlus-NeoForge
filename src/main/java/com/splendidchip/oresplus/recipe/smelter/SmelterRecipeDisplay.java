package com.splendidchip.oresplus.recipe.smelter;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record SmelterRecipeDisplay(SlotDisplay input1, SlotDisplay input2, SlotDisplay flux, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {

    public static final MapCodec<SmelterRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SlotDisplay.CODEC.fieldOf("input1").forGetter(SmelterRecipeDisplay::input1),
                    SlotDisplay.CODEC.fieldOf("input2").forGetter(SmelterRecipeDisplay::input2),
                    SlotDisplay.CODEC.fieldOf("flux").forGetter(SmelterRecipeDisplay::flux),
                    SlotDisplay.CODEC.fieldOf("result").forGetter(SmelterRecipeDisplay::result),
                    SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(SmelterRecipeDisplay::craftingStation)
            ).apply(instance, SmelterRecipeDisplay::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, SmelterRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC, SmelterRecipeDisplay::input1,
            SlotDisplay.STREAM_CODEC, SmelterRecipeDisplay::input2,
            SlotDisplay.STREAM_CODEC, SmelterRecipeDisplay::flux,
            SlotDisplay.STREAM_CODEC, SmelterRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, SmelterRecipeDisplay::craftingStation,
            SmelterRecipeDisplay::new
    );

    @Override
    public RecipeDisplay.Type<? extends RecipeDisplay> type() {
        return ModRecipes.SMELTER_RECIPE_DISPLAY.get();
    }
}
