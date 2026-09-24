/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.Animation
 *  software.bernie.geckolib.model.DefaultedBlockGeoModel
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.geckolib.util.CustomDefaultedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomBlockGeoModel<T extends GeoAnimatable>
extends DefaultedBlockGeoModel<T>
implements CustomDefaultedGeoModel<T> {
    public CustomBlockGeoModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return CustomDefaultedGeoModel.buildFormattedAnimationPathImpl(this.subtype(), basePath);
    }

    @Nullable
    public Animation getAnimation(T animatable, String name) {
        return CustomDefaultedGeoModel.getAnimationImpl(() -> this.getAnimationResource(animatable), name, this.getAnimationResourceFallbacks(animatable));
    }
}

