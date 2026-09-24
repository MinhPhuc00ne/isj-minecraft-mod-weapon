package net.neoforged.neoforge.client.event;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;

public class ViewportEvent {
    private final GameRenderer renderer;
    private final Camera camera;
    private final double partialTick;

    public ViewportEvent(GameRenderer renderer, Camera camera, double partialTick) {
        this.renderer = renderer;
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public GameRenderer getRenderer() { return renderer; }
    public Camera getCamera() { return camera; }
    public double getPartialTick() { return partialTick; }

    public static class ComputeCameraAngles extends ViewportEvent {
        private float yaw, pitch, roll;

        public ComputeCameraAngles(GameRenderer renderer, Camera camera, double partialTick, float yaw, float pitch, float roll) {
            super(renderer, camera, partialTick);
            this.yaw = yaw;
            this.pitch = pitch;
            this.roll = roll;
        }

        public float getYaw() { return yaw; }
        public void setYaw(float yaw) { this.yaw = yaw; }
        public float getPitch() { return pitch; }
        public void setPitch(float pitch) { this.pitch = pitch; }
        public float getRoll() { return roll; }
        public void setRoll(float roll) { this.roll = roll; }
    }

    public static class ComputeFov extends ViewportEvent {
        private double fov;

        public ComputeFov(GameRenderer renderer, Camera camera, double partialTick, double fov) {
            super(renderer, camera, partialTick);
            this.fov = fov;
        }

        public double getFOV() { return fov; }
        public void setFOV(double fov) { this.fov = fov; }
    }
}
