/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.GeckoLibConstants
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.Animation
 *  software.bernie.geckolib.cache.GeckoLibCache
 *  software.bernie.geckolib.loading.object.BakedAnimations
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.geckolib.util.BossesRiseAnimationCache;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface CustomDefaultedGeoModel<T extends GeoAnimatable> {
    public static ResourceLocation buildFormattedAnimationPathImpl(String modelSubtype, ResourceLocation basePath) {
        return ResourceLocation.fromNamespaceAndPath((String)basePath.getNamespace(), (String)("anim_bosses_rise/" + modelSubtype + "/" + basePath.getPath() + ".animation.json"));
    }

    @Nullable
    public static Animation getAnimationImpl(Supplier<ResourceLocation> modelGetAnimationResource, String name, ResourceLocation[] fallbacks) {
        ResourceLocation location = modelGetAnimationResource.get();
        BakedAnimations bakedAnimations = BossesRiseAnimationCache.getBakedAnimations().get(location);
        Animation animation = bakedAnimations.getAnimation(name);
        ResourceLocation[] resourceLocationArray = fallbacks;
        int n = resourceLocationArray.length;
        for (int i = 0; i < n; ++i) {
            ResourceLocation fallbackLocation;
            location = fallbackLocation = resourceLocationArray[i];
            bakedAnimations = (BakedAnimations)GeckoLibCache.getBakedAnimations().get(location);
            Animation animation2 = animation = bakedAnimations != null ? bakedAnimations.getAnimation(name) : null;
            if (animation == null) continue;
            return animation;
        }
        if (animation != null) {
            return animation;
        }
        if (bakedAnimations == null) {
            if (!location.getPath().contains("anim_bosses_rise/")) {
                throw GeckoLibConstants.exception((ResourceLocation)location, (String)"Invalid animation resource path provided - Bosses'Rise animations must be placed in assets/<modid>/anim_bosses_rise/");
            }
            throw GeckoLibConstants.exception((ResourceLocation)location, (String)"Unable to find animation file.");
        }
        return null;
    }
}

