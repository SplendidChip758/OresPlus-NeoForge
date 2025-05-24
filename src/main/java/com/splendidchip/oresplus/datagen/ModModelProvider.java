package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.item.ModTestItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
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
        blockModels.createTrivialCube(ModBlocks.LIMESTONE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.REFRACTORY_BRICKS.get());

        blockModels.createTrivialCube(ModBlocks.BAUXITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.SALT_ORE.get());
        blockModels.createTrivialCube(ModBlocks.HEMATITE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.MAGNETITE_ORE.get());

        //Test Blocks
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_1.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_2.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_3.get());
        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK_4.get());

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(
                        ModBlocks.CRUSHER_BLOCK.get()).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/crusher_block")))
                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/crusher_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/crusher_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/crusher_block"))
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                )
        );

        // Simple Kiln
        // Unlit model
        ResourceLocation simpleKilnUnlitModel = TexturedModel.ORIENTABLE.updateTexture(mapping -> mapping
                        .put(TextureSlot.SIDE, modLocation("block/simple_kiln_side"))
                        .put(TextureSlot.FRONT, modLocation("block/simple_kiln_front"))
                        .put(TextureSlot.TOP, modLocation("block/simple_kiln_top"))
                        .put(TextureSlot.BOTTOM, modLocation("block/simple_kiln_bottom")))
                .create(ModBlocks.SIMPLE_KILN_BLOCK.get(), blockModels.modelOutput);

        // Lit model
        ResourceLocation simpleKilnLitModel = TexturedModel.ORIENTABLE.updateTexture(mapping -> mapping
                        .put(TextureSlot.SIDE, modLocation("block/simple_kiln_side"))
                        .put(TextureSlot.FRONT, modLocation("block/simple_kiln_front_lit"))
                        .put(TextureSlot.TOP, modLocation("block/simple_kiln_top"))
                        .put(TextureSlot.BOTTOM, modLocation("block/simple_kiln_bottom")))
                .createWithSuffix(ModBlocks.SIMPLE_KILN_BLOCK.get(), "_lit", blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(ModBlocks.SIMPLE_KILN_BLOCK.get())
                        .with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT)
                                .select(Direction.NORTH, false, variant(simpleKilnUnlitModel, 0))
                                .select(Direction.NORTH, true, variant(simpleKilnLitModel, 0))
                                .select(Direction.EAST, false, variant(simpleKilnUnlitModel, 90))
                                .select(Direction.EAST, true, variant(simpleKilnLitModel, 90))
                                .select(Direction.SOUTH, false, variant(simpleKilnUnlitModel, 180))
                                .select(Direction.SOUTH, true, variant(simpleKilnLitModel, 180))
                                .select(Direction.WEST, false, variant(simpleKilnUnlitModel, 270))
                                .select(Direction.WEST, true, variant(simpleKilnLitModel, 270))
                        )
        );

        // Smelter Controller
        // Unlit model
        ResourceLocation smelterControllerUnlitModel = TexturedModel.ORIENTABLE.updateTexture(mapping -> mapping
                        .put(TextureSlot.SIDE, modLocation("block/refractory_bricks"))
                        .put(TextureSlot.FRONT, modLocation("block/smelter_front"))
                        .put(TextureSlot.TOP, modLocation("block/refractory_bricks"))
                        .put(TextureSlot.BOTTOM, modLocation("block/refractory_bricks")))
                .create(ModBlocks.SMELTER_CONTROLLER_BLOCK.get(), blockModels.modelOutput);

        // lit model
        ResourceLocation smelterControllerlitModel = TexturedModel.ORIENTABLE.updateTexture(mapping -> mapping
                        .put(TextureSlot.SIDE, modLocation("block/refractory_bricks"))
                        .put(TextureSlot.FRONT, modLocation("block/smelter_front_lit"))
                        .put(TextureSlot.TOP, modLocation("block/refractory_bricks"))
                        .put(TextureSlot.BOTTOM, modLocation("block/refractory_bricks")))
                .createWithSuffix(ModBlocks.SMELTER_CONTROLLER_BLOCK.get(), "_lit", blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(ModBlocks.SMELTER_CONTROLLER_BLOCK.get())
                        .with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT)
                                .select(Direction.NORTH, false, variant(smelterControllerUnlitModel, 0))
                                .select(Direction.NORTH, true, variant(smelterControllerlitModel, 0))
                                .select(Direction.EAST, false, variant(smelterControllerUnlitModel, 90))
                                .select(Direction.EAST, true, variant(smelterControllerlitModel, 90))
                                .select(Direction.SOUTH, false, variant(smelterControllerUnlitModel, 180))
                                .select(Direction.SOUTH, true, variant(smelterControllerlitModel, 180))
                                .select(Direction.WEST, false, variant(smelterControllerUnlitModel, 270))
                                .select(Direction.WEST, true, variant(smelterControllerlitModel, 270))
                        )
        );


        //Items
        itemModels.generateFlatItem(ModItems.ALUMINA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ALUMINUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_BAUXITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRUSHED_BAUXITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_HEMATITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_MAGNETITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRUSHED_HEMATITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CRUSHED_MAGNETITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIMESTONE_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.QUICK_LIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COKE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CARBON_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.REFRACTORY_CEMENT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.REFRACTORY_BRICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UNFIRED_REFRACTORY_BRICK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BRICK_MOLD.get(), ModelTemplates.FLAT_ITEM);

        //Test Items
        itemModels.generateFlatItem(ModTestItems.TEST_ITEM_1.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModTestItems.TEST_ITEM_2.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModTestItems.TEST_ITEM_3.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModTestItems.TEST_ITEM_4.get(), ModelTemplates.FLAT_ITEM);

    }

    private static Variant variant(ResourceLocation model, int yRot) {
        return Variant.variant()
                .with(VariantProperties.MODEL, model)
                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[yRot / 90]);
    }
}
