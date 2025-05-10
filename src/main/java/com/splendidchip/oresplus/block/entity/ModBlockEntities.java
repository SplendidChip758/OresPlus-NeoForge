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

    public static final Supplier<BlockEntityType<GrinderBlockEntity>> GRINDER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "grinder_block_entity",
            () -> new BlockEntityType<>(
                    GrinderBlockEntity::new,
                    ModBlocks.GRINDER_BLOCK.get()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
