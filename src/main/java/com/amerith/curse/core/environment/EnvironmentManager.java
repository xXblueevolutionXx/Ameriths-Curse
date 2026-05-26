package com.amerith.curse.core.environment;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.GameRules;

/**
 * Handles the advanced gothic environment engine for Amerith's Curse.
 * Locks game rules, freezes world clocks, and forces pitch black conditions.
 */
public class EnvironmentManager {

    private final Minecraft mc;
    private static final long GOTHIC_MIDNIGHT_TICK = 18000L; // 18000 is absolute dead midnight 🌌

    public EnvironmentManager() {
        this.mc = Minecraft.getMinecraft();
    }

    /**
     * Call this function during the initial world-load phase or player join event.
     * This stops the sun from moving at the server/world rules layer!
     */
    public void initializeCurseRules(WorldClient world) {
        if (world == null) return;

        GameRules rules = world.getGameRules();
        
        // ⏳ Freeze the daylight cycle game rule completely so the engine stops counting time forward
        rules.setOrCreateGameRule("doDaylightCycle", "false");
        
        // Set the foundational time baseline right off the bat
        world.setWorldTime(GOTHIC_MIDNIGHT_TICK);
    }

    /**
     * Primary loop engine. Run this inside your client tick or player update event handler
     * to enforce absolute darkness and supernatural fog.
     */
    public void executeEternalNightLoop() {
        WorldClient world = mc.world;
        EntityPlayerSP player = mc.player;

        if (world == null || player == null) return;

        // Safety verification: Force lock the exact tick position if anything tries to shift it
        if (world.getWorldTime() != GOTHIC_MIDNIGHT_TICK) {
            world.setWorldTime(GOTHIC_MIDNIGHT_TICK);
        }

        // Apply supernatural Blinding Mist layer (Level 1 Blindness)
        // Check if effect is missing or about to expire (< 20 ticks remaining)
        PotionEffect blindnessEffect = player.getActivePotionEffect(MobEffects.BLINDNESS);
        if (blindnessEffect == null || blindnessEffect.getDuration() < 20) {
            // 200 ticks length, level 0 (amplifier), ambient true, particles hidden
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0, true, false));
        }
        
        // 🌧️ BONUS CRITICAL EFFECT: Lock the weather to stormy/overcast 
        // This cuts down ambient star glow and maximizes the pitch black landscape atmosphere
        if (!world.isRaining() || !world.isThundering()) {
            world.getWorldInfo().setRaining(true);
            world.getWorldInfo().setThundering(true);
        }
    }
}
