package com.splendidchip.oresplus.datagen.advancement;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class ModBauxiteAdvancementProvider implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {

        AdvancementHolder bauxite_root = Advancement.Builder.advancement()
                .parent(ModRootAdvancementProvider.ROOT)
                .display(
                        ModBlocks.BAUXITE_ORE,
                        Component.translatable("advancement.oresplus.mine_bauxite.title"),
                        Component.translatable("advancement.oresplus.mine_bauxite.description"),
                        null,
                        AdvancementType.GOAL,
                        true, true, false
                )
                .addCriterion("mine_bauxite",  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_BAUXITE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "mining/mine_bauxite"));

        Advancement.Builder.advancement()
                .parent(bauxite_root)
                .display(
                        ModItems.CRUSHED_BAUXITE,
                        Component.translatable("advancement.oresplus.crush_bauxite.title"),
                        Component.translatable("advancement.oresplus.crush_bauxite.description"),
                        null,
                        AdvancementType.GOAL,
                        true, true, false
                )
                .addCriterion("crush_bauxite",  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CRUSHED_BAUXITE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "bauxite/crush_bauxite"));

    }
}
