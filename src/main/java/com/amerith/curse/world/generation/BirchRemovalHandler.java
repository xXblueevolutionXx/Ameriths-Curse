package com.amerith.curse.world.generation;

import net.minecraftforge.fml.common.eventbus.Subscribe;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;

/**
 * World generation handler that removes all birch trees from the world.
 * Erases birch logs, leaves, and saplings during chunk load/generation.
 */
@Subscribe
public class BirchRemovalHandler {

    /**
     * Remove birch blocks when a chunk is loaded/generated.
     */
    @Subscribe
    public void onChunkLoad(ChunkEvent.Load event) {
        if (event.getWorld().isRemote) {
            return; // Client side, don't process
        }

        Chunk chunk = event.getChunk();
        removeBirchFromChunk(chunk);
    }

    /**
     * Also handle newly generated chunks.
     */
    @Subscribe
    public void onChunkGenerated(ChunkEvent.Generated event) {
        if (event.getWorld().isRemote) {
            return;
        }

        Chunk chunk = event.getChunk();
        removeBirchFromChunk(chunk);
    }

    /**
     * Scans a chunk and removes all birch blocks (logs, leaves, saplings).
     */
    private void removeBirchFromChunk(Chunk chunk) {
        World world = chunk.getWorld();
        
        // Iterate through the chunk's blocks
        for (int x = chunk.getxPosition() << 4; x < (chunk.getxPosition() << 4) + 16; x++) {
            for (int z = chunk.getzPosition() << 4; z < (chunk.getzPosition() << 4) + 16; z++) {
                // Check all heights
                for (int y = 0; y < 256; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    
                    // Remove birch logs
                    if (world.getBlockState(pos).getBlock() == Blocks.LOG) {
                        if (isBirchLog(world, pos)) {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                    
                    // Remove birch leaves
                    if (world.getBlockState(pos).getBlock() == Blocks.LEAVES) {
                        if (isBirchLeaves(world, pos)) {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                    
                    // Remove birch saplings
                    if (world.getBlockState(pos).getBlock() == Blocks.SAPLING) {
                        if (isBirchSapling(world, pos)) {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if a log block is birch.
     * Birch logs have variant value 2 in 1.12.
     */
    private boolean isBirchLog(World world, BlockPos pos) {
        try {
            int variant = world.getBlockState(pos).getValue(Blocks.LOG.getPropertyKeys().iterator().next());
            return variant == 2; // Birch
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a leaves block is birch.
     * Birch leaves have variant value 2 in 1.12.
     */
    private boolean isBirchLeaves(World world, BlockPos pos) {
        try {
            int variant = world.getBlockState(pos).getValue(Blocks.LEAVES.getPropertyKeys().iterator().next());
            return variant == 2; // Birch
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a sapling is birch.
     * Birch saplings have variant value 2 in 1.12.
     */
    private boolean isBirchSapling(World world, BlockPos pos) {
        try {
            int variant = world.getBlockState(pos).getValue(Blocks.SAPLING.getPropertyKeys().iterator().next());
            return variant == 2; // Birch
        } catch (Exception e) {
            return false;
        }
    }
}
