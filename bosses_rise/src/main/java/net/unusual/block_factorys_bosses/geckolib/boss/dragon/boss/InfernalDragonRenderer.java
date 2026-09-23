/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package net.unusual.block_factorys_bosses.geckolib.boss.dragon.boss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class InfernalDragonRenderer
extends GeoEntityRenderer<InfernalDragonEntity>
implements CinematicRenderer<InfernalDragonEntity> {
    private final GeoModel<InfernalDragonEntity> phase1Model;
    private final GeoModel<InfernalDragonEntity> phase2Model;

    public InfernalDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"dragon_phase1")));
        this.phase1Model = this.model;
        this.phase2Model = new CustomEntityGeoModel(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"dragon_phase2"));
        this.addRenderLayer(new EmissiveRenderLayer(this));
    }

    public void defaultRender(PoseStack poseStack, InfernalDragonEntity dragon, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (dragon.isIntro()) {
            poseStack.pushPose();
            Vec3 a = new Vec3(-8.7616875, 19.339375, 17.1345);
            a = a.yRot((float)(-dragon.angleDelta - 180.0) * ((float)Math.PI / 180));
            poseStack.translate(-a.x, -a.y, -a.z);
            super.defaultRender(poseStack, dragon, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
            poseStack.popPose();
        } else {
            super.defaultRender(poseStack, dragon, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
    }

    public GeoModel<InfernalDragonEntity> getGeoModel() {
        if (this.animatable == null) {
            return this.model;
        }
        return ((InfernalDragonEntity)this.animatable).isTransformed() ? this.phase2Model : this.phase1Model;
    }

    public boolean shouldRender(InfernalDragonEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    @Nullable
    public Pair<Float, Float> getCinematicYawAndPitch(InfernalDragonEntity animatable, GeoBone cameraBone, LocalPlayer player) {
        if (animatable.isIntro()) {
            GeoModel<InfernalDragonEntity> geoModel = this.getGeoModel();
            GeoBone bone = geoModel.getBone("hip").orElse(null);
            if (bone == null) {
                return null;
            }
            Vector3d pos = bone.getWorldPosition();
            Vec3 a = new Vec3(-8.7616875, 19.339375, 17.1345);
            a = a.yRot((float)(-animatable.angleDelta - 180.0) * ((float)Math.PI / 180));
            Vector3d panPos = new Vector3d(pos.x - a.x, pos.y - a.y, pos.z - a.z);
            Vector3d camPos = cameraBone.getWorldPosition();
            double dx = camPos.x() - panPos.x();
            double dy = camPos.y() - panPos.y();
            double dz = camPos.z() - panPos.z();
            float yaw = (float)(Math.atan2(dx, dz) * -57.29577951308232 + 180.0);
            float pitch = (float)(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 57.29577951308232);
            return new Pair((Object)Float.valueOf(yaw), (Object)Float.valueOf(pitch));
        }
        Vector3d camPos = cameraBone.getWorldPosition();
        Vec3 targetPos = animatable.getEyePosition();
        double dx = camPos.x() - targetPos.x;
        double dy = camPos.y() - targetPos.y;
        double dz = camPos.z() - targetPos.z;
        float yaw = (float)(Math.atan2(dx, dz) * -57.2957763671875 + 180.0);
        float pitch = (float)(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 57.2957763671875);
        return new Pair((Object)Float.valueOf(yaw), (Object)Float.valueOf(pitch));
    }
}

