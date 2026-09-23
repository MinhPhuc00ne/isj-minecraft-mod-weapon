/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 *  software.bernie.geckolib.cache.object.GeoBone
 */
package net.unusual.block_factorys_bosses.client.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;
import net.unusual.block_factorys_bosses.client.camera.renderer.BarsRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.BlackRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.CameraRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.FovRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.HideGUIRenderer;
import net.unusual.block_factorys_bosses.client.camera.renderer.ShakeRenderer;
import software.bernie.geckolib.cache.object.GeoBone;

@OnlyIn(value=Dist.CLIENT)
public class BossesRiseClientCinematicCamera {
    private static final Map<CinematicCameraTypes, CameraHandler> handlers = Map.of(CinematicCameraTypes.BARS, new BarsRenderer(), CinematicCameraTypes.FOV, new FovRenderer(), CinematicCameraTypes.CAMERA, new CameraRenderer(), CinematicCameraTypes.SHAKE, new ShakeRenderer(), CinematicCameraTypes.BLACK, new BlackRenderer(), CinematicCameraTypes.HIDE_GUI, new HideGUIRenderer());
    private static final List<CinematicCameraTypes> activeHandlers = new ArrayList<CinematicCameraTypes>();
    @Nullable
    private static CinematicEntity trackedTarget;

    public static boolean isHandlerActive(CinematicCameraTypes handlerId) {
        if (!BossesRiseClientCinematicCamera.isCameraActive()) {
            return false;
        }
        if (trackedTarget == null) {
            BossesRiseClientCinematicCamera.stopCinematicCamera();
            return false;
        }
        return activeHandlers.contains((Object)handlerId);
    }

    public static boolean isCameraActive() {
        return !activeHandlers.isEmpty();
    }

    public static void startCinematicCamera(String handlerId, CinematicEntity trackedObject, String cameraBoneName) {
        try {
            CinematicCameraTypes type = CinematicCameraTypes.valueOf(handlerId.toUpperCase());
            BossesRiseClientCinematicCamera.startCinematicCamera(type, trackedObject, cameraBoneName);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid cinematic handler ID: " + handlerId.toLowerCase());
        }
    }

    public static void startCinematicCamera(CinematicCameraTypes handler, CinematicEntity trackedObject, String cameraBoneName) {
        activeHandlers.add(handler);
        trackedTarget = trackedObject;
        trackedTarget.setCameraBone(cameraBoneName);
        handlers.get((Object)handler).init();
    }

    public static void stopCinematicCamera(CinematicEntity trackedObject) {
        if (trackedObject != trackedTarget) {
            return;
        }
        BossesRiseClientCinematicCamera.stopCinematicCamera();
    }

    public static void stopCinematicCamera() {
        activeHandlers.forEach(s -> {
            CameraHandler handler = handlers.get(s);
            if (handler != null) {
                handler.cleanup();
            }
        });
        activeHandlers.clear();
        trackedTarget = null;
    }

    public static void stopCinematicCamera(String handlerId, CinematicEntity trackedObject) {
        if (trackedObject != trackedTarget) {
            return;
        }
        try {
            CinematicCameraTypes type = CinematicCameraTypes.valueOf(handlerId.toUpperCase());
            BossesRiseClientCinematicCamera.stopCinematicCamera(type, trackedObject);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid cinematic handler ID: " + handlerId.toUpperCase());
        }
    }

    public static void stopCinematicCamera(CinematicCameraTypes handlerId, CinematicEntity trackedObject) {
        if (trackedObject != trackedTarget) {
            return;
        }
        CameraHandler handler = handlers.get((Object)handlerId);
        if (handler == null) {
            return;
        }
        handler.cleanup();
        activeHandlers.remove((Object)handlerId);
        if (activeHandlers.isEmpty()) {
            BossesRiseClientCinematicCamera.stopCinematicCamera(trackedObject);
        }
    }

    public static Vec3 getCameraPosition() {
        if (trackedTarget == null) {
            return Vec3.ZERO;
        }
        GeoBone bone = trackedTarget.getCameraBone();
        if (bone != null) {
            return new Vec3(bone.getWorldPosition().x(), bone.getWorldPosition().y(), bone.getWorldPosition().z());
        }
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        return camera.getPosition();
    }

    public static GeoBone getCameraBone() {
        if (trackedTarget == null) {
            return null;
        }
        return trackedTarget.getCameraBone();
    }

    public static CinematicEntity getTrackedObject() {
        return trackedTarget;
    }
}

