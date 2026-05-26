package com.amerith.curse.world.generation;

import net.minecraftforge.fml.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import com.amerith.curse.block.custom.CursedGrass;

/**
 * World generation handler that replaces all vanilla grass blocks with Cursed Grass.
 * Hooks into chunk load/generation events to scan and replace blocks.
 */
@Subscribe
public class GrassReplacementHandler {

    private static CursedGrass cursedGrass;

    public GrassReplacementHandler(CursedGrass cursedGrassBlock) {
        this.cursedGrass = cursedGrassBlock;
    }

    /**
     * Replace grass blocks when a chunk is loaded/generated.
     * This ensures all chunks get the cursed grass treatment.
     */
    @Subscribe
    public void onChunkLoad(ChunkEvent.Load event) {
        if (event.getWorld().isRemote) {
            return; // Client side, don't process
        }

        Chunk chunk = event.getChunk();
        replaceGrassInChunk(chunk);
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
        replaceGrassInChunk(chunk);
    }

    /**
     * Scans a chunk and replaces all grass blocks with cursed grass.
     * Grass typically grows from Y=0 to Y=256, but we focus on surface level (Y=60-90 average).
     */
    private void replaceGrassInChunk(Chunk chunk) {
        World world = chunk.getWorld();
        
        // Iterate through the chunk's blocks
        for (int x = chunk.getxPosition() << 4; x < (chunk.getxPosition() << 4) + 16; x++) {
            for (int z = chunk.getzPosition() << 4; z < (chunk.getzPosition() << 4) + 16; z++) {
                // Check surface level blocks (Y from 0 to 256)
                for (int y = 0; y < 256; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    
                    // Replace vanilla grass with cursed grass
                    if (world.getBlockState(pos).getBlock() == Blocks.GRASS) {
                        world.setBlockState(pos, cursedGrass.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
