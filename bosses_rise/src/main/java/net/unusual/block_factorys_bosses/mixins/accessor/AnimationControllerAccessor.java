/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimationController
 *  software.bernie.geckolib.animation.EasingType
 */
package net.unusual.block_factorys_bosses.mixins.accessor;

import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.EasingType;

@Mixin(value={AnimationController.class}, remap=false)
public interface AnimationControllerAccessor<T extends GeoAnimatable> {
    @Accessor(value="isJustStarting", remap=false)
    public void setIsJustStarting(boolean var1);

    @Accessor(value="overrideEasingTypeFunction", remap=false)
    public Function<T, EasingType> getOverrideEasingTypeFunction();
}

