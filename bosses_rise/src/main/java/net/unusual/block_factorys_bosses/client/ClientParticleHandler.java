/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.core.particles.ColorParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 */
package net.unusual.block_factorys_bosses.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.InvulWispParticle;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class ClientParticleHandler {
    protected static Vec3 toVec3(Vector3d vec) {
        return new Vec3(vec.x, vec.y, vec.z);
    }

    @Nullable
    public static Particle addParticle(ParticleOptions particleData, Vec3 position, Vec3 velocity) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel instanceof ClientLevel) {
            ClientLevel level = clientLevel;
            return level.levelRenderer.addParticleInternal(particleData, true, position.x, position.y, position.z, velocity.x, velocity.y, velocity.z);
        }
        return null;
    }

    public static void renderInvulWisp(GeoModel<UnderworldKnightEntity> geoModel, String boneName, boolean inactive) {
        Object var4_3 = geoModel.getBone(boneName).orElse(null);
        if (var4_3 instanceof GeoBone) {
            GeoBone bone = (GeoBone) var4_3;
            Vec3 pos = ClientParticleHandler.toVec3(bone.getWorldPosition());
            ClientParticleHandler.addParticle((ParticleOptions)ColorParticleOption.create((ParticleType)((ParticleType)BossesRiseParticleTypes.PIXEL.get()), (int)(inactive ? 16740689 : 62969)), pos.subtract(0.0, 0.1, 0.0), Vec3.ZERO);
            Particle invulWispParticle = ClientParticleHandler.addParticle(inactive ? (ParticleOptions)BossesRiseParticleTypes.INVUL_WISP_INACTIVE.get() : (ParticleOptions)BossesRiseParticleTypes.INVUL_WISP.get(), pos, Vec3.ZERO);
            if (invulWispParticle instanceof InvulWispParticle) {
                InvulWispParticle particle = (InvulWispParticle)invulWispParticle;
                particle.bone = bone;
            }
        }
    }
}

