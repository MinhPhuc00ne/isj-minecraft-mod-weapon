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
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.util.RenderUtil
 */
package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KrakenCannonRenderer
extends GeoEntityRenderer<CannonEntity> {
    public KrakenCannonRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(BossesRise.prefix("kraken_cannon")));
    }

    public void preRender(PoseStack poseStack, CannonEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        model.getBone("rotating_horizontal").ifPresent(bone -> bone.setRotY(-Mth.lerp((float)partialTick, (float)animatable.yRotO, (float)animatable.getYRot()) * ((float)Math.PI / 180)));
        model.getBone("rotating_vertical").ifPresent(bone -> bone.setRotX(-Mth.lerp((float)partialTick, (float)animatable.xRotO, (float)animatable.getXRot()) * ((float)Math.PI / 180)));
    }

    public void defaultRender(PoseStack poseStack, CannonEntity cannon, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.pushPose();
        float hurtTime = (float)cannon.getHurtTime() - partialTick;
        float damage = cannon.getDamage() - partialTick;
        if (damage < 0.0f) {
            damage = 0.0f;
        }
        if (hurtTime > 0.0f) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin((float)hurtTime) * hurtTime * damage / 25.0f * (float)cannon.getHurtDir()));
        }
        super.defaultRender(poseStack, cannon, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        poseStack.popPose();
    }

    public void renderRecursively(PoseStack poseStack, CannonEntity cannon, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.pushPose();
        RenderUtil.translateMatrixToBone((PoseStack)poseStack, (GeoBone)bone);
        RenderUtil.translateToPivotPoint((PoseStack)poseStack, (GeoBone)bone);
        RenderUtil.rotateMatrixAroundBone((PoseStack)poseStack, (GeoBone)bone);
        RenderUtil.scaleMatrixForBone((PoseStack)poseStack, (GeoBone)bone);
        if (Objects.equals(bone.getName(), "seat")) {
            Matrix4f poseState = new Matrix4f((Matrix4fc)poseStack.last().pose());
            Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices((Matrix4f)poseState, (Matrix4f)this.entityRenderTranslations);
            bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices((Matrix4f)poseState, (Matrix4f)this.modelRenderTranslations));
            localMatrix.translate((Vector3fc)new Vector3f((Vector3fc)this.getRenderOffset(this.animatable, 1.0f).toVector3f()));
            bone.setLocalSpaceMatrix(localMatrix);
            Matrix4f worldState = new Matrix4f((Matrix4fc)localMatrix);
            worldState.translate((Vector3fc)new Vector3f((Vector3fc)((CannonEntity)this.animatable).position().toVector3f()));
            bone.setWorldSpaceMatrix(worldState);
            cannon.bonePos = bone.getLocalPosition();
        } else if (bone.isTrackingMatrices()) {
            Matrix4f poseState = new Matrix4f((Matrix4fc)poseStack.last().pose());
            Matrix4f localMatrix = RenderUtil.invertAndMultiplyMatrices((Matrix4f)poseState, (Matrix4f)this.entityRenderTranslations);
            bone.setModelSpaceMatrix(RenderUtil.invertAndMultiplyMatrices((Matrix4f)poseState, (Matrix4f)this.modelRenderTranslations));
            bone.setLocalSpaceMatrix(RenderUtil.translateMatrix((Matrix4f)localMatrix, (Vector3f)this.getRenderOffset(this.animatable, 1.0f).toVector3f()));
            bone.setWorldSpaceMatrix(RenderUtil.translateMatrix((Matrix4f)new Matrix4f((Matrix4fc)localMatrix), (Vector3f)((CannonEntity)this.animatable).position().toVector3f()));
        }
        RenderUtil.translateAwayFromPivotPoint((PoseStack)poseStack, (GeoBone)bone);
        this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);
        if (!isReRender) {
            this.applyRenderLayersForBone(poseStack, cannon, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
        this.renderChildBones(poseStack, cannon, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.popPose();
    }
}

