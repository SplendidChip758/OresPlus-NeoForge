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

    public static final DeferredItem<Item> CRUSHED_BAUXITE = ITEMS.registerItem("crushed_bauxite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> RAW_SALT = ITEMS.registerItem("raw_salt",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> SALT = ITEMS.registerItem("salt",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> RAW_HEMATITE = ITEMS.registerItem("raw_hematite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> RAW_MAGNETITE = ITEMS.registerItem("raw_magnetite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> CRUSHED_HEMATITE = ITEMS.registerItem("crushed_hematite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> CRUSHED_MAGNETITE = ITEMS.registerItem("crushed_magnetite",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> LIMESTONE_DUST = ITEMS.registerItem("limestone_dust",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> COKE = ITEMS.registerItem("coke",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> REFRACTORY_CEMENT = ITEMS.registerItem("refractory_cement",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> UNFIRED_REFRACTORY_BRICK = ITEMS.registerItem("unfired_refractory_brick",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> REFRACTORY_BRICK = ITEMS.registerItem("refractory_brick",
            Item::new,
            new Item.Properties());

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
