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

    public static final Supplier<BlockEntityType<SimpleKilnBlockEntity>> SIMPLE_KILN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "simple_kiln_block_entity",
            () -> new BlockEntityType<>(
                    SimpleKilnBlockEntity::new,
                    ModBlocks.SIMPLE_KILN_BLOCK.get()
            )
    );

    public static final Supplier<BlockEntityType<SimpleSmelterBlockEntity>> SIMPLE_SMELTER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "simple_smelter_block_entity",
            () -> new BlockEntityType<>(
                    SimpleSmelterBlockEntity::new,
                    ModBlocks.SIMPLE_SMELTER_BLOCK.get()
            )
    );

    public static final Supplier<BlockEntityType<SmelterControllerBlockEntity>> SMELTER_CONTROLLER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "smelter_controller_block_entity",
            () -> new BlockEntityType<>(
                    SmelterControllerBlockEntity::new,
                    ModBlocks.SMELTER_CONTROLLER_BLOCK.get()
            )
    );

    public static final Supplier<BlockEntityType<SmelterIOBlockEntity>> SMELTER_IO_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "smelter_io_block_entity",
            () -> new BlockEntityType<>(
                    SmelterIOBlockEntity::new,
                    ModBlocks.SMELTER_IO_BLOCK.get()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
