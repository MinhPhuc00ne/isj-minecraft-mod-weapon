/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeCameraAngles
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(value={Dist.CLIENT})
public class ShakeRenderer
implements CameraHandler {
    private static CameraInject camera;

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (Minecraft.getInstance().isPaused()) {
            return;
        }
        ClientLevel level = Minecraft.getInstance().level;
        camera = (CameraInject)event.getCamera();
        if (level == null) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.SHAKE)) {
            return;
        }
        float yaw = event.getYaw() + ((float)Math.random() - 0.5f);
        float pitch = event.getPitch() + ((float)Math.random() - 0.5f);
        event.setYaw(yaw);
        event.setPitch(pitch);
    }

    @Override
    public void cleanup() {
        camera.setCinematic(false);
    }
}

