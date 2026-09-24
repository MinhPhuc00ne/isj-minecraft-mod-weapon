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
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelCrate1;
import net.unusual.block_factorys_bosses.client.model.ModelCrate2;
import net.unusual.block_factorys_bosses.client.model.ModelCrate3;
import net.unusual.block_factorys_bosses.client.model.ModelCrate4;
import net.unusual.block_factorys_bosses.client.model.ModelCrate5;
import net.unusual.block_factorys_bosses.client.model.ModelCrate6;
import net.unusual.block_factorys_bosses.client.model.ModelCrate7;
import net.unusual.block_factorys_bosses.client.model.ModelCrate8;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CrateEntityRenderer<T extends EntityModel<CratePileEntity>>
extends EntityRenderer<CratePileEntity>
implements RenderLayerParent<CratePileEntity, T> {
    private static final ResourceLocation TEXTURE = BossesRise.prefix("textures/entities/crate.png");
    private final EntityModel<CratePileEntity> crate1;
    private final EntityModel<CratePileEntity> crate2;
    private final EntityModel<CratePileEntity> crate3;
    private final EntityModel<CratePileEntity> crate4;
    private final EntityModel<CratePileEntity> crate5;
    private final EntityModel<CratePileEntity> crate6;
    private final EntityModel<CratePileEntity> crate7;
    private final EntityModel<CratePileEntity> crate8;

    public CrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.crate1 = new ModelCrate1<CratePileEntity>(context.bakeLayer(ModelCrate1.LAYER_LOCATION));
        this.crate2 = new ModelCrate2<CratePileEntity>(context.bakeLayer(ModelCrate2.LAYER_LOCATION));
        this.crate3 = new ModelCrate3<CratePileEntity>(context.bakeLayer(ModelCrate3.LAYER_LOCATION));
        this.crate4 = new ModelCrate4<CratePileEntity>(context.bakeLayer(ModelCrate4.LAYER_LOCATION));
        this.crate5 = new ModelCrate5<CratePileEntity>(context.bakeLayer(ModelCrate5.LAYER_LOCATION));
        this.crate6 = new ModelCrate6<CratePileEntity>(context.bakeLayer(ModelCrate6.LAYER_LOCATION));
        this.crate7 = new ModelCrate7<CratePileEntity>(context.bakeLayer(ModelCrate7.LAYER_LOCATION));
        this.crate8 = new ModelCrate8<CratePileEntity>(context.bakeLayer(ModelCrate8.LAYER_LOCATION));
    }

    public T getModel() {
        return (T)this.crate1;
    }

    public T getModel(CratePileEntity pile) {
        return (T)(switch (pile.getCrateCount()) {
            case 1 -> this.crate1;
            case 2 -> this.crate2;
            case 3 -> this.crate3;
            case 4 -> this.crate4;
            case 5 -> this.crate5;
            case 6 -> this.crate6;
            case 7 -> this.crate7;
            default -> this.crate8;
        });
    }

    public ResourceLocation getTextureLocation(CratePileEntity entity) {
        return TEXTURE;
    }

    public void render(CratePileEntity crate, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout((ResourceLocation)TEXTURE));
        poseStack.pushPose();
        poseStack.translate(0.0f, 1.5f, 0.0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(crate.getYRot()));
        float hurtTime = (float)crate.getHurtTime() - partialTicks;
        float damage = crate.getDamage() - partialTicks;
        if (damage < 0.0f) {
            damage = 0.0f;
        }
        if (hurtTime > 0.0f) {
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin((float)hurtTime) * hurtTime * damage / 25.0f * (float)crate.getHurtDir()));
        }
        this.getModel(crate).setupAnim(crate, 0.0f, 0.0f, 0.0f, crate.getYRot(), crate.getXRot());
        this.getModel(crate).renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(crate, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }
}

