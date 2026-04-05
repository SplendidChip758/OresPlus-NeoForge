package com.splendidchip.oresplus.block.custom;

import com.mojang.serialization.MapCodec;
import com.splendidchip.oresplus.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.splendidchip.oresplus.block.entity.custom.ItemPipeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.splendidchip.oresplus.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class ItemPipeBlock extends BaseEntityBlock {
    public static final MapCodec<ItemPipeBlock> CODEC = simpleCodec(ItemPipeBlock::new);

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH_INPUT = BooleanProperty.create("north_input");
    public static final BooleanProperty EAST_INPUT = BooleanProperty.create("east_input");
    public static final BooleanProperty SOUTH_INPUT = BooleanProperty.create("south_input");
    public static final BooleanProperty WEST_INPUT = BooleanProperty.create("west_input");
    public static final BooleanProperty UP_INPUT = BooleanProperty.create("up_input");
    public static final BooleanProperty DOWN_INPUT = BooleanProperty.create("down_input");

    // Static shapes for the pipe core and each directional arm.
    private static final VoxelShape CORE = Shapes.box(5/16d, 5/16d, 5/16d, 11/16d, 11/16d, 11/16d);
    private static final VoxelShape NORTH_SHAPE = Shapes.box(6/16d, 6/16d, 0, 10/16d, 10/16d, 5/16d);
    private static final VoxelShape SOUTH_SHAPE = Shapes.box(6/16d, 6/16d, 11/16d, 10/16d, 10/16d, 1);
    private static final VoxelShape EAST_SHAPE = Shapes.box(11/16d, 6/16d, 6/16d, 1, 10/16d, 10/16d);
    private static final VoxelShape WEST_SHAPE = Shapes.box(0, 6/16d, 6/16d, 5/16d, 10/16d, 10/16d);
    private static final VoxelShape UP_SHAPE = Shapes.box(6/16d, 11/16d, 6/16d, 10/16d, 1, 10/16d);
    private static final VoxelShape DOWN_SHAPE = Shapes.box(6/16d, 0, 6/16d, 10/16d, 5/16d, 10/16d);
    private static final double ARM_HIT_EPSILON = 1.0E-4D;
    
    private static VoxelShape makeShape(BlockState state) {
        VoxelShape shape = CORE;
        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(EAST)) shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(WEST)) shape = Shapes.or(shape, WEST_SHAPE);
        if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPE);
        return shape;
    }

    public ItemPipeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH_INPUT, false)
                .setValue(EAST_INPUT, false)
                .setValue(SOUTH_INPUT, false)
                .setValue(WEST_INPUT, false)
                .setValue(UP_INPUT, false)
                .setValue(DOWN_INPUT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN,
                NORTH_INPUT, EAST_INPUT, SOUTH_INPUT, WEST_INPUT, UP_INPUT, DOWN_INPUT);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateConnections(context.getLevel(), context.getClickedPos(), this.defaultBlockState());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess,
                                     BlockPos currentPos, Direction direction, BlockPos neighborPos,
                                     BlockState neighborState, RandomSource random) {
        boolean connected = connectsTo(level, neighborPos)
                && (isPipeNeighbor(level, currentPos, direction) || isSideEnabled(level, currentPos, direction));
        return state
                .setValue(propertyFor(direction), connected)
                .setValue(inputPropertyFor(direction), connected && state.getValue(inputPropertyFor(direction)));
    }

    private static BooleanProperty propertyFor(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
        };
    }

    private static BooleanProperty inputPropertyFor(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_INPUT;
            case EAST -> EAST_INPUT;
            case SOUTH -> SOUTH_INPUT;
            case WEST -> WEST_INPUT;
            case UP -> UP_INPUT;
            case DOWN -> DOWN_INPUT;
        };
    }

    private static BlockState updateConnections(LevelAccessor level, BlockPos pos, BlockState state) {
        boolean north = connectsTo(level, pos.north()) && (isPipeNeighbor(level, pos, Direction.NORTH) || isSideEnabled(level, pos, Direction.NORTH));
        boolean east = connectsTo(level, pos.east()) && (isPipeNeighbor(level, pos, Direction.EAST) || isSideEnabled(level, pos, Direction.EAST));
        boolean south = connectsTo(level, pos.south()) && (isPipeNeighbor(level, pos, Direction.SOUTH) || isSideEnabled(level, pos, Direction.SOUTH));
        boolean west = connectsTo(level, pos.west()) && (isPipeNeighbor(level, pos, Direction.WEST) || isSideEnabled(level, pos, Direction.WEST));
        boolean up = connectsTo(level, pos.above()) && (isPipeNeighbor(level, pos, Direction.UP) || isSideEnabled(level, pos, Direction.UP));
        boolean down = connectsTo(level, pos.below()) && (isPipeNeighbor(level, pos, Direction.DOWN) || isSideEnabled(level, pos, Direction.DOWN));

        return state
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(UP, up)
                .setValue(DOWN, down)
                .setValue(NORTH_INPUT, north && state.getValue(NORTH_INPUT))
                .setValue(EAST_INPUT, east && state.getValue(EAST_INPUT))
                .setValue(SOUTH_INPUT, south && state.getValue(SOUTH_INPUT))
                .setValue(WEST_INPUT, west && state.getValue(WEST_INPUT))
                .setValue(UP_INPUT, up && state.getValue(UP_INPUT))
                .setValue(DOWN_INPUT, down && state.getValue(DOWN_INPUT));
    }

    private static boolean isPipeNeighbor(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);
        return neighborState.getBlock() instanceof ItemPipeBlock;
    }

    private static boolean isSideEnabled(BlockGetter level, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ItemPipeBlockEntity pipeEntity) {
            return pipeEntity.getConnectionMode(direction) != ItemPipeBlockEntity.ConnectionMode.NONE;
        }
        return true;
    }

    private static boolean connectsTo(BlockGetter level, BlockPos neighborPos) {
        BlockState neighborState = level.getBlockState(neighborPos);
        
        // Connect to other item pipes.
        if (neighborState.getBlock() instanceof ItemPipeBlock) {
            return true;
        }
        
        // Connect to blocks exposing a vanilla inventory interface.
        BlockEntity blockEntity = level.getBlockEntity(neighborPos);
        if (blockEntity instanceof WorldlyContainer || blockEntity instanceof Container) {
            return true;
        }
        
        return false;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                         Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ItemPipeBlockEntity pipeEntity) {
                ItemStack held = player.getItemInHand(hand);
                if (held.getItem() == ModItems.PIG_IRON_WRENCH.get() || held.getItem() == ModItems.STEEL_WRENCH.get()) {
                    // Configure the clicked arm when possible, otherwise fall back to the hit face.
                    Direction configDirection = determineConfigDirection(state, hitResult);

                    if (isPipeNeighbor(level, pos, configDirection)) {
                        player.displayClientMessage(Component.literal("§cPipe-to-pipe connections are fixed."), true);
                        return InteractionResult.SUCCESS;
                    }

                    pipeEntity.cycleConnectionMode(configDirection);

                    ItemPipeBlockEntity.ConnectionMode newMode = pipeEntity.getConnectionMode(configDirection);
                    player.displayClientMessage(
                        Component.literal("§6Pipe §f" + configDirection.getName() + " §6set to: §f" + newMode.getSerializedName().toUpperCase()),
                        true
                    );

                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
    
    private static Direction determineConfigDirection(BlockState state, BlockHitResult hitResult) {
        Vec3 blockLocalHit = hitResult.getLocation().subtract(
                hitResult.getBlockPos().getX(),
                hitResult.getBlockPos().getY(),
                hitResult.getBlockPos().getZ());

        Direction bestDirection = null;
        double bestDistance = Double.MAX_VALUE;

        for (Direction direction : Direction.values()) {
            if (!state.getValue(propertyFor(direction))) {
                continue;
            }

            AABB bounds = armBounds(direction).inflate(ARM_HIT_EPSILON);
            if (bounds.contains(blockLocalHit)) {
                double distance = squaredDistanceToCenter(bounds, blockLocalHit);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestDirection = direction;
                }
            }
        }

        return bestDirection != null ? bestDirection : hitResult.getDirection();
    }

    private static AABB armBounds(Direction direction) {
        return switch (direction) {
            case NORTH -> new AABB(6 / 16d, 6 / 16d, 0, 10 / 16d, 10 / 16d, 5 / 16d);
            case SOUTH -> new AABB(6 / 16d, 6 / 16d, 11 / 16d, 10 / 16d, 10 / 16d, 1);
            case EAST -> new AABB(11 / 16d, 6 / 16d, 6 / 16d, 1, 10 / 16d, 10 / 16d);
            case WEST -> new AABB(0, 6 / 16d, 6 / 16d, 5 / 16d, 10 / 16d, 10 / 16d);
            case UP -> new AABB(6 / 16d, 11 / 16d, 6 / 16d, 10 / 16d, 1, 10 / 16d);
            case DOWN -> new AABB(6 / 16d, 0, 6 / 16d, 10 / 16d, 5 / 16d, 10 / 16d);
        };
    }

    private static double squaredDistanceToCenter(AABB bounds, Vec3 point) {
        double dx = point.x - (bounds.minX + bounds.maxX) * 0.5D;
        double dy = point.y - (bounds.minY + bounds.maxY) * 0.5D;
        double dz = point.z - (bounds.minZ + bounds.maxZ) * 0.5D;
        return dx * dx + dy * dy + dz * dz;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        
        return createTickerHelper(blockEntityType, ModBlockEntities.ITEM_PIPE_BLOCK_ENTITY.get(),
                (level1, blockPos, blockState, blockEntity) -> ItemPipeBlockEntity.tick(level1, blockPos, blockState, blockEntity));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemPipeBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        return makeShape(state);
    }

    @Override
    public net.minecraft.world.phys.shapes.VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        return makeShape(state);
    }
}































