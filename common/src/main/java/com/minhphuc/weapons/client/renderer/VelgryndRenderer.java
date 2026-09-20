package com.minhphuc.weapons.client.renderer;

import com.minhphuc.weapons.client.model.VelgryndModel;
import com.minhphuc.weapons.entity.tensura.VelgryndEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VelgryndRenderer extends MobRenderer<VelgryndEntity, VelgryndModel> {

    public static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/velgrynd.png");

    public VelgryndRenderer(EntityRendererProvider.Context context) {
        super(context, new VelgryndModel(context.bakeLayer(VelgryndModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new VelgryndGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(VelgryndEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(VelgryndEntity entity, PoseStack poseStack, float partialTickTime) {
        // True Dragon majesty scale
        float scale = 1.08F;
        poseStack.scale(scale, scale, scale);
    }
}
