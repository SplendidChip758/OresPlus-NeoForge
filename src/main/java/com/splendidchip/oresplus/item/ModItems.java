package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.world.item.*;
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

    public static final DeferredItem<Item> BRICK_MOLD = ITEMS.registerItem("brick_mold",
            Item::new,
            new Item.Properties().durability(32).stacksTo(1));

    public static final DeferredItem<Item> CARBON_DUST = ITEMS.registerItem("carbon_dust",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> QUICK_LIME = ITEMS.registerItem("quick_lime",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> PIG_IRON_INGOT = ITEMS.registerItem("pig_iron_ingot",
            Item::new,
            new Item.Properties());

    public static final DeferredItem<Item> STEEL_INGOT = ITEMS.registerItem("steel_ingot",
            Item::new,
            new Item.Properties());

    // Tools and weapons
    public static final DeferredItem<SwordItem> PIG_IRON_SWORD = ITEMS.registerItem(
            "pig_iron_sword",
            props -> new SwordItem(ModToolTeirs.PIG_IRON_TOOL_MATERIAL, 3.0f, -2.4f, props)
    );
    public static final DeferredItem<ShovelItem> PIG_IRON_SHOVEL = ITEMS.registerItem(
            "pig_iron_shovel",
            props -> new ShovelItem(ModToolTeirs.PIG_IRON_TOOL_MATERIAL, 1.5f, -3.0f, props)
    );

    public static final DeferredItem<PickaxeItem> PIG_IRON_PICKAXE = ITEMS.registerItem(
            "pig_iron_pickaxe",
            props -> new PickaxeItem(ModToolTeirs.PIG_IRON_TOOL_MATERIAL, 1.0f, -2.8f, props)
    );

    public static final DeferredItem<AxeItem> PIG_IRON_AXE = ITEMS.registerItem(
            "pig_iron_axe",
            props -> new AxeItem(ModToolTeirs.PIG_IRON_TOOL_MATERIAL, 6.0f, -3.1f, props)
    );

    public static final DeferredItem<HoeItem> PIG_IRON_HOE = ITEMS.registerItem(
            "pig_iron_hoe",
            props -> new HoeItem(ModToolTeirs.PIG_IRON_TOOL_MATERIAL, -2.0f, -1.0f, props)
    );

    public static final DeferredItem<SwordItem> STEEL_SWORD = ITEMS.registerItem(
            "steel_sword",
            props -> new SwordItem(ModToolTeirs.STEEL_TOOL_MATERIAL, 3.0f, -2.4f, props)
    );
    public static final DeferredItem<ShovelItem> STEEL_SHOVEL = ITEMS.registerItem(
            "steel_shovel",
            props -> new ShovelItem(ModToolTeirs.STEEL_TOOL_MATERIAL, 1.5f, -3.0f, props)
    );

    public static final DeferredItem<PickaxeItem> STEEL_PICKAXE = ITEMS.registerItem(
            "steel_pickaxe",
            props -> new PickaxeItem(ModToolTeirs.STEEL_TOOL_MATERIAL, 1.0f, -2.8f, props)
    );

    public static final DeferredItem<AxeItem> STEEL_AXE = ITEMS.registerItem(
            "steel_axe",
            props -> new AxeItem(ModToolTeirs.STEEL_TOOL_MATERIAL, 5.5f, -3.0f, props)
    );

    public static final DeferredItem<HoeItem> STEEL_HOE = ITEMS.registerItem(
            "steel_hoe",
            props -> new HoeItem(ModToolTeirs.STEEL_TOOL_MATERIAL, -2.0f, -1.0f, props)
    );




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
