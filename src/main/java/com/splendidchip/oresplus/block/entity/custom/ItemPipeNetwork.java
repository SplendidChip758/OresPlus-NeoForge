package com.splendidchip.oresplus.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lightweight pipe graph traversal for item routes.
 * Keeps network discovery separate from extraction/insertion rules.
 */
public final class ItemPipeNetwork {
    private final Level level;
    private final BlockPos startPipePos;

    private ItemPipeNetwork(Level level, BlockPos startPipePos) {
        this.level = level;
        this.startPipePos = startPipePos;
    }

    public static ItemPipeNetwork from(Level level, BlockPos startPipePos) {
        return new ItemPipeNetwork(level, startPipePos);
    }

    public List<OutputEndpoint> collectOutputEndpoints(BlockPos sourceInventoryPos) {
        List<OutputEndpoint> outputs = new ArrayList<>();
        Deque<BlockPos> open = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        open.add(startPipePos);
        visited.add(startPipePos);

        while (!open.isEmpty()) {
            BlockPos pipePos = open.removeFirst();
            BlockEntity blockEntity = level.getBlockEntity(pipePos);
            if (!(blockEntity instanceof ItemPipeBlockEntity pipe)) {
                continue;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pipePos.relative(direction);
                BlockEntity neighborEntity = level.getBlockEntity(neighborPos);

                if (neighborEntity instanceof ItemPipeBlockEntity) {
                    if (visited.add(neighborPos)) {
                        open.addLast(neighborPos);
                    }
                    continue;
                }

                if (pipe.getConnectionMode(direction) == ItemPipeBlockEntity.ConnectionMode.OUTPUT
                        && !neighborPos.equals(sourceInventoryPos)) {
                    outputs.add(new OutputEndpoint(pipePos, neighborPos, direction.getOpposite()));
                }
            }
        }

        // Keep endpoint eligibility unchanged, but enforce a stable priority order.
        outputs.sort(Comparator
                .comparingInt((OutputEndpoint endpoint) -> endpoint.targetPos().getX())
                .thenComparingInt(endpoint -> endpoint.targetPos().getY())
                .thenComparingInt(endpoint -> endpoint.targetPos().getZ())
                .thenComparingInt(endpoint -> endpoint.insertionFace().get3DDataValue())
                .thenComparingInt(endpoint -> endpoint.pipePos().getX())
                .thenComparingInt(endpoint -> endpoint.pipePos().getY())
                .thenComparingInt(endpoint -> endpoint.pipePos().getZ()));

        return outputs;
    }

    public record OutputEndpoint(BlockPos pipePos, BlockPos targetPos, Direction insertionFace) {
    }
}
