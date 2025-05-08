package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, OresPlus.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        //Blocks

        blockModels.createTrivialCube(ModBlocks.ALUMINUM_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.BAUXITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.SALT_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_1.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_2.get());

        //Items
        itemModels.generateFlatItem(ModItems.ALUMINA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ALUMINUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BAUXITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BAUXITE_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SALT.get(), ModelTemplates.FLAT_ITEM);

    }
}
