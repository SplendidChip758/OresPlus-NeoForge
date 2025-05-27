package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.datagen.advancement.ModBauxiteAdvancementProvider;
import com.splendidchip.oresplus.datagen.advancement.ModMachineAdvancementProvider;
import com.splendidchip.oresplus.datagen.advancement.ModRootAdvancementProvider;
import com.splendidchip.oresplus.datagen.recipes.*;
import net.minecraft.data.advancements.AdvancementProvider;
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
    public static void gatherClientData(GatherDataEvent.Client event) {

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider
        ));

        event.createProvider((output, lookupProvider) -> new AdvancementProvider(
                output, lookupProvider,
                List.of(new ModRootAdvancementProvider(), new ModBauxiteAdvancementProvider(), new ModMachineAdvancementProvider())
        ));

        //registering the recipe providers
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(CrusherRecipeProvider.Runner::new);
        event.createProvider(SimpleKilnRecipeProvider.Runner::new);
        event.createProvider(SmelterRecipeProvider.Runner::new);
        event.createProvider(CookingRecipeProvider.Runner::new);
        event.createProvider(BrickMoldRecipeProvider.Runner::new);
        event.createProvider(ToolRecipeProvider.Runner::new);

        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);

        event.createProvider(ModDataMapProvider::new);

        event.createProvider(ModModelProvider::new);

        event.createProvider(ModDatapackProvider::new);
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTableProvider::new,
                        LootContextParamSets.BLOCK
                )),
                lookupProvider
        ));

        event.createProvider((output, lookupProvider) -> new AdvancementProvider(
                output, lookupProvider,
                List.of(
                        new ModRootAdvancementProvider(),
                        new ModBauxiteAdvancementProvider(),
                        new ModMachineAdvancementProvider()
                )
        ));

        //registering the recipe providers
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(CrusherRecipeProvider.Runner::new);
        event.createProvider(SimpleKilnRecipeProvider.Runner::new);
        event.createProvider(SmelterRecipeProvider.Runner::new);
        event.createProvider(CookingRecipeProvider.Runner::new);
        event.createProvider(BrickMoldRecipeProvider.Runner::new);
        event.createProvider(ToolRecipeProvider.Runner::new);

        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);

        event.createProvider(ModDataMapProvider::new);

        event.createProvider(ModModelProvider::new);

        event.createProvider(ModDatapackProvider::new);
    }
}
