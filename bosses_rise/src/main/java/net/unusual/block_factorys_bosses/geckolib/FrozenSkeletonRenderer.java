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
 *  net.minecraft.resources.ResourceLocation
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
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.FrozenSkeletonEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FrozenSkeletonRenderer
extends GeoEntityRenderer<FrozenSkeletonEntity> {
    public FrozenSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(BossesRise.prefix("frozen_skeleton")));
    }

    public RenderType getRenderType(FrozenSkeletonEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent((ResourceLocation)texture);
    }

    public void defaultRender(PoseStack poseStack, FrozenSkeletonEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.pushPose();
        float yRot = animatable.getViewYRot(partialTick);
        if ((Integer)animatable.getEntityData().get(FrozenSkeletonEntity.DATA_HIT_ANIMATION_TIME) > 0) {
            yRot += (float)(Math.cos((double)animatable.tickCount * 3.25) * Math.PI * (double)0.4f);
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        poseStack.popPose();
    }
}

