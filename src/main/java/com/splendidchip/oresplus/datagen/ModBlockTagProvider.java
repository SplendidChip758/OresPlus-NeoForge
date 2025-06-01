package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;

import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, OresPlus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALUMINUM_BLOCK.get())
                .add(ModBlocks.BAUXITE_ORE.get())
                .add(ModBlocks.SALT_ORE.get())
                .add(ModBlocks.HEMATITE_ORE.get())
                .add(ModBlocks.MAGNETITE_ORE.get())
                .add(ModBlocks.TEST_BLOCK_1.get())
                .add(ModBlocks.TEST_BLOCK_2.get())
                .add(ModBlocks.LIMESTONE_BLOCK.get())
                .add(ModBlocks.CRUSHER_BLOCK.get())
                .add(ModBlocks.SIMPLE_KILN_BLOCK.get())
                .add(ModBlocks.SIMPLE_SMELTER_BLOCK.get())
                .add(ModBlocks.SMELTER_CONTROLLER_BLOCK.get())
                .add(ModBlocks.SMELTER_IO_BLOCK.get());

        tag(ModTags.Blocks.BAUXITE_ORE_REPLACABLES)
                .add(Blocks.STONE)
                .add(Blocks.GRANITE)
                .add(Blocks.DIORITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIRT);

        tag(ModTags.Blocks.SALT_ORE_REPLACABLES)
                .add(Blocks.STONE)
                .add(Blocks.GRANITE)
                .add(Blocks.DIORITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.SAND)
                .add(Blocks.SANDSTONE);

        tag(ModTags.Blocks.NEEDS_PIG_IRON_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_PIG_IRON_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_PIG_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_STEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .remove(ModTags.Blocks.NEEDS_STEEL_TOOL);

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.MAGNETITE_ORE.get());
    }
}
