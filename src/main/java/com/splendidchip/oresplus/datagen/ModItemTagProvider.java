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
    }
}
