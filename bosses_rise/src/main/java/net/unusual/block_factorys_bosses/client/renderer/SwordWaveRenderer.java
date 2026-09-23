/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SwordWaveRenderer
extends EntityRenderer<SwordWaveEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/particle/soul_shockwave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive((ResourceLocation)TEXTURE_LOCATION);

    public SwordWaveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(SwordWaveEntity wave, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        float baseRot = wave.isVertical() ? 0.0f : 90.0f;
        float scale = wave.getScale();
        poseStack.pushPose();
        if (wave.isVertical()) {
            poseStack.translate(0.0f, 1.0f, 0.0f);
        }
        poseStack.mulPose(Axis.YN.rotationDegrees(-wave.getYRot() + 90.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(baseRot));
        poseStack.mulPose(Axis.ZN.rotationDegrees(-wave.getXRot() + 90.0f));
        poseStack.scale(6.0f * scale, scale, 6.0f * scale);
        PoseStack.Pose pose = poseStack.last();
        SwordWaveRenderer.vertex(vertexconsumer, pose, 0.0f, 0, 0, 1);
        SwordWaveRenderer.vertex(vertexconsumer, pose, 1.0f, 0, 1, 1);
        SwordWaveRenderer.vertex(vertexconsumer, pose, 1.0f, 1, 1, 0);
        SwordWaveRenderer.vertex(vertexconsumer, pose, 0.0f, 1, 0, 0);
        poseStack.popPose();
        super.render(wave, entityYaw, partialTicks, poseStack, buffer, 0xF000F0);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5f, (float)y - 0.5f, 0.0f).setColor(-1).setUv((float)u, (float)v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(pose, 0.0f, 1.0f, 0.0f);
    }

    public ResourceLocation getTextureLocation(SwordWaveEntity entity) {
        return TEXTURE_LOCATION;
    }
}

