/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeFov
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

public class HideGUIRenderer
implements CameraHandler {
    private static Boolean wasHideGui = null;

    @SubscribeEvent
    public static void onTickPre(ViewportEvent.ComputeFov event) {
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.HIDE_GUI)) {
            return;
        }
        if (wasHideGui == null) {
            wasHideGui = Minecraft.getInstance().options.hideGui;
        }
        Minecraft.getInstance().options.hideGui = true;
    }

    @Override
    public void cleanup() {
        Minecraft.getInstance().options.hideGui = wasHideGui != null && wasHideGui != false;
        wasHideGui = null;
    }
}

