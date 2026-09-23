/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.client.ClientHooks
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.unusual.block_factorys_bosses.mixins.client;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.camera.renderer.CameraRenderer;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Camera.class})
public abstract class CameraMixin
implements CameraInject {
    @Shadow
    private float eyeHeight;
    @Unique
    private boolean isCinematic = false;
    @Unique
    private Vec3 bosses_rise_java$pos = Vec3.ZERO;

    @Shadow
    public abstract void setPosition(Vec3 var1);

    @Shadow
    @Deprecated
    protected abstract void setRotation(float var1, float var2);

    @Shadow
    protected abstract float getMaxZoom(float var1);

    @Shadow
    protected abstract void move(float var1, float var2, float var3);

    @Shadow
    public abstract Vec3 getPosition();

    @Inject(method={"getPosition"}, at={@At(value="RETURN")}, cancellable=true)
    private void getPosition(CallbackInfoReturnable<Vec3> cir) {
        if (this.isCinematic) {
            cir.setReturnValue(this.bosses_rise_java$pos);
        }
    }

    @Override
    public boolean isCinematic() {
        return this.isCinematic;
    }

    @Override
    public void setCinematic(boolean isCinematic) {
        if (this.isCinematic != isCinematic) {
            Minecraft.getInstance().options.hideGui = isCinematic;
        }
        this.isCinematic = isCinematic;
        if (isCinematic) {
            CameraRenderer.viewBobCancel = 3;
        }
    }

    @Override
    public void setCinematicPosition(Vec3 pos) {
        this.bosses_rise_java$pos = pos;
    }

    @Inject(method={"setup"}, at={@At(value="TAIL")})
    private void setupMixin(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        Entity entity2 = entity.getControlledVehicle();
        if (entity2 instanceof CannonEntity) {
            CannonEntity cannon = (CannonEntity)entity2;
            if (cannon.bonePos != null) {
                Vec3 camPos = new Vec3(cannon.bonePos.x, cannon.bonePos.y, cannon.bonePos.z);
                Vec3 eyePos = new Vec3(0.0, (double)entity.getEyeHeight() * (detached ? 1.0 : 0.5), 0.0);
                this.setPosition(cannon.getPosition(partialTick).add(camPos).add(eyePos.xRot(cannon.getViewXRot(partialTick) * ((float)Math.PI / 180)).yRot((-cannon.getViewYRot(partialTick) - 180.0f) * ((float)Math.PI / 180))));
                if (detached) {
                    float f;
                    if (thirdPersonReverse) {
                        this.setRotation(entity.getViewYRot(partialTick) + 180.0f, -(entity.getViewXRot(partialTick) + cannon.getViewXRot(partialTick)));
                    }
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity)entity;
                        f = living.getScale();
                    } else {
                        f = 1.0f;
                    }
                    float f2 = f;
                    this.move(-this.getMaxZoom(4.0f * f2), 0.0f, 0.0f);
                }
            }
        }
    }
}

