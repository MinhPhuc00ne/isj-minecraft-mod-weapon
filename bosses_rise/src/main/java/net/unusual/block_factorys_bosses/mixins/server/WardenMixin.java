/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.warden.Warden
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.unusual.block_factorys_bosses.mixins.server;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Warden.class})
public abstract class WardenMixin
extends Monster
implements VibrationSystem {
    protected WardenMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    private void onInit(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
        if (!level.isClientSide) {
            BossHandling.CommonHandler.getOrCreate((Warden)(Object)this);
        }
    }

    @Inject(method={"customServerAiStep"}, at={@At(value="TAIL")})
    private void onTick(CallbackInfo ci) {
        if (!this.level().isClientSide) {
            BossHandling.CommonHandler.getOrCreate((Warden)(Object)this).setProgress(this.getHealth() / this.getMaxHealth());
        }
    }
}

