package com.splendidchip.oresplus.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import com.splendidchip.oresplus.block.ModBlocks;

public class MultiblockStructureValidator {

    public static boolean validateHollowCubeWithControllerOnFront(Level level, BlockPos controllerPos, Block casingBlock) {
        BlockState controllerState = level.getBlockState(controllerPos);

        // Get the controller's facing direction (e.g. NORTH, SOUTH, WEST, EAST)
        Direction facing = controllerState.getValue(BlockStateProperties.HORIZONTAL_FACING);

        // Center of the cube is 1 block *behind* the controller
        BlockPos center = controllerPos.relative(facing.getOpposite());

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos current = center.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(current);

                    boolean isShell = Math.abs(dx) == 1 || Math.abs(dy) == 1 || Math.abs(dz) == 1;

                    if (current.equals(controllerPos)) {
                        // Allow controller block at correct front-center position
                        if (!state.is(controllerState.getBlock())) {
                            return false;
                        }
                    } else if (isShell) {
                        // Allow either casing block or IO block as shell
                        if (!state.is(casingBlock) && !state.is(ModBlocks.SMELTER_IO_BLOCK.get())) {
                            return false;
                        }
                    } else {
                        if (!state.isAir()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
