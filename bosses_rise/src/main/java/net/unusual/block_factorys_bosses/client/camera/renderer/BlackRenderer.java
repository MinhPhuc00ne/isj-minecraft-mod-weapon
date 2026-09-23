/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.player.LocalPlayer
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent$OverlayType
 *  net.neoforged.neoforge.client.event.RenderGuiEvent$Pre
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(value={Dist.CLIENT})
public class BlackRenderer
implements CameraHandler {
    @SubscribeEvent(receiveCanceled=true)
    public static void computeCameraAngles(RenderGuiEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BLACK)) {
            return;
        }
        BlackRenderer.onRenderGui(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() != RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BLACK)) {
            return;
        }
        event.setCanceled(true);
    }

    public static void onRenderGui(GuiGraphics graphics) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int color = -16777216;
        graphics.fill(0, 0, screenWidth, screenHeight, color);
        RenderSystem.disableBlend();
    }
}

