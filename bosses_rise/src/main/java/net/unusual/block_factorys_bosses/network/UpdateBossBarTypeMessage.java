/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
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

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.unusual.block_factorys_bosses.util.BossHandling;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record UpdateBossBarTypeMessage(UUID id, BossHandling.TrackedBoss trackedBoss) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<UpdateBossBarTypeMessage> TYPE = new CustomPacketPayload.Type(BossesRise.prefix("bossbar_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBossBarTypeMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> {
        buffer.writeUUID(message.id);
        buffer.writeJsonWithCodec(BossHandling.TrackedBoss.CODEC, message.trackedBoss);
    }, buffer -> new UpdateBossBarTypeMessage(buffer.readUUID(), (BossHandling.TrackedBoss)buffer.readJsonWithCodec(BossHandling.TrackedBoss.CODEC)));

    public CustomPacketPayload.Type<UpdateBossBarTypeMessage> type() {
        return TYPE;
    }

    public static void handleData(UpdateBossBarTypeMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.CLIENTBOUND) {
            context.enqueueWork(() -> ClientHandler.handle(message, context)).exceptionally(e -> {
                context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                return null;
            });
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, UpdateBossBarTypeMessage::handleData);
    }

    private static interface ClientHandler {
        public static void handle(UpdateBossBarTypeMessage message, IPayloadContext context) {
            BossHandling.TRACKED_BOSSES.put(message.id, message.trackedBoss);
        }
    }
}

