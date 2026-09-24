/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  it.unimi.dsi.fastutil.doubles.Double2DoubleFunction
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.Nullable
 *  org.joml.AxisAngle4f
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Vector2f
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.EasingType
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.cache.object.GeoQuad
 *  software.bernie.geckolib.cache.object.GeoVertex
 *  software.bernie.geckolib.cache.texture.AnimatableTexture
 *  software.bernie.geckolib.loading.json.raw.FaceUV$Rotation
 *  software.bernie.geckolib.renderer.GeoRenderer
 *  software.bernie.geckolib.renderer.layer.GeoRenderLayer
 *  software.bernie.geckolib.util.RenderUtil
 */
package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.util.Collection;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoQuad;
import software.bernie.geckolib.cache.object.GeoVertex;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.loading.json.raw.FaceUV;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtil;

public class DangerZonesRenderLayer<T extends GeoAnimatable & DangerZonesProvider>
extends GeoRenderLayer<T> {
    private static final ResourceLocation DANGER_ZONE_TEXTURE = ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"textures/entity/danger_zone.png");
    private static final Double2DoubleFunction GHOST_EASING = EasingType.easeOut(EasingType::quadratic);
    private static final GeoQuad QUAD = GeoQuad.build((GeoVertex[])new GeoVertex[]{new GeoVertex(-0.5, 0.0, -0.5), new GeoVertex(-0.5, 0.0, 0.5), new GeoVertex(0.5, 0.0, 0.5), new GeoVertex(0.5, 0.0, -0.5)}, (float)0.0f, (float)0.0f, (float)48.0f, (float)48.0f, (FaceUV.Rotation)FaceUV.Rotation.NONE, (float)48.0f, (float)48.0f, (boolean)false, (Direction)Direction.UP);

    public DangerZonesRenderLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    protected ResourceLocation getTextureResource(T animatable) {
        return DANGER_ZONE_TEXTURE;
    }

    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        Collection<DangerZonesProvider.DangerZone> dangerZones = ((DangerZonesProvider)animatable).getDangerZones();
        if (dangerZones.isEmpty()) {
            return;
        }
        renderType = RenderType.entityTranslucent((ResourceLocation)DANGER_ZONE_TEXTURE);
        if (renderType == null) {
            return;
        }
        buffer = bufferSource.getBuffer(renderType);
        for (DangerZonesProvider.DangerZone dangerZone : dangerZones) {
            this.renderDangerZone(dangerZone, poseStack, animatable, buffer, partialTick, packedLight, packedOverlay);
        }
    }

    public void renderDangerZone(DangerZonesProvider.DangerZone dangerZone, PoseStack poseStack, T animatable, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        packedLight = 0xF00000;
        poseStack.pushPose();
        Vector3f offset = dangerZone.getOffset();
        poseStack.translate((double)offset.x, (double)offset.y + 0.251, (double)offset.z);
        poseStack.rotateAround(new Quaternionf(new AxisAngle4f(dangerZone.getRotation(), 0.0f, 1.0f, 0.0f)), 0.0f, 0.0f, 0.0f);
        Vector2f scale = dangerZone.getSize();
        poseStack.scale(scale.x, 1.0f, scale.y);
        AnimatableTexture.setAndUpdate((ResourceLocation)DANGER_ZONE_TEXTURE);
        this.renderQuad(poseStack, buffer, packedLight, packedOverlay, dangerZone.getColor());
        double floatingProgress = RenderUtil.getCurrentTick() % 10.0 / 10.0;
        poseStack.translate(0.0, GHOST_EASING.apply(floatingProgress) * (double)0.3f, 0.0);
        this.renderQuad(poseStack, buffer, packedLight, packedOverlay, DangerZonesRenderLayer.setColorAlpha(dangerZone.getColor(), 1.0 - floatingProgress * 1.2));
        poseStack.popPose();
    }

    private static int setColorAlpha(int color, double alpha) {
        return color & 0xFFFFFF | (int)Math.floor(255.0 * Math.clamp((double)alpha, (double)0.0, (double)1.0)) << 24;
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        Vector3f normal = poseStack.last().normal().transform(new Vector3f((Vector3fc)QUAD.normal()));
        Matrix4f poseState = new Matrix4f((Matrix4fc)poseStack.last().pose());
        this.getRenderer().createVerticesOfQuad(QUAD, poseState, normal, buffer, packedLight, packedOverlay, color);
    }
}

