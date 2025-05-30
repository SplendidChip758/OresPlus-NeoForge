package com.splendidchip.oresplus.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class BranchingCoalVeinFeature extends Feature<OreConfiguration> {

    public BranchingCoalVeinFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int totalBranches = 2 + random.nextInt(2); // 2–3 branches
        int branchLength = 40 + random.nextInt(60); // up to 100 blocks

        for (int branch = 0; branch < totalBranches; branch++) {
            BlockPos current = origin.offset(
                    random.nextInt(12) - 6,
                    random.nextInt(8) - 4,
                    random.nextInt(12) - 6
            );

            for (int i = 0; i < branchLength; i++) {
                int radius = random.nextInt(2); // 1–2 blocks wide

                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {
                        for (int dz = -radius; dz <= radius; dz++) {
                            // Use a spherical shape for more organic branching
                            if (dx * dx + dy * dy + dz * dz > radius * radius) continue;

                            BlockPos pos = current.offset(dx, dy, dz);
                            BlockState state = level.getBlockState(pos);
                            if (state.is(Blocks.STONE)) {
                                level.setBlock(pos, Blocks.COAL_ORE.defaultBlockState(), 2);
                            }
                        }
                    }
                }

                current = current.offset(
                        random.nextInt(3) - 1,
                        random.nextInt(2) - 1,
                        random.nextInt(3) - 1
                );
            }
        }

        return true;
    }

}
