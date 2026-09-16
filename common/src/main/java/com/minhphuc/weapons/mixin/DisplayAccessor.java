package com.minhphuc.weapons.mixin;

import com.mojang.math.Transformation;
import net.minecraft.world.entity.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Display.class)
public interface DisplayAccessor {
    @Invoker("setTransformation")
    void weapons$setTransformation(Transformation transformation);

    @Invoker("setBillboardConstraints")
    void weapons$setBillboardConstraints(Display.BillboardConstraints constraints);

    @Invoker("setGlowColorOverride")
    void weapons$setGlowColorOverride(int glowColorOverride);

    @Invoker("setViewRange")
    void weapons$setViewRange(float viewRange);

    @Invoker("setTransformationInterpolationDuration")
    void weapons$setTransformationInterpolationDuration(int duration);
}
