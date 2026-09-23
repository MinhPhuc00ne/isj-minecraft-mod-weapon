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
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
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
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeRenderer
extends GeoEntityRenderer<IceSpikeEntity> {
    public IceSpikeRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new IceSpikeModel());
        this.addRenderLayer(new DangerZonesRenderLayer(this));
    }

    public void defaultRender(PoseStack poseStack, IceSpikeEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.tickCount < animatable.getDelay()) {
            RenderType type = this.getRenderType(animatable, this.getTextureLocation(animatable), bufferSource, partialTick);
            if (type == null) {
                return;
            }
            BakedGeoModel model = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(animatable, (GeoRenderer)this));
            if (animatable.getEvil()) {
                animatable.getDangerZones().forEach(dangerZone -> dangerZone.setColor(0xFF0000));
            }
            this.applyRenderLayers(poseStack, animatable, model, type, bufferSource, bufferSource.getBuffer(type), partialTick, packedLight, this.getPackedOverlay(animatable, 0.0f, partialTick));
            return;
        }
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getViewYRot(partialTick) + 180.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getViewXRot(partialTick)));
        float trueTick = (float)(animatable.tickCount - animatable.getDelay()) + partialTick;
        float factor = 5.5f;
        float scaler = (trueTick - factor) / factor;
        float size = ((float)(-Math.sqrt(Math.sqrt(scaler * scaler))) + 1.0f) * 1.5f;
        if (size > 0.0f) {
            poseStack.scale(size, size, size);
            poseStack.translate(0.0f, -0.25f, 0.0f);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
        poseStack.popPose();
    }

    private static class IceSpikeModel
    extends CustomEntityGeoModel<IceSpikeEntity> {
        private final ResourceLocation bigPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_big"));
        private final ResourceLocation mediumPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_medium"));
        private final ResourceLocation smallPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_small"));
        private final ResourceLocation tinyPath = this.buildFormattedModelPath(BossesRise.prefix("ice_spike_tiny"));

        public IceSpikeModel() {
            super(BossesRise.prefix("ice_spike"));
        }

        public ResourceLocation getModelResource(IceSpikeEntity spike) {
            return switch (spike.getScale()) {
                case 1 -> this.tinyPath;
                case 2 -> this.mediumPath;
                case 3 -> this.smallPath;
                default -> this.bigPath;
            };
        }
    }
}

