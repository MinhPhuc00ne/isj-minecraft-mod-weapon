package com.minhphuc.weapons.client.renderer;

import com.minhphuc.weapons.client.model.KuboModel;
import com.minhphuc.weapons.entity.darkgathering.KuboEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KuboRenderer extends MobRenderer<KuboEntity, KuboModel> {

    public static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("weapons", "textures/entity/kubo.png");

    public KuboRenderer(EntityRendererProvider.Context context) {
        super(context, new KuboModel(context.bakeLayer(KuboModel.LAYER_LOCATION)), 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(KuboEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(KuboEntity entity, PoseStack poseStack, float partialTickTime) {
        // Mặt trời đen to lớn uy nghiêm lơ lửng
        float s = 1.6F;
        poseStack.scale(s, s, s);
    }
}
