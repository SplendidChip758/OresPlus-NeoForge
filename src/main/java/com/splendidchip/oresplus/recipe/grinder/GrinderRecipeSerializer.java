package com.splendidchip.oresplus.recipe.grinder;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class GrinderRecipeSerializer implements RecipeSerializer<GrinderRecipe> {
    public static final MapCodec<GrinderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(GrinderRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(GrinderRecipe::getResult)
    ).apply(inst, GrinderRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, GrinderRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, GrinderRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, GrinderRecipe::getResult,
                    GrinderRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<GrinderRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, GrinderRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
