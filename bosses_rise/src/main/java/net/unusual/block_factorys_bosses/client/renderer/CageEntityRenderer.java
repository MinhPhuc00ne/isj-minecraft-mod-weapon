/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CageEntityRenderer<T extends EntityModel<CageEntity>>
extends EntityRenderer<CageEntity>
implements RenderLayerParent<CageEntity, T> {
    protected final T model;
    protected final ResourceLocation texture;

    public CageEntityRenderer(EntityRendererProvider.Context context, T model, ResourceLocation texture) {
        super(context);
        this.model = model;
        this.texture = texture;
    }

    public T getModel() {
        return this.model;
    }

    public ResourceLocation getTextureLocation(CageEntity entity) {
        return this.texture;
    }

    public void render(CageEntity cage, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout((ResourceLocation)this.getTextureLocation(cage)));
        poseStack.pushPose();
        poseStack.translate(0.0f, 1.5f, 0.0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(cage.getYRot()));
        float hurtTime = (float)cage.getHurtTime() - partialTicks;
        float damage = cage.getDamage() - partialTicks;
        if (damage < 0.0f) {
            damage = 0.0f;
        }
        if (hurtTime > 0.0f) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin((float)hurtTime) * hurtTime * damage / 25.0f * (float)cage.getHurtDir()));
        }
        this.model.setupAnim(cage, 0.0f, 0.0f, 0.0f, cage.getYRot(), cage.getXRot());
        this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(cage, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }
}

