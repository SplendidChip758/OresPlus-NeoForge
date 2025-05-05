package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
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

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
