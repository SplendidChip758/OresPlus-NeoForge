package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlockItems;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.block.custom.ItemPipeBlock;
import com.splendidchip.oresplus.block.custom.SmelterIOBlock;
import com.splendidchip.oresplus.block.entity.SmelterIOBlockEntity;
import com.splendidchip.oresplus.item.ModArmorMaterials;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.item.ModTestItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.common.Mod;

import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;


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

        // Crusher Block
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(
                        ModBlocks.CRUSHER_BLOCK.get()).with(PropertyDispatch.property(HORIZONTAL_FACING)
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
                        .with(PropertyDispatch.properties(HORIZONTAL_FACING, LIT)
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

        // Simple Smelter
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(ModBlocks.SIMPLE_SMELTER_BLOCK.get())
                        .with(PropertyDispatch.properties(HORIZONTAL_FACING, LIT)
                                .select(Direction.NORTH, false, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter"), 0))
                                .select(Direction.NORTH, true, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter_on"), 0))
                                .select(Direction.EAST, false, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter"), 90))
                                .select(Direction.EAST, true, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter_on"), 90))
                                .select(Direction.SOUTH, false, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter"), 180))
                                .select(Direction.SOUTH, true, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter_on"), 180))
                                .select(Direction.WEST, false, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter"), 270))
                                .select(Direction.WEST, true, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter_on"), 270))
                        )
        );

        blockModels.registerSimpleItemModel(ModBlockItems.SIMPLE_SMELTER_BLOCK_ITEM.get(),
                ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/simple_smelter"));


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
                        .with(PropertyDispatch.properties(HORIZONTAL_FACING, LIT)
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

        //smelter IO block
        // Input model
        ResourceLocation smelterInputModel = TexturedModel.CUBE.updateTexture(mapping -> mapping
                        .put(TextureSlot.ALL, modLocation("block/smelter_io_block_input")))
                .createWithSuffix(ModBlocks.SMELTER_IO_BLOCK.get(), "_input", blockModels.modelOutput);

        // Output model
        ResourceLocation smelterOutputModel = TexturedModel.CUBE.updateTexture(mapping -> mapping
                        .put(TextureSlot.ALL, modLocation("block/smelter_io_block_output")))
                .createWithSuffix(ModBlocks.SMELTER_IO_BLOCK.get(), "_output", blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(ModBlocks.SMELTER_IO_BLOCK.get())
                        .with(PropertyDispatch.property(SmelterIOBlock.MODE)
                                .select(SmelterIOBlockEntity.Mode.INPUT, Variant.variant().with(VariantProperties.MODEL, smelterInputModel))
                                .select(SmelterIOBlockEntity.Mode.OUTPUT, Variant.variant().with(VariantProperties.MODEL, smelterOutputModel))
                        )
        );
        blockModels.registerSimpleItemModel(ModBlockItems.SMELTER_IO_BLOCK_ITEM.get(),
                ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/smelter_io_block_input"));


        // Item Pipe
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(
                        ModBlocks.ITEM_PIPE_BLOCK.get()).with(PropertyDispatch.property(FACING)
                        .select(Direction.NORTH, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 0))
                        .select(Direction.EAST, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 90))
                        .select(Direction.SOUTH, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 180))
                        .select(Direction.WEST, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 270))
                        .select(Direction.UP, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 0))
                        .select(Direction.DOWN, variant(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"), 0))
                )
        );

        blockModels.registerSimpleItemModel(ModBlockItems.ITEM_PIPE_BLOCK_ITEM.get(),
                ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "block/item_pipe"));

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
        itemModels.generateFlatItem(ModItems.PIG_IRON_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SMELTER_UPGRADE_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UPGRADE_MODULE_CASING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SMELTER_UPGRADE_MODULE.get(), ModelTemplates.FLAT_ITEM);

        //Tools
        itemModels.generateFlatItem(ModItems.PIG_IRON_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PIG_IRON_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PIG_IRON_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PIG_IRON_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.PIG_IRON_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateFlatItem(ModItems.PIG_IRON_WRENCH.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.STEEL_WRENCH.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        //Armor
        itemModels.generateTrimmableItem(ModItems.STEEL_HELMET.get(), ModArmorMaterials.STEEL_ARMOR_MATERIAL.assetId(), "steel", false);
        itemModels.generateTrimmableItem(ModItems.STEEL_CHESTPLATE.get(), ModArmorMaterials.STEEL_ARMOR_MATERIAL.assetId(), "steel", false);
        itemModels.generateTrimmableItem(ModItems.STEEL_LEGGINGS.get(), ModArmorMaterials.STEEL_ARMOR_MATERIAL.assetId(), "steel", false);
        itemModels.generateTrimmableItem(ModItems.STEEL_BOOTS.get(), ModArmorMaterials.STEEL_ARMOR_MATERIAL.assetId(), "steel", false);


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

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.concat(
                ModItems.ITEMS.getEntries().stream(),
                ModBlockItems.ITEMS.getEntries().stream()
        );
    }
}
