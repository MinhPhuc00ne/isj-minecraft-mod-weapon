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
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.yeti.IceSpikeClusterEntity;
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
public class IceSpikeClusterRenderer
extends GeoEntityRenderer<IceSpikeClusterEntity> {
    public static ResourceLocation PROJECTILE_MODEL = BossesRise.prefix("geo/entity/ice_spike_projectile.geo.json");
    private static final float GRAB_TIME = 5.0f;

    public IceSpikeClusterRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new CustomEntityGeoModel<IceSpikeClusterEntity>(BossesRise.prefix("ice_spike_cluster")){

            public ResourceLocation getModelResource(IceSpikeClusterEntity animatable) {
                return animatable.getHeld() ? PROJECTILE_MODEL : super.getModelResource(animatable);
            }
        });
        this.addRenderLayer(new DangerZonesRenderLayer(this));
    }

    public void defaultRender(PoseStack poseStack, IceSpikeClusterEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        Player player;
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
        LivingEntity model = animatable.getOwner();
        if (model instanceof Player && !(player = (Player)model).isUsingItem() && animatable.getHeld()) {
            return;
        }
        poseStack.pushPose();
        model = animatable.getOwner();
        if (model instanceof Player && (player = (Player)model).isUsingItem() && animatable.getHeld()) {
            float spin;
            float transition = ((float)player.getTicksUsingItem() + partialTick) / 5.0f;
            if (transition > 1.0f) {
                transition = 1.0f;
            }
            if ((spin = (float)player.getTicksUsingItem() + partialTick - 5.0f) <= 0.0f) {
                spin = 0.0f;
            }
            poseStack.translate(0.0f, Mth.lerp((float)transition, (float)0.0f, (float)(animatable.getBbHeight() * 0.5f)), 0.0f);
            poseStack.mulPose(Axis.YP.rotationDegrees(-player.getViewYRot(partialTick) + 180.0f));
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp((float)transition, (float)0.0f, (float)(-90.0f - player.getViewXRot(partialTick)))));
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp((float)transition, (float)0.0f, (float)(spin * 12.0f))));
        } else {
            float yRot = animatable.getViewYRot(partialTick);
            if ((Integer)animatable.getEntityData().get(IceSpikeClusterEntity.DATA_HIT_ANIMATION_TIME) > 0) {
                yRot += (float)(Math.cos((double)animatable.tickCount * 3.25) * Math.PI * (double)0.4f);
            }
            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        }
        float trueTick = (float)(animatable.tickCount - animatable.getDelay()) + partialTick;
        float factor = 5.0f;
        float size = trueTick * trueTick / (factor * factor);
        if (size >= 1.0f) {
            size = 1.0f;
        }
        if (size > 0.0f) {
            poseStack.scale(size, size, size);
            poseStack.translate(0.0f, -0.25f, 0.0f);
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
        poseStack.popPose();
    }
}

