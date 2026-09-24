/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FlamingSkeletonGuardFireballRenderer
extends GeoEntityRenderer<FlamingSkeletonGuardFireballEntity> {
    private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"flaming_skeleton_guard_fireball");

    public FlamingSkeletonGuardFireballRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(BASE_TEXTURE));
        this.addRenderLayer(EmissiveRenderLayer.fromBaseTexture(this, BASE_TEXTURE));
    }
}

