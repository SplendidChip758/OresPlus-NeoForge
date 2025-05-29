package com.splendidchip.oresplus.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(ArmorMaterial material, ArmorType type, Properties properties) {
        super(material, type, properties);
    }


    private boolean hasPlayerCorrectArmorOn(ArmorMaterial mapArmorMaterial, Player player) {
        for(ItemStack armorStack : player.getArmorSlots()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        Equippable equippableComponentBoots = player.getInventory().getArmor(0).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentLeggings = player.getInventory().getArmor(1).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentBreastplate = player.getInventory().getArmor(2).getComponents().get(DataComponents.EQUIPPABLE);
        Equippable equippableComponentHelmet = player.getInventory().getArmor(3).getComponents().get(DataComponents.EQUIPPABLE);

        return equippableComponentBoots.assetId().get().equals(mapArmorMaterial.assetId()) &&
                equippableComponentLeggings.assetId().get().equals(mapArmorMaterial.assetId()) &&
                equippableComponentBreastplate.assetId().get().equals(mapArmorMaterial.assetId()) &&
                equippableComponentHelmet.assetId().get().equals(mapArmorMaterial.assetId());
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }
}