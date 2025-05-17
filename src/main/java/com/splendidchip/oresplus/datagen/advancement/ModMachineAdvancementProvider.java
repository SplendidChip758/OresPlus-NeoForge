package com.splendidchip.oresplus.datagen.advancement;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class ModMachineAdvancementProvider implements AdvancementSubProvider {

    public static AdvancementHolder CRUSHER;

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {

        CRUSHER = Advancement.Builder.advancement()
                .parent(ModRootAdvancementProvider.ROOT)
                .display(
                        ModBlocks.CRUSHER_BLOCK,
                        Component.translatable("advancement.oresplus.crusher.title"),
                        Component.translatable("advancement.oresplus.crusher.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("craft_crusher", RecipeCraftedTrigger.TriggerInstance.craftedItem(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "crusher_block"))))
                .save(saver, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "machine/crusher"));
    }
}
