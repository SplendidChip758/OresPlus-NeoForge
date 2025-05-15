package com.splendidchip.oresplus.worldgen;

import com.nimbusds.jose.util.Resource;
import com.splendidchip.oresplus.OresPlus;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_BAUXITE_ORE = registerKey("add_bauxite_ore");
    public static final ResourceKey<BiomeModifier> ADD_BAUXITE_JUNGLE_SAVANNA_ORE = registerKey("add_bauxite_jungle_savanna_ore");

    public static final ResourceKey<BiomeModifier> ADD_SALT_ORE = registerKey("add_salt_ore");
    public static final ResourceKey<BiomeModifier> ADD_SALT_SHORE_DESERT_ORE = registerKey("add_salt_shore_desert_ore");

    public static final ResourceKey<BiomeModifier> ADD_HEMATITE_ORE = registerKey("add_hematite_ore");
    public static final ResourceKey<BiomeModifier> ADD_HEMATITE_HILLS_MOUNT_ORE = registerKey("add_hematite_hills_mount_ore");

    public static final ResourceKey<BiomeModifier> ADD_MAGNETITE_ORE = registerKey("add_magnetite_ore");
    public static final ResourceKey<BiomeModifier> ADD_MAGNETITE_DEEP_ORE = registerKey("add_magnetite_deep_ore");

    public static final ResourceKey<BiomeModifier> ADD_LIMESTONE_ORE = registerKey("add_limestone_ore");

    public static final ResourceKey<BiomeModifier> REMOVE_IRON_ORE = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "remove_iron_ore"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(REMOVE_IRON_ORE, new BiomeModifiers.RemoveFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(OrePlacements.ORE_IRON_MIDDLE),
                        placedFeatures.getOrThrow(OrePlacements.ORE_IRON_SMALL),
                        placedFeatures.getOrThrow(OrePlacements.ORE_IRON_UPPER)),
                Set.of(
                        GenerationStep.Decoration.UNDERGROUND_ORES,
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        ));

        context.register(ADD_BAUXITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BAUXITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_BAUXITE_JUNGLE_SAVANNA_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.JUNGLE),
                        biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                        biomes.getOrThrow(Biomes.BAMBOO_JUNGLE),
                        biomes.getOrThrow(Biomes.SAVANNA),
                        biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                        biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BAUXITE_ORE_PLACED_JUNGLE_SAVANNA_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SALT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SALT_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SALT_SHORE_DESERT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.BEACH),
                        biomes.getOrThrow(Biomes.STONY_SHORE),
                        biomes.getOrThrow(Biomes.OCEAN),
                        biomes.getOrThrow(Biomes.LUKEWARM_OCEAN),
                        biomes.getOrThrow(Biomes.WARM_OCEAN),
                        biomes.getOrThrow(Biomes.DESERT)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SALT_ORE_PLACED_SHORE_DESERT_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_HEMATITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.HEMATITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_HEMATITE_HILLS_MOUNT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.WINDSWEPT_HILLS),
                        biomes.getOrThrow(Biomes.STONY_PEAKS),
                        biomes.getOrThrow(Biomes.JAGGED_PEAKS),
                        biomes.getOrThrow(Biomes.FROZEN_PEAKS),
                        biomes.getOrThrow(Biomes.STONY_PEAKS),
                        biomes.getOrThrow(Biomes.MEADOW),
                        biomes.getOrThrow(Biomes.GROVE)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.HEMATITE_ORE_PLACED_HILLS_MOUNT_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MAGNETITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MAGNETITE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MAGNETITE_DEEP_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.DRIPSTONE_CAVES),
                        biomes.getOrThrow(Biomes.DEEP_DARK),
                        biomes.getOrThrow(Biomes.LUSH_CAVES)

                        ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MAGNETITE_ORE_PLACED_DEEP_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_LIMESTONE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LIMESTONE_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, name));
    }
}
