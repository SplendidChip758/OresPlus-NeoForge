package com.splendidchip.oresplus.block.entity;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, OresPlus.MOD_ID);

    public static final Supplier<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "crusher_block_entity",
            () -> new BlockEntityType<>(
                    CrusherBlockEntity::new,
                    ModBlocks.CRUSHER_BLOCK.get()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
