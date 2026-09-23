/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.resources.ResourceLocation
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.Animation
 *  software.bernie.geckolib.model.DefaultedEntityGeoModel
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import java.util.function.BiFunction;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.geckolib.util.CustomDefaultedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomEntityGeoModel<T extends GeoAnimatable>
extends DefaultedEntityGeoModel<T>
implements CustomDefaultedGeoModel<T> {
    @Nullable
    private final BiFunction<ResourceLocation, Boolean, RenderType> renderType;

    public CustomEntityGeoModel(ResourceLocation assetSubpath, @Nullable BiFunction<ResourceLocation, Boolean, RenderType> renderType) {
        super(assetSubpath);
        this.renderType = renderType;
    }

    public CustomEntityGeoModel(ResourceLocation assetSubpath) {
        this(assetSubpath, null);
    }

    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return CustomDefaultedGeoModel.buildFormattedAnimationPathImpl(this.subtype(), basePath);
    }

    @Nullable
    public Animation getAnimation(T animatable, String name) {
        return CustomDefaultedGeoModel.getAnimationImpl(() -> this.getAnimationResource(animatable), name, this.getAnimationResourceFallbacks(animatable));
    }

    @Nullable
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return this.renderType != null ? this.renderType.apply(texture, true) : super.getRenderType(animatable, texture);
    }
}

