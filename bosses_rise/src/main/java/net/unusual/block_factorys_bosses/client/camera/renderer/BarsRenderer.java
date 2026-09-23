/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.GameRenderer
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent$OverlayType
 *  net.neoforged.neoforge.client.event.RenderGuiEvent$Pre
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(value={Dist.CLIENT})
public class BarsRenderer
implements CameraHandler {
    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void eventHandler(RenderGuiEvent.Pre event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BARS)) {
            return;
        }
        BarsRenderer.renderBars(event);
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() != RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.BARS)) {
            return;
        }
        event.setCanceled(true);
    }

    private static void renderBars(RenderGuiEvent.Pre event) {
        int w = event.getGuiGraphics().guiWidth();
        int h = event.getGuiGraphics().guiHeight();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask((boolean)false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        event.getGuiGraphics().blit(BossesRise.prefix("textures/screens/cinematic_bars.png"), 0, 0, 0.0f, 0.0f, w, h, w, h);
        RenderSystem.depthMask((boolean)true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

