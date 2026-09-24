/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.vector;

import net.unusual.block_factorys_bosses.client.particle.bedrock.vector.ParticleDirection;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class OutwardParticleDirection
implements ParticleDirection {
    public static OutwardParticleDirection INSTANCE = new OutwardParticleDirection();

    private OutwardParticleDirection() {
    }

    @Override
    public void getDirection(Vector3f offsetInput, Vector3f direction) {
        direction.set((Vector3fc)offsetInput);
        direction.normalize(-1.0f);
    }
}

