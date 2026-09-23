/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 *  net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.DodgeRollMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(value={Dist.CLIENT})
public class BossesRiseKeyMappings {
    public static final KeyMapping DODGE_ROLL = new KeyMapping("key.block_factorys_bosses.dodge_roll", 90, "key.categories.movement"){
        private boolean isDownOld = false;

        public void setDown(boolean isDown) {
            LocalPlayer player;
            super.setDown(isDown);
            if (this.isDownOld != isDown && isDown && (player = Minecraft.getInstance().player) != null) {
                try {
                    net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(new DodgeRollMessage(0, player.input.leftImpulse, player.input.forwardImpulse));
                } catch (Throwable ignored) {}
                DodgeRollMessage.pressAction((Player)player, 0, player.input.leftImpulse, player.input.forwardImpulse);
            }
            this.isDownOld = isDown;
        }
    };

    public static void register() {
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(DODGE_ROLL);
    }

    public static class KeyEventListener {
        public static void onClientTick() {
            if (Minecraft.getInstance().screen == null) {
                DODGE_ROLL.consumeClick();
            }
        }
    }
}

