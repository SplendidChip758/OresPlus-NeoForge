package com.splendidchip.oresplus.block;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OresPlus.MOD_ID);

    //Block Items
    public static final DeferredItem<BlockItem> ALUMINUM_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.ALUMINUM_BLOCK,
            new Item.Properties()
    );

    //Block Ores Items
    public static final DeferredItem<BlockItem> BAUXITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.BAUXITE_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> SALT_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.SALT_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> HEMATITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.HEMATITE_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> MAGNETITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.MAGNETITE_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> LIMESTONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.LIMESTONE_BLOCK,
            new Item.Properties()
    );

    //Block Entities Items
    public static final DeferredItem<BlockItem> CRUSHER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.CRUSHER_BLOCK,
            new Item.Properties()
    );

    //Test Blocks Items
    public static final DeferredItem<BlockItem> TEST_BLOCK_1 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_1,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> TEST_BLOCK_2 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_2,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> TEST_BLOCK_3 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_3,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> TEST_BLOCK_4 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_4,
            new Item.Properties()
    );

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
