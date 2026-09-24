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
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.DragonGuardSwordEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonGuardSwordRenderer
extends GeoEntityRenderer<DragonGuardSwordEntity> {
    public DragonGuardSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomEntityGeoModel(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"dragon_guard_sword")));
    }
}

