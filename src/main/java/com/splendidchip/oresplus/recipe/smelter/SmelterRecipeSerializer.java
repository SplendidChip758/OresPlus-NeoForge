package com.splendidchip.oresplus.recipe.smelter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SmelterRecipeSerializer implements RecipeSerializer<SmelterRecipe> {

    public static final MapCodec<SmelterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("input1").forGetter(SmelterRecipe::getInput1),
            Ingredient.CODEC.optionalFieldOf("input2").forGetter(SmelterRecipe::getInput2),
            Ingredient.CODEC.optionalFieldOf("flux").forGetter(SmelterRecipe::getFlux),
            ItemStack.CODEC.fieldOf("result").forGetter(SmelterRecipe::getResult),
            Codec.INT.optionalFieldOf("cook_time", 200).forGetter(SmelterRecipe::getCookTime),
            Codec.FLOAT.optionalFieldOf("experience", 0.0f).forGetter(SmelterRecipe::getExperience)
    ).apply(inst, SmelterRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SmelterRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, SmelterRecipe::getInput1,
                    Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, SmelterRecipe::getInput2,
                    Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, SmelterRecipe::getFlux,
                    ItemStack.STREAM_CODEC, SmelterRecipe::getResult,
                    ByteBufCodecs.VAR_INT, SmelterRecipe::getCookTime,
                    ByteBufCodecs.FLOAT, SmelterRecipe::getExperience,
                    SmelterRecipe::new
            );
    
    @Override
    public MapCodec<SmelterRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SmelterRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
