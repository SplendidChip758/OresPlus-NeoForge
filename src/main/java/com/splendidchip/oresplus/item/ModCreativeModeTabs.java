package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OresPlus.MOD_ID);

    public static final Supplier<CreativeModeTab> ORESPLUS_ITEMS_TAB = CREATIVE_MODE_TABS.register("oresplus_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALUMINUM_INGOT.get()))
                    .title(Component.translatable("creativetab.oresplus.oresplus_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ALUMINUM_INGOT.get());
                        output.accept(ModItems.ALUMINA.get());
                        output.accept(ModItems.RAW_BAUXITE.get());
                        output.accept(ModItems.BAUXITE_DUST.get());
                    }).build());

    public static final Supplier<CreativeModeTab> ORESPLUS_BLOCKS_TAB = CREATIVE_MODE_TABS.register("oresplus_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BAUXITE_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "oresplus_items_tab"))
                    .title(Component.translatable("creativetab.oresplus.oresplus_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.BAUXITE_BLOCK.get());
                        output.accept(ModBlocks.ALUMINUM_BLOCK.get());
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
