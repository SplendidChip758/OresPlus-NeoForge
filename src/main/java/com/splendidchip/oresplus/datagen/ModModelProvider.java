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
        blockModels.createTrivialCube(ModBlocks.BAUXITE_BLOCK.get());

        //Items
        itemModels.generateFlatItem(ModItems.ALUMINA.get(), ModelTemplates.FLAT_ITEM);

    }
}
