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

    //Blocks
    public static final DeferredBlock<Block> BAUXITE_ORE = BLOCKS.registerBlock("bauxite_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> ALUMINUM_BLOCK = BLOCKS.registerBlock("aluminum_block",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL));

    public static final DeferredBlock<Block> SALT_ORE = BLOCKS.registerBlock("salt_ore",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.CALCITE));

    //Block Entities
    public static final DeferredBlock<GrinderBlock> GRINDER_BLOCK = BLOCKS.registerBlock("grinder_block",
            GrinderBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops());

    //Test Blocks
    public static final DeferredBlock<Block> TEST_BLOCK_1 = BLOCKS.registerBlock("test_block_1",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.CALCITE));

    public static final DeferredBlock<Block> TEST_BLOCK_2 = BLOCKS.registerBlock("test_block_2",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.CALCITE));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
