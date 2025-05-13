package com.splendidchip.oresplus.util;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> BAUXITE_ORE_REPLACABLES = createTag("bauxite_ore_replacables");
        public static final TagKey<Block> SALT_ORE_REPLACABLES = createTag("salt_ore_replacables");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
        }
    }
}
