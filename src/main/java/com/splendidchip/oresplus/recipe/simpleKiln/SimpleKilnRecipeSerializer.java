package com.splendidchip.oresplus.recipe.simpleKiln;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleKilnRecipeSerializer implements RecipeSerializer<SimpleKilnRecipe> {

    public static final MapCodec<SimpleKilnRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(SimpleKilnRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(SimpleKilnRecipe::getResult),
            Codec.INT.optionalFieldOf("cook_time", 200).forGetter(SimpleKilnRecipe::getCookTime)
    ).apply(inst, SimpleKilnRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleKilnRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, SimpleKilnRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, SimpleKilnRecipe::getResult,
                    ByteBufCodecs.VAR_INT, SimpleKilnRecipe::getCookTime,
                    SimpleKilnRecipe::new
            );

    @Override
    public MapCodec<SimpleKilnRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SimpleKilnRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}

