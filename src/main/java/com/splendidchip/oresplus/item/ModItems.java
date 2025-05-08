package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OresPlus.MOD_ID);

    public static final DeferredItem<Item> ALUMINUM_INGOT = ITEMS.registerItem("aluminum_ingot",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> ALUMINA = ITEMS.registerItem("alumina",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> RAW_BAUXITE = ITEMS.registerItem("raw_bauxite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> BAUXITE_DUST = ITEMS.registerItem("bauxite_dust",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> RAW_SALT = ITEMS.registerItem("raw_salt",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> SALT = ITEMS.registerItem("salt",
            Item::new,
            new Item.Properties());

    //Block Items
    public static final DeferredItem<BlockItem> BAUXITE_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.BAUXITE_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> ALUMINUM_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.ALUMINUM_BLOCK,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> SALT_ORE_ITEM = ITEMS.registerSimpleBlockItem(
            ModBlocks.SALT_ORE,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> TEST_BLOCK_1 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_1,
            new Item.Properties()
    );

    public static final DeferredItem<BlockItem> TEST_BLOCK_2 = ITEMS.registerSimpleBlockItem(
            ModBlocks.TEST_BLOCK_2,
            new Item.Properties()
    );

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
