/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package net.unusual.block_factorys_bosses.client.renderer;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelsoul_knight_wither_skeleton;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;

@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SoulKnightWitherSkeletonRenderer
extends MobRenderer<SoulKnightWitherSkeletonEntity, Modelsoul_knight_wither_skeleton<SoulKnightWitherSkeletonEntity>> {
    private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/entities/soul_knight_wither_skeleton_sword.png");

    public SoulKnightWitherSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelsoul_knight_wither_skeleton(context.bakeLayer(Modelsoul_knight_wither_skeleton.LAYER_LOCATION)), 0.5f);
    }

    public ResourceLocation getTextureLocation(SoulKnightWitherSkeletonEntity entity) {
        return TEXTURE_LOCATION;
    }
}

