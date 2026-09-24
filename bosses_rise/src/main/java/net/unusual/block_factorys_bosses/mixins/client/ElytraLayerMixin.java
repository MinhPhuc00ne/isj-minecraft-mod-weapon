/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ElytraModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.player.PlayerRenderer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.unusual.block_factorys_bosses.mixins.client;

import java.util.Collections;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={ElytraModel.class})
public abstract class ElytraLayerMixin<T extends LivingEntity> {
    @Shadow
    @Final
    private ModelPart leftWing;
    @Shadow
    @Final
    private ModelPart rightWing;

    @Inject(method={"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at={@At(value="HEAD")})
    private void preSetup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof AbstractClientPlayer) {
            this.leftWing.resetPose();
            this.rightWing.resetPose();
        }
    }

    @Inject(method={"setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V"}, at={@At(value="TAIL")})
    private void postSetup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        EntityRenderer entityRenderer;
        AbstractClientPlayer player;
        RollAttachment attachment;
        if (entity instanceof AbstractClientPlayer && (attachment = RollAttachment.fromPlayer((Player)(player = (AbstractClientPlayer)entity))).isRolling() && (entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)player)) instanceof PlayerRenderer) {
            PlayerRenderer renderer = (PlayerRenderer)entityRenderer;
            ModelPart cloak = ((PlayerModel)renderer.getModel()).body;
            this.leftWing.resetPose();
            ModelPart left = new ModelPart(Collections.emptyList(), Collections.emptyMap());
            left.copyFrom(this.leftWing);
            this.leftWing.copyFrom(cloak);
            this.leftWing.x += left.x;
            this.leftWing.z += left.z;
            this.leftWing.y += left.y;
            this.leftWing.xRot += left.xRot;
            this.leftWing.yRot += left.yRot;
            this.leftWing.zRot += left.zRot;
            this.rightWing.resetPose();
            ModelPart right = new ModelPart(Collections.emptyList(), Collections.emptyMap());
            right.copyFrom(this.rightWing);
            this.rightWing.copyFrom(cloak);
            this.rightWing.x += right.x;
            this.rightWing.z += right.z;
            this.rightWing.y += right.y;
            this.rightWing.xRot += right.xRot;
            this.rightWing.yRot += right.yRot;
            this.rightWing.zRot += right.zRot;
        }
    }
}

