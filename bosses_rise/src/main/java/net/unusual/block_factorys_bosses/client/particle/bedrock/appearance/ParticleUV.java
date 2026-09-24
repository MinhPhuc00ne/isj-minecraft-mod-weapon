/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  org.joml.Vector2f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.appearance;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleUVFlipbook;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleUVFull;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleUVMath;
import org.joml.Vector2f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ParticleUV {
    public void getUV(BedrockParticle var1, float var2, Vector2f var3, Vector2f var4);

    public static ParticleUV deserialize(@Nullable JsonObject component) {
        if (component == null) {
            return ParticleUVFull.INSTANCE;
        }
        if (component.has("flipbook")) {
            return ParticleUVFlipbook.deserialize(component);
        }
        return ParticleUVMath.deserialize(component);
    }
}

