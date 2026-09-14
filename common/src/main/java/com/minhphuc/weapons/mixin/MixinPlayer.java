package com.minhphuc.weapons.mixin;

import com.minhphuc.weapons.content.tensura.TensuraEvents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer {

    @Inject(method = "stopSleepInBed", at = @At("HEAD"))
    private void weapons$onStopSleepInBed(boolean resetSleepTimer, boolean updateLevelForSleepingPlayers, CallbackInfo ci) {
        TensuraEvents.onPlayerWakeUp((Player) (Object) this);
    }

    @Inject(method = "stopSleeping", at = @At("HEAD"))
    private void weapons$onStopSleeping(CallbackInfo ci) {
        TensuraEvents.onPlayerWakeUp((Player) (Object) this);
    }
}
