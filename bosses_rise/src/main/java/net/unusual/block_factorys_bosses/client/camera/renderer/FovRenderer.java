/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeFov
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;

@EventBusSubscriber(value={Dist.CLIENT})
public class FovRenderer
implements CameraHandler {
    @SubscribeEvent
    public static void computeFOV(ViewportEvent.ComputeFov event) {
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.FOV)) {
            return;
        }
        event.setFOV(event.getFOV() + 30.0);
    }
}

