package com.splendidchip.oresplus.item;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.util.ModTags;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

import static net.minecraft.world.item.equipment.EquipmentAssets.createId;

public class ModArmorMaterials {

    public static final ArmorMaterial STEEL_ARMOR_MATERIAL = new ArmorMaterial(24,
            Util.make(new EnumMap<>(ArmorType.class), attribute -> {
                attribute.put(ArmorType.BOOTS, 2);
                attribute.put(ArmorType.LEGGINGS, 6);
                attribute.put(ArmorType.CHESTPLATE, 7);
                attribute.put(ArmorType.HELMET, 3);
                attribute.put(ArmorType.BODY, 9);
            }), 9, SoundEvents.ARMOR_EQUIP_IRON,
            1f, 0.0f,
            ModTags.Items.STEEL_REPAIRABLE,
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "steel"))
    );
}
