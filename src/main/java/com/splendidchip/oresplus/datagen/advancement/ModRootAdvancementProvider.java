package com.splendidchip.oresplus.datagen.advancement;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class ModRootAdvancementProvider implements AdvancementSubProvider {
    public static AdvancementHolder ROOT;

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {

         ROOT = Advancement.Builder.advancement()
                .display(
                        ModItems.ALUMINUM_INGOT,
                        Component.translatable("advancement.oresplus.root.title"),
                        Component.translatable("advancement.oresplus.root.description"),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/advancements/backgrounds/stone.png"),
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "root"));

        AdvancementHolder hematite_root = Advancement.Builder.advancement()
                .parent(ROOT)
                .display(
                        ModBlocks.HEMATITE_ORE,
                        Component.translatable("advancement.oresplus.mine_hematite.title"),
                        Component.translatable("advancement.oresplus.mine_hematite.description"),
                        null,
                        AdvancementType.GOAL,
                        true, true, false
                )
                .addCriterion("mine_hematite",  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_HEMATITE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "mining/mine_hematite"));

        AdvancementHolder magnetite_root = Advancement.Builder.advancement()
                .parent(ROOT)
                .display(
                        ModBlocks.MAGNETITE_ORE,
                        Component.translatable("advancement.oresplus.mine_magnetite.title"),
                        Component.translatable("advancement.oresplus.mine_magnetite.description"),
                        null,
                        AdvancementType.GOAL,
                        true, true, false
                )
                .addCriterion("mine_magnetite",  InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAW_MAGNETITE))
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "mining/mine_magnetite"));
    }
}
