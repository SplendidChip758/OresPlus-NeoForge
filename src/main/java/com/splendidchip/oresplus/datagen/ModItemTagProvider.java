package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.item.ModItems;
import com.splendidchip.oresplus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, OresPlus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(ModTags.Items.DUSTS)
                .add(ModItems.LIMESTONE_DUST.get())
                .add(ModItems.CARBON_DUST.get())
                .add(ModItems.QUICK_LIME.get());

        tag(ModTags.Items.FLUXES)
                .add(ModItems.LIMESTONE_DUST.get())
                .add(ModItems.QUICK_LIME.get());

        tag(ModTags.Items.PIG_IRON_REPAIRABLE)
                .add(ModItems.PIG_IRON_INGOT.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.PIG_IRON_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.PIG_IRON_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.PIG_IRON_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.PIG_IRON_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.PIG_IRON_HOE.get());
    }
}
