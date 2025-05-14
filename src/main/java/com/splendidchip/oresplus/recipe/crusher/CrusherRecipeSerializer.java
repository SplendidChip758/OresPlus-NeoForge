package com.splendidchip.oresplus.recipe.crusher;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrusherRecipeSerializer implements RecipeSerializer<CrusherRecipe> {
    public static final MapCodec<CrusherRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CrusherRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(CrusherRecipe::getResult)
    ).apply(inst, CrusherRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, CrusherRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, CrusherRecipe::getResult,
                    CrusherRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<CrusherRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CrusherRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
