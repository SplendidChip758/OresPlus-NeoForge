package com.splendidchip.oresplus.block;

import com.splendidchip.oresplus.OresPlus;
import com.splendidchip.oresplus.block.custom.CrusherBlock;
import com.splendidchip.oresplus.block.custom.SimpleKilnBlock;
import com.splendidchip.oresplus.block.custom.SimpleSmelterBlock;
import com.splendidchip.oresplus.block.custom.SmelterControllerBlock;
import com.splendidchip.oresplus.block.custom.SmelterIOBlock;
import com.splendidchip.oresplus.block.custom.ItemPipeBlock;
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
                    .strength(2.5f, 3f).requiresCorrectToolForDrops().sound(SoundType.CALCITE));

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
                    .strength(5f, 6f).requiresCorrectToolForDrops().sound(SoundType.METAL));

    public static final DeferredBlock<Block> LIMESTONE_BLOCK = BLOCKS.registerBlock("limestone_block",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(1.5f, 6f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> REFRACTORY_BRICKS = BLOCKS.registerBlock("refractory_bricks",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(2.0F, 6.0F).requiresCorrectToolForDrops());

    //Block Entities
    public static final DeferredBlock<CrusherBlock> CRUSHER_BLOCK = BLOCKS.registerBlock("crusher_block",
            CrusherBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredBlock<SimpleKilnBlock> SIMPLE_KILN_BLOCK = BLOCKS.registerBlock("simple_kiln_block",
            SimpleKilnBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredBlock<SimpleSmelterBlock> SIMPLE_SMELTER_BLOCK = BLOCKS.registerBlock("simple_smelter_block",
            SimpleSmelterBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredBlock<SmelterControllerBlock> SMELTER_CONTROLLER_BLOCK = BLOCKS.registerBlock("smelter_controller_block",
            SmelterControllerBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredBlock<SmelterIOBlock> SMELTER_IO_BLOCK = BLOCKS.registerBlock("smelter_io_block",
            SmelterIOBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(3.5F).requiresCorrectToolForDrops().noOcclusion());

    public static final DeferredBlock<ItemPipeBlock> ITEM_PIPE_BLOCK = BLOCKS.registerBlock("item_pipe_block",
            ItemPipeBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(2.0F).requiresCorrectToolForDrops().noOcclusion());

    //Test Blocks
    public static final DeferredBlock<Block> TEST_BLOCK_1 = BLOCKS.registerBlock("test_block_1",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> TEST_BLOCK_2 = BLOCKS.registerBlock("test_block_2",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> TEST_BLOCK_3 = BLOCKS.registerBlock("test_block_3",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static final DeferredBlock<Block> TEST_BLOCK_4 = BLOCKS.registerBlock("test_block_4",
            Block::new,
            BlockBehaviour.Properties.of()
                    .strength(5f).requiresCorrectToolForDrops().sound(SoundType.STONE));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
