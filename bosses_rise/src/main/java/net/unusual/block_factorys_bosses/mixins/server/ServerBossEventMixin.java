/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.BossEvent
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.unusual.block_factorys_bosses.mixins.server;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={ServerBossEvent.class})
public abstract class ServerBossEventMixin
extends BossEvent {
    public ServerBossEventMixin(UUID id, Component name, BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay) {
        super(id, name, color, overlay);
    }

    @Inject(method={"addPlayer"}, at={@At(value="INVOKE", target="Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V")})
    private void onAddPlayer(ServerPlayer player, CallbackInfo ci) {
        BossHandling.TrackedBoss boss = BossHandling.TRACKED_BOSSES.getOrDefault(this.getId(), null);
        if (boss != null) {
            net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new UpdateBossBarTypeMessage(this.getId(), boss));
        }
    }
}

