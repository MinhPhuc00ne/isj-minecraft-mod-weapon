/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThrownCrateRenderer
extends GeoEntityRenderer<ThrownCrateEntity> {
    public ThrownCrateRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(BossesRise.prefix("crate")));
    }

    public void defaultRender(PoseStack poseStack, ThrownCrateEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.pushPose();
        float time = (float)(animatable.getId() + animatable.tickCount) + partialTick;
        poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getViewYRot(partialTick) + 180.0f + time * 2.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getViewXRot(partialTick) + time * 3.5f));
        poseStack.translate(0.0f, 0.0f, 0.0f);
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        poseStack.popPose();
    }
}

