package com.splendidchip.oresplus.worldgen;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> BAUXITE_ORE_PLACED_KEY = registerKey("bauxite_ore_placed");
    public static final ResourceKey<PlacedFeature> BAUXITE_ORE_PLACED_JUNGLE_SAVANNA_KEY = registerKey("bauxite_ore_jungle_savanna_placed");

    public static final ResourceKey<PlacedFeature> SALT_ORE_PLACED_KEY = registerKey("salt_ore_placed");
    public static final ResourceKey<PlacedFeature> SALT_ORE_PLACED_SHORE_DESERT_KEY = registerKey("salt_ore_shore_desert_placed");

    public static final ResourceKey<PlacedFeature> HEMATITE_ORE_PLACED_KEY = registerKey("hematite_ore_placed");
    public static final ResourceKey<PlacedFeature> HEMATITE_ORE_PLACED_HILLS_MOUNT_KEY = registerKey("hematite_ore_hills_mount_placed");

    public static final ResourceKey<PlacedFeature> MAGNETITE_ORE_PLACED_KEY = registerKey("magnetite_ore_placed");
    public static final ResourceKey<PlacedFeature> MAGNETITE_ORE_PLACED_DEEP_KEY = registerKey("magnetite_ore_deep_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, BAUXITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_BAUXITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(48), VerticalAnchor.absolute(128))));
        register(context, BAUXITE_ORE_PLACED_JUNGLE_SAVANNA_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_BAUXITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(48), VerticalAnchor.absolute(128))));

        register(context, SALT_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SALT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(80))));
        register(context, SALT_ORE_PLACED_SHORE_DESERT_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_SALT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(80))));

        register(context, HEMATITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_HEMATITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(64))));
        register(context, HEMATITE_ORE_PLACED_HILLS_MOUNT_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_HEMATITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(64))));

        register(context, MAGNETITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_MAGNETITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));
        register(context, MAGNETITE_ORE_PLACED_DEEP_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_MAGNETITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
