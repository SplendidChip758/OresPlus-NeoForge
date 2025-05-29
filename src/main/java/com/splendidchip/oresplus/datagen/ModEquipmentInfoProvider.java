package com.splendidchip.oresplus.datagen;

import com.splendidchip.oresplus.OresPlus;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEquipmentInfoProvider implements DataProvider {

    private final PackOutput.PathProvider path;

    public ModEquipmentInfoProvider(PackOutput output) {
        this.path = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private void add(BiConsumer<ResourceLocation, EquipmentClientInfo> registrar) {
        registrar.accept(
                // Must match Equippable#assetId
                ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "steel"),
                EquipmentClientInfo.builder()
                        // For humanoid head, chest, and feet
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID,
                                // Base texture
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "steel"),
                                        Optional.empty(),
                                        false
                                )
                        )
                        // For humanoid legs
                        .addLayers(
                                EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                                new EquipmentClientInfo.Layer(
                                        ResourceLocation.fromNamespaceAndPath(OresPlus.MOD_ID, "steel"),
                                        Optional.empty(),
                                        false
                                )
                        )
                        .build()
        );
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<ResourceLocation, EquipmentClientInfo> map = new HashMap<>();
        this.add((name, info) -> {
            if (map.putIfAbsent(name, info) != null) {
                throw new IllegalStateException("Tried to register equipment client info twice for id: " + name);
            }
        });
        return DataProvider.saveAll(cachedOutput, EquipmentClientInfo.CODEC, this.path, map);
    }

    @Override
    public String getName() {
        return "Equipment Client Infos: " + OresPlus.MOD_ID;
    }
}
