package com.amerith.curse.core.mechanics;

import net.minecraftforge.fml.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import com.amerith.curse.core.environment.Mist;

/**
 * Handles the custom fog rendering for the perpetual mist effect.
 * Modifies OpenGL fog settings to create the blinding supernatural haze.
 * Respects the mistEnabled toggle from commands.
 */
@Subscribe
public class FogRenderHandler {

    private static final float FOG_START = 5.0f;      // Fog starts at 5 blocks
    private static final float FOG_END = 25.0f;       // Fog ends at 25 blocks (heavy fog)
    private static final float FOG_R = 0.2f;          // Dark purple/grey tint
    private static final float FOG_G = 0.15f;
    private static final float FOG_B = 0.3f;

    @Subscribe
    public void onFogRender(EntityViewRenderEvent.FogColors event) {
        // Only apply mist if it's enabled
        if (!Mist.mistEnabled) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player == null || mc.world == null) {
            return;
        }

        // Set the fog color to a dark, supernatural purple-grey
        event.setRed(FOG_R);
        event.setGreen(FOG_G);
        event.setBlue(FOG_B);
    }

    @Subscribe
    public void onFogDensity(EntityViewRenderEvent.RenderFogEvent event) {
        // Only apply mist density if it's enabled
        if (!Mist.mistEnabled) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) {
            return;
        }

        // Configure fog mode and parameters
        GL11.glFog(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
        GL11.glFog(GL11.GL_FOG_START, FOG_START);
        GL11.glFog(GL11.GL_FOG_END, FOG_END);
        GL11.glFog(GL11.GL_FOG_DENSITY, 0.5f);

        // Enable fog
        GL11.glEnable(GL11.GL_FOG);
    }

    @Subscribe
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // This is where you could add additional per-tick logic if needed
        // For example, dynamic fog adjustments based on player location
    }
}
