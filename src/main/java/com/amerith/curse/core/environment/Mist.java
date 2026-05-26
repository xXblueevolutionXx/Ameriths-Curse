package com.amerith.curse.core.environment;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import com.amerith.curse.core.mechanics.FogRenderHandler;
import com.amerith.curse.handler.MistCommandHandler;
import com.amerith.curse.block.custom.CursedGrass;
import com.amerith.curse.world.generation.GrassReplacementHandler;
import com.amerith.curse.world.generation.BirchRemovalHandler;

/**
 * The core entry master class for Amerith's Curse.
 * Injects the permanent blinding supernatural fog system into the game loop.
 */
@Mod(modid = Mist.MODID, name = Mist.NAME, version = Mist.VERSION)
public class Mist {
    public static final String MODID = "amerithcurse";
    public static final String NAME = "Amerith's Curse";
    public static final String VERSION = "1.0.0";
    
    // Global mist state toggle
    public static boolean mistEnabled = true;
    
    // Cursed Grass block instance
    public static CursedGrass cursedGrass;

    /**
     * Runs instantly during the mod's initial boot sequence.
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        // 🔥 ALWAYS ON: Registering the fog handler to the global Forge Event Bus!
        // This locks down your custom OpenGL fog math at all times.
        MinecraftForge.EVENT_BUS.register(new FogRenderHandler());
        
        // Initialize Cursed Grass block
        cursedGrass = new CursedGrass();
        
        // Register the grass replacement handler to replace vanilla grass with cursed grass
        MinecraftForge.EVENT_BUS.register(new GrassReplacementHandler(cursedGrass));
        
        // Register the birch removal handler to erase all birch from world gen
        MinecraftForge.EVENT_BUS.register(new BirchRemovalHandler());
    }

    /**
     * Register commands when server starts (only in multiplayer/server mode).
     */
    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        // Register the /wrld mist command
        server.getCommandManager().registerCommand(new MistCommandHandler());
    }
}
