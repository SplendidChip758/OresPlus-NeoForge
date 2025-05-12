package com.splendidchip.oresplus.block;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.custom.GrinderBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.neoforged.bus.api.IEventBus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OresPlus.MOD_ID);

    //Ores
    public static final DeferredBlock<Block> BAUXITE_ORE = BLOCKS.registerBlock("bauxite_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(2.5f, 3f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> SALT_ORE = BLOCKS.registerBlock("salt_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.CALCITE));

    public static final DeferredBlock<Block> HEMATITE_ORE = BLOCKS.registerBlock("hematite_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(3f, 3f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> MAGNETITE_ORE = BLOCKS.registerBlock("magnetite_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(4.5f,3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));

    //BLocks
    public static final DeferredBlock<Block> ALUMINUM_BLOCK = BLOCKS.registerBlock("aluminum_block",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL));

    //Block Entities
    public static final DeferredBlock<GrinderBlock> GRINDER_BLOCK = BLOCKS.registerBlock("grinder_block",
            GrinderBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    //Test Blocks
    public static final DeferredBlock<Block> TEST_BLOCK_1 = BLOCKS.registerBlock("test_block_1",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));

    public static final DeferredBlock<Block> TEST_BLOCK_2 = BLOCKS.registerBlock("test_block_2",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> TEST_BLOCK_3 = BLOCKS.registerBlock("test_block_3",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.BASALT));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
