/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.PacketFlow
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package net.unusual.block_factorys_bosses.network;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PlayerAnimationMessage(PlayerAnimationHandler.QueuedPlayerAnimation animation, int id) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PlayerAnimationMessage> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"player_animation_msg"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerAnimationMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> {
        buffer.writeJsonWithCodec(PlayerAnimationHandler.QueuedPlayerAnimation.CODEC, message.animation);
        buffer.writeInt(message.id);
    }, buffer -> new PlayerAnimationMessage((PlayerAnimationHandler.QueuedPlayerAnimation)buffer.readJsonWithCodec(PlayerAnimationHandler.QueuedPlayerAnimation.CODEC), buffer.readInt()));

    public PlayerAnimationMessage(PlayerAnimationHandler.TimedPlayerAnimation animation, int id) {
        this(new PlayerAnimationHandler.QueuedPlayerAnimation(List.of(animation)), id);
    }

    public CustomPacketPayload.Type<PlayerAnimationMessage> type() {
        return TYPE;
    }

    public static void handleData(PlayerAnimationMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.CLIENTBOUND) {
            context.enqueueWork(() -> ClientHandler.handle(message, context)).exceptionally(e -> {
                context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                return null;
            });
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, PlayerAnimationMessage::handleData);
    }

    private static interface ClientHandler {
        public static void handle(PlayerAnimationMessage message, IPayloadContext context) {
            if (context.player().level() instanceof ClientLevel level) {
                if (level.getEntity(message.id()) instanceof Player player) {
                    PlayerAnimationHandler.fromPlayer(player).startAnimation(player, message.animation());
                }
            }
        }
    }
}

