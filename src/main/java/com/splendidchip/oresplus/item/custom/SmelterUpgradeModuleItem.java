package com.splendidchip.oresplus.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmelterUpgradeModuleItem extends Item {
    public SmelterUpgradeModuleItem(Properties properties) {
        super(properties);
    }


    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.oresplus.smelter_upgrade_module.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
