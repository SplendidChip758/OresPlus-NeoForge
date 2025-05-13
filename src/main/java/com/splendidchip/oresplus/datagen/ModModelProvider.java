package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


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
        blockModels.createTrivialCube(ModBlocks.HEMATITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.MAGNETITE_ORE.get());

        //Test Blocks
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_1.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_2.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_3.get());

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(
                        ModBlocks.GRINDER_BLOCK.get()).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/grinder_block")))
                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/grinder_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/grinder_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/grinder_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                )

        );

        //Items
        itemModels.generateFlatItem(ModItems.ALUMINA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ALUMINUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BAUXITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BAUXITE_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_HEMATITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_MAGNETITE.get(), ModelTemplates.FLAT_ITEM);

        //Test Items
        itemModels.generateFlatItem(ModItems.TEST_ITEM_1.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TEST_ITEM_2.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TEST_ITEM_3.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TEST_ITEM_4.get(), ModelTemplates.FLAT_ITEM);


    }
}
