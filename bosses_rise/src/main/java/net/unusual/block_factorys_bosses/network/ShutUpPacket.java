/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.Channel
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.PacketFlow
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package net.unusual.block_factorys_bosses.network;

import com.mojang.blaze3d.audio.Channel;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ShutUpPacket() implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<ShutUpPacket> TYPE = new CustomPacketPayload.Type(BossesRise.prefix("shut_up"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShutUpPacket> STREAM_CODEC = StreamCodec.of((buffer, message) -> {}, buffer -> new ShutUpPacket());

    public CustomPacketPayload.Type<ShutUpPacket> type() {
        return TYPE;
    }

    public static void handleData(ShutUpPacket message, IPayloadContext context) {
        if (context.flow() == PacketFlow.CLIENTBOUND) {
            context.enqueueWork(() -> ClientHandler.handle(message, context)).exceptionally(e -> {
                context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                return null;
            });
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, ShutUpPacket::handleData);
    }

    private static interface ClientHandler {
        public static void handle(ShutUpPacket message, IPayloadContext context) {
            Minecraft.getInstance().getSoundManager().soundEngine.instanceToChannel.forEach((soundInstance, channelHandle) -> {
                if (BossesRiseSounds.CANCELABLE_SOUNDS.contains(soundInstance.getLocation())) {
                    BossesRise.LOGGER.debug("YAY {}!", (Object)soundInstance.getLocation());
                    channelHandle.execute(Channel::stop);
                }
            });
        }
    }
}

