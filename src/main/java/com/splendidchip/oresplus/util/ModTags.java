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

        public static final TagKey<Block> NEEDS_PIG_IRON_TOOL = createTag("needs_pig_iron_tool");
        public static final TagKey<Block> INCORRECT_FOR_PIG_IRON_TOOL = createTag("incorrect_for_pig_iron_tool");
        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");

        public static final TagKey<Block> BAUXITE_ORE_REPLACABLES = createTag("bauxite_ore_replacables");
        public static final TagKey<Block> SALT_ORE_REPLACABLES = createTag("salt_ore_replacables");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> DUSTS = createTag("dusts");
        public static final TagKey<Item> FLUXES = createTag("fluxes");
        public static final TagKey<Item> PIG_IRON_REPAIRABLE = createTag("pig_iron_repairable");
        public static final TagKey<Item> STEEL_REPAIRABLE = createTag("steel_repairable");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
        }
    }
}
