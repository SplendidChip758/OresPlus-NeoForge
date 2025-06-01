package com.splendidchip.oresplus.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import com.splendidchip.oresplus.block.entity.SmelterIOBlockEntity;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import com.splendidchip.oresplus.item.ModItems;
import net.minecraft.world.item.Item;

public class SmelterIOBlock extends BaseEntityBlock {
    public static final MapCodec<SmelterIOBlock> CODEC = simpleCodec(SmelterIOBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<SmelterIOBlockEntity.Mode> MODE = EnumProperty.create("mode", SmelterIOBlockEntity.Mode.class);

    public SmelterIOBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(MODE, SmelterIOBlockEntity.Mode.INPUT));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, MODE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SmelterIOBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {

        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SmelterIOBlockEntity ioBlockEntity) {
                Item held = player.getItemInHand(hand).getItem();
                if (held == ModItems.PIG_IRON_WRENCH.get() || held == ModItems.STEEL_WRENCH.get()) {
                    ioBlockEntity.toggleMode();
                    player.displayClientMessage(Component.translatable("block.oresplus.smelter_io_block.mode_" + ioBlockEntity.getMode().name().toLowerCase()), true);
                    return InteractionResult.SUCCESS;
                } else {
                    player.displayClientMessage(Component.translatable("block.oresplus.smelter_io_block.need_wrench"), true);
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
