package com.splendidchip.oresplus.block.custom;

import com.mojang.serialization.MapCodec;
import com.splendidchip.oresplus.block.entity.ModBlockEntities;
import com.splendidchip.oresplus.block.entity.custom.SimpleSmelterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SimpleSmelterBlock extends BaseEntityBlock {
    public static final MapCodec<SimpleKilnBlock> CODEC = simpleCodec(SimpleKilnBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SimpleSmelterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, net.minecraft.core.Direction.NORTH).setValue(LIT, false));
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
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SimpleSmelterBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SimpleSmelterBlockEntity simpleSmelterBlockEntity) {
                simpleSmelterBlockEntity.drops();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                          Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SimpleSmelterBlockEntity controller) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(controller, Component.literal("Simple Smelter")), pos);
            } else {
                throw new IllegalStateException("Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.SIMPLE_SMELTER_BLOCK_ENTITY.get(),
                (level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level1, blockPos, blockState, blockEntity));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(BlockStateProperties.LIT)) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f , 1.0f, false);
        }

        Direction facing = state.getValue(FACING);
        Direction.Axis axis = facing.getAxis();

        double offset = random.nextDouble() * 0.6 - 0.3;
        double dx = axis == Direction.Axis.X ? facing.getStepX() * 0.52 : offset;
        double dz = axis == Direction.Axis.Z ? facing.getStepZ() * 0.52 : offset;
        double dy = random.nextDouble() * 6.0 / 16.0;

        level.addParticle(ParticleTypes.SMOKE, x + dx, y + dy, z + dz, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + dx, y + dy, z + dz, 0, 0, 0);

        double xTop = pos.getX() + 0.5 + (random.nextGaussian() * 0.1);
        double yTop = y + 1.0 + (random.nextDouble() * 0.1);
        double zTop = pos.getZ() + 0.5 + (random.nextGaussian() * 0.1);

        level.addParticle(ParticleTypes.SMOKE, xTop, yTop, zTop, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, xTop, yTop, zTop, 0, 0, 0);


    }
}
