/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Camera
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.util.FastColor$ARGB32
 *  net.minecraft.util.Mth
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector2f
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.particle.bedrock.AbstractBedrockParticle;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleAppearanceBillboard;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BedrockParticle
extends AbstractBedrockParticle {
    @Nullable
    public final BedrockParticleEmitter parent;
    private float rollVelocity = 0.0f;

    public BedrockParticle(ClientLevel level, BedrockParticleEffect particleEffect, @Nullable BedrockParticleEmitter parent, Vec3 position, Vec3 direction) {
        super(level, particleEffect, position, direction);
        this.parent = parent;
        this.lifetime = particleEffect.particleLifetime().getLifetime();
    }

    public ParticleRenderType getRenderType() {
        return this.particleEffect.particleRenderType();
    }

    public AABB getRenderBoundingBox(float partialTicks) {
        return this.particleEffect.particleAppearance().getRenderBoundingBox(this, partialTicks);
    }

    public float getRollVelocity() {
        return this.rollVelocity;
    }

    public void setRollVelocity(float rollVelocity) {
        this.rollVelocity = rollVelocity;
    }

    @Override
    public void setMolangVariables(float partialTicks) {
        super.setMolangVariables(partialTicks);
        this.setMolangVariable("particle_age", ((float)this.age + partialTicks) / 20.0f);
        this.setMolangVariable("particle_lifetime", (float)this.lifetime / 20.0f);
        this.setMolangVariable("particle_random_1", this.random1);
        this.setMolangVariable("particle_random_2", this.random2);
        this.setMolangVariable("particle_random_3", this.random3);
        this.setMolangVariable("particle_random_4", this.random4);
        if (this.parent != null) {
            this.parent.setMolangVariables(partialTicks);
        }
    }

    @Override
    public void tick() {
        if (this.age >= this.lifetime) {
            this.remove();
            return;
        }
        if (this.age == 0) {
            if (this.particleEffect.particleMotionCollision() != null) {
                this.particleEffect.particleMotionCollision().initialize(this);
            }
            this.particleEffect.particleInitialSpin().initialize(this);
        }
        this.setMolangVariables(0.0f);
        this.particleEffect.particleLifetime().tick(this);
        if (!this.isAlive()) {
            return;
        }
        if (this.particleEffect.particleMotion() != null) {
            this.particleEffect.particleMotion().tick(this);
        }
        ++this.age;
    }

    @Override
    public void physicsStep() {
        super.physicsStep();
        this.setRoll(this.roll + this.rollVelocity * 0.05f);
    }

    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        this.setMolangVariables(partialTicks);
        Quaternionf quaternion = this.getRotation(renderInfo, partialTicks);
        Vec3 cameraPosition = renderInfo.getPosition();
        Vec3 position = this.getPos(partialTicks).subtract(cameraPosition);
        this.renderRotatedQuad(buffer, quaternion, (float)position.x, (float)position.y, (float)position.z, partialTicks);
    }

    protected Quaternionf getRotation(Camera renderInfo, float partialTicks) {
        Quaternionf quaternion = new Quaternionf();
        this.particleEffect.particleAppearance().facingCameraMode().getFacing(this, quaternion, renderInfo, partialTicks);
        if (this.roll != 0.0f) {
            quaternion.rotateZ(Mth.lerp((float)partialTicks, (float)this.oRoll, (float)this.roll));
        }
        return quaternion;
    }

    protected void getUV(Vector2f topLeft, Vector2f bottomRight, float partialTicks) {
        this.particleEffect.particleAppearance().uv().getUV(this, partialTicks, topLeft, bottomRight);
        topLeft.set(this.sprite.getU(topLeft.x()), this.sprite.getV(topLeft.y()));
        bottomRight.set(this.sprite.getU(bottomRight.x()), this.sprite.getV(bottomRight.y()));
    }

    protected void updateColor() {
        int color = this.particleEffect.particleAppearanceTinting().getColor(this);
        this.alpha = (float)FastColor.ARGB32.alpha((int)color) / 255.0f;
        this.rCol = (float)FastColor.ARGB32.red((int)color) / 255.0f;
        this.gCol = (float)FastColor.ARGB32.green((int)color) / 255.0f;
        this.bCol = (float)FastColor.ARGB32.blue((int)color) / 255.0f;
    }

    protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks) {
        ParticleAppearanceBillboard particleAppearance = this.particleEffect.particleAppearance();
        Vector2f quadHalfSize = particleAppearance.size().getNewVector().mul(0.5f);
        Vector2f uvTopLeft = new Vector2f();
        Vector2f uvBottomRight = new Vector2f();
        this.getUV(uvTopLeft, uvBottomRight, partialTicks);
        this.updateColor();
        int lightColor = this.getLightColor(partialTicks);
        this.renderVertex(buffer, quaternion, x, y, z, quadHalfSize.x(), -quadHalfSize.y(), uvBottomRight.x(), uvBottomRight.y(), lightColor);
        this.renderVertex(buffer, quaternion, x, y, z, quadHalfSize.x(), quadHalfSize.y(), uvBottomRight.x(), uvTopLeft.y(), lightColor);
        this.renderVertex(buffer, quaternion, x, y, z, -quadHalfSize.x(), quadHalfSize.y(), uvTopLeft.x(), uvTopLeft.y(), lightColor);
        this.renderVertex(buffer, quaternion, x, y, z, -quadHalfSize.x(), -quadHalfSize.y(), uvTopLeft.x(), uvBottomRight.y(), lightColor);
    }

    protected void renderVertex(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float quadSizeX, float quadSizeY, float u, float v, int packedLight) {
        Vector3f vector3f = new Vector3f(quadSizeX, quadSizeY, 0.0f).rotate((Quaternionfc)quaternion).add(x, y, z);
        buffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(packedLight);
    }
}

