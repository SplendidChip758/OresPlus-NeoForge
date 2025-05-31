package com.splendidchip.oresplus.worldgen;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
        DeferredRegister.create(Registries.FEATURE, OresPlus.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<OreConfiguration>> BRANCHING_COAL_VEIN =
            FEATURES.register("branching_coal_vein", () -> new BranchingCoalVeinFeature(OreConfiguration.CODEC));

}
