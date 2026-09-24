/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.unusual.block_factorys_bosses.mixins.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.unusual.block_factorys_bosses.event.DragonArmorEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GameRenderer.class})
public class GameRendererNightVisionMixin {
    @Inject(method={"getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/effect/MobEffectInstance;getDuration()I")}, cancellable=true)
    private static void bossesrise$getNightVisionScale(LivingEntity livingEntity, float nanoTime, CallbackInfoReturnable<Float> cir) {
        if (DragonArmorEvents.shouldDisableNightVisionFlashing(livingEntity)) {
            cir.setReturnValue(1.0f);
        }
    }
}

