/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 *  software.bernie.geckolib.animation.AnimatableManager
 */
package net.unusual.block_factorys_bosses.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import software.bernie.geckolib.animation.AnimatableManager;

@Mixin(value={AnimatableManager.class}, remap=false)
public interface AnimatableManagerAccessor {
    @Invoker(value="finishFirstTick", remap=false)
    public void invokeFinishFirstTick();
}

