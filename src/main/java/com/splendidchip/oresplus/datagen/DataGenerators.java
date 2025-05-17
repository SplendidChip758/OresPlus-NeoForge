package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = OresPlus.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                // A list of sub provider entries. See below for what values to use here.
                List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTableProvider::new,
                        LootContextParamSets.BLOCK
                )),
        // The registry access
        lookupProvider
    ));

        event.createProvider(ModRecipeProvider.Runner::new);

        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);

        event.createProvider(ModDataMapProvider::new);

        event.createProvider(ModModelProvider::new);

        event.createProvider(ModDatapackProvider::new);
    }
}
