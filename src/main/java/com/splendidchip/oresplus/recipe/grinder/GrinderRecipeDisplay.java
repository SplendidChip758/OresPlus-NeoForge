package com.splendidchip.oresplus.recipe.grinder;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.splendidchip.oresplus.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;


public record GrinderRecipeDisplay (SlotDisplay inputItem, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<GrinderRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            SlotDisplay.CODEC.fieldOf("inputState").forGetter(GrinderRecipeDisplay::inputItem),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(GrinderRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(GrinderRecipeDisplay::craftingStation)
                    )
                    .apply(instance, GrinderRecipeDisplay::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GrinderRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            GrinderRecipeDisplay::inputItem,
            SlotDisplay.STREAM_CODEC,
            GrinderRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC,
            GrinderRecipeDisplay::craftingStation,
            GrinderRecipeDisplay::new
    );

    @Override
    public RecipeDisplay.Type<? extends RecipeDisplay> type() {
        // Return the registered type from below
        return ModRecipes.GRINDER_RECIPE_DISPLAY.get();
    }

}
