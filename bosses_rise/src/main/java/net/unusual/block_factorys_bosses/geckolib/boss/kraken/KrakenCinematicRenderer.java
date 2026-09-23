/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.joml.Vector3f
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 */
package net.unusual.block_factorys_bosses.geckolib.boss.kraken;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenCinematicEntity;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneData;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneDataCache;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneDataCacheLayer;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KrakenCinematicRenderer
extends GeoEntityRenderer<KrakenCinematicEntity>
implements CinematicRenderer<KrakenCinematicEntity> {
    private static final ResourceLocation TEXTURE = BossesRise.prefix("textures/entity/kraken.png");

    public KrakenCinematicRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new KrakenCinematicModel(BossesRise.prefix("kraken_cinematic")));
        this.addRenderLayer(BoneDataCacheLayer.forEntity(this).withAlwaysWanted("camera"));
    }

    public boolean shouldRender(KrakenCinematicEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Pair<Float, Float> getCinematicYawAndPitch(KrakenCinematicEntity animatable, GeoBone cameraBone, LocalPlayer player) {
        Vector3f lookDir = new Vector3f(0.0f, 0.0f, 1.0f);
        BoneData boneData = BoneDataCache.getInitializedBoneData(animatable, cameraBone.getName());
        if (boneData != null) {
            boneData.worldTransform.transformDirection(lookDir);
        }
        float yaw = (float)Math.atan2(lookDir.x(), -lookDir.z()) * 57.295776f;
        float pitch = (float)Math.atan2(lookDir.y(), Mth.sqrt((float)(lookDir.x() * lookDir.x() + lookDir.z() * lookDir.z()))) * 57.295776f;
        return new Pair((Object)Float.valueOf(yaw), (Object)Float.valueOf(pitch));
    }

    private static class KrakenCinematicModel
    extends CustomEntityGeoModel<KrakenCinematicEntity> {
        public KrakenCinematicModel(ResourceLocation location) {
            super(location);
        }

        public ResourceLocation getTextureResource(KrakenCinematicEntity kraken) {
            return TEXTURE;
        }
    }
}

