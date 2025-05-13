package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.block.ModBlocks;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ALUMINUM_BLOCK.get());
        dropSelf(ModBlocks.GRINDER_BLOCK.get());

        dropSelf(ModBlocks.HEMATITE_ORE.get());
        dropSelf(ModBlocks.MAGNETITE_ORE.get());

        dropSelf(ModBlocks.TEST_BLOCK_1.get());
        dropSelf(ModBlocks.TEST_BLOCK_2.get());
        dropSelf(ModBlocks.TEST_BLOCK_3.get());

        add(ModBlocks.BAUXITE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.BAUXITE_ORE.get(), ModItems.RAW_BAUXITE.get(), 2, 4));

        add(ModBlocks.SALT_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.SALT_ORE.get(), ModItems.RAW_SALT.get(), 3, 6));

        add(ModBlocks.HEMATITE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.HEMATITE_ORE.get(), ModItems.RAW_HEMATITE.get(), 1, 2));

        add(ModBlocks.MAGNETITE_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.MAGNETITE_ORE.get(), ModItems.RAW_MAGNETITE.get(), 1, 3));

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
