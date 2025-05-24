package com.splendidchip.oresplus.villager;

import com.google.common.collect.ImmutableSet;
import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, OresPlus.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, OresPlus.MOD_ID);

    public static final Holder<PoiType> SMELTER_POI = POI_TYPES.register("smelter_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SIMPLE_KILN_BLOCK.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> SMELTER = VILLAGER_PROFESSIONS.register("smelter",
            () -> new VillagerProfession("smelter", holder -> holder.value() == SMELTER_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == SMELTER_POI.value(), ImmutableSet.of(), ImmutableSet.of(), SoundType.ANVIL.getPlaceSound()));


    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
