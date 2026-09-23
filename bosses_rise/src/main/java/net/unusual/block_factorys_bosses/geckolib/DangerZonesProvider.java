/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  org.joml.Vector2f
 *  org.joml.Vector2fc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package net.unusual.block_factorys_bosses.geckolib;

import java.util.Collection;
import javax.annotation.Nonnull;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public interface DangerZonesProvider {
    public Collection<DangerZone> getDangerZones();

    public static class DangerZone {
        @Nonnull
        private final Vector3f offset = new Vector3f();
        @Nonnull
        private final Vector2f size = new Vector2f();
        private float rotation = 0.0f;
        private int color = -1;

        @Nonnull
        public Vector3f getOffset() {
            return this.offset;
        }

        public DangerZone setOffset(Vector3f offset) {
            this.offset.set((Vector3fc)offset);
            return this;
        }

        @Nonnull
        public Vector2f getSize() {
            return this.size;
        }

        public DangerZone setSize(Vector2f size) {
            this.size.set((Vector2fc)size);
            return this;
        }

        public DangerZone setSize(float x, float z) {
            this.size.set(x, z);
            return this;
        }

        public float getRotation() {
            return this.rotation;
        }

        public DangerZone setRotation(float rotation) {
            this.rotation = rotation;
            return this;
        }

        public int getColor() {
            return this.color;
        }

        public DangerZone setColor(int color) {
            this.color = color;
            return this;
        }
    }
}

