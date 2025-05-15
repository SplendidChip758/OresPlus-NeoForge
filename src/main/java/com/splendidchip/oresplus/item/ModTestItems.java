package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTestItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OresPlus.MOD_ID);

    //Test Items
    public static final DeferredItem<Item> TEST_ITEM_1 = ITEMS.registerItem("test_item_1",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> TEST_ITEM_2 = ITEMS.registerItem("test_item_2",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> TEST_ITEM_3 = ITEMS.registerItem("test_item_3",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> TEST_ITEM_4 = ITEMS.registerItem("test_item_4",
            Item::new,
            new Item.Properties());

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
