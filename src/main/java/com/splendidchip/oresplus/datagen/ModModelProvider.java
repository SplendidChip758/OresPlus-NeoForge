package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.FurnaceBlock;
import net.neoforged.neoforge.client.extensions.ITexturedModelExtension;

import static net.minecraft.client.data.models.model.TexturedModel.ORIENTABLE_ONLY_TOP;

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

        blockModels.createHorizontallyRotatedBlock(
                ModBlocks.GRINDER_BLOCK.get(),
                TexturedModel.ORIENTABLE.updateTexture(mapping ->
                        mapping.put(TextureSlot.SIDE, this.modLocation("block/grinder_side"))
                                .put(TextureSlot.FRONT, this.modLocation("block/grinder_front"))
                                .put(TextureSlot.TOP, this.modLocation("block/grinder_top"))
                                .put(TextureSlot.BOTTOM, this.modLocation("block/grinder_bottom"))
                )
        );

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
