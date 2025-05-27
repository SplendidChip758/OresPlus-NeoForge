package com.splendidchip.oresplus.screen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.screen.custom.CrusherMenu;
import com.splendidchip.oresplus.screen.custom.SimpleKilnMenu;
import com.splendidchip.oresplus.screen.custom.SimpleSmelterMenu;
import com.splendidchip.oresplus.screen.custom.SmelterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, OresPlus.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CrusherMenu>> CRUSHER_MENU =
            registerMenuType("crusher_menu", CrusherMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SimpleKilnMenu>> SIMPLE_KILN_MENU =
            registerMenuType("simple_kiln_menu", SimpleKilnMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SimpleSmelterMenu>> SIMPLE_SMELTER_MENU =
            registerMenuType("simple_smelter_menu", SimpleSmelterMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SmelterMenu>> SMELTER_MENU =
            registerMenuType("smelter_menu", SmelterMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}