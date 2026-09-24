/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.ResourceLocation
 */
package net.unusual.block_factorys_bosses.client.renderer;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelsoul_skeleton;
import net.unusual.block_factorys_bosses.client.renderer.GlowRenderLayer;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;

@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SoulSkeletonRenderer
extends MobRenderer<SoulSkeletonEntity, Modelsoul_skeleton<SoulSkeletonEntity>> {
    private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/entities/soul_skeleton.png");
    private static final ResourceLocation GLOW_TEXTURE_LOCATION = BossesRise.prefix("textures/entities/soul_skeleton_glow.png");

    public SoulSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelsoul_skeleton(context.bakeLayer(Modelsoul_skeleton.LAYER_LOCATION)), 0.5f);
        this.addLayer((RenderLayer)new GlowRenderLayer(this, RenderType.eyes((ResourceLocation)GLOW_TEXTURE_LOCATION)));
    }

    public ResourceLocation getTextureLocation(SoulSkeletonEntity entity) {
        return TEXTURE_LOCATION;
    }
}

