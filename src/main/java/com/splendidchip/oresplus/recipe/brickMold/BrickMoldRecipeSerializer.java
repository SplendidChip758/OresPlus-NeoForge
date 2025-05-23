package com.splendidchip.oresplus.recipe.brickMold;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;

import static net.minecraft.network.codec.ByteBufCodecs.collection;

public class BrickMoldRecipeSerializer implements RecipeSerializer<BrickMoldRecipe> {

    public static final MapCodec<BrickMoldRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(BrickMoldRecipe::getInputItems),
            ItemStack.CODEC.fieldOf("result").forGetter(BrickMoldRecipe::getResult)
    ).apply(inst, BrickMoldRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BrickMoldRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    collection(ArrayList::new, Ingredient.CONTENTS_STREAM_CODEC), BrickMoldRecipe::getInputItems,
                    ItemStack.STREAM_CODEC, BrickMoldRecipe::getResult,
                    BrickMoldRecipe::new
            );

    @Override
    public MapCodec<BrickMoldRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BrickMoldRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
