package com.minhphuc.weapons.client.renderer;

import com.minhphuc.weapons.client.model.PrimordialDemonModel;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PrimordialDemonRenderer extends MobRenderer<PrimordialDemonEntity, PrimordialDemonModel> {

    public PrimordialDemonRenderer(EntityRendererProvider.Context context) {
        super(context, new PrimordialDemonModel(context.bakeLayer(PrimordialDemonModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new DemonEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(PrimordialDemonEntity entity) {
        return entity.getDemonType().getTextureLocation();
    }

    @Override
    protected void scale(PrimordialDemonEntity entity, PoseStack poseStack, float partialTickTime) {
        float baseScale = entity.getDemonType() != null ? entity.getDemonType().getScale() : 1.0F;
        if (entity.isWinged()) {
            baseScale *= 1.07F; // Dạng Ma Vương có cánh vươn cao lớn uy nghiêm hơn
        }
        poseStack.scale(baseScale, baseScale, baseScale);
    }
}
