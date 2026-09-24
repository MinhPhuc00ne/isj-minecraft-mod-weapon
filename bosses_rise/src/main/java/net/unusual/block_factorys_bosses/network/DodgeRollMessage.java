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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package net.unusual.block_factorys_bosses.network;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record DodgeRollMessage(int eventType, float leftImpulse, float forwardImpulse) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<DodgeRollMessage> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"key_dodge_roll"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DodgeRollMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> {
        buffer.writeInt(message.eventType);
        buffer.writeFloat(message.leftImpulse);
        buffer.writeFloat(message.forwardImpulse);
    }, buffer -> new DodgeRollMessage(buffer.readInt(), buffer.readFloat(), buffer.readFloat()));

    public CustomPacketPayload.Type<DodgeRollMessage> type() {
        return TYPE;
    }

    public static void handleData(DodgeRollMessage message, IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> DodgeRollMessage.pressAction(context.player(), message.eventType, message.leftImpulse, message.forwardImpulse)).exceptionally(e -> {
                context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                return null;
            });
        }
    }

    public static void pressAction(Player player, int type, float leftImpulse, float forwardImpulse) {
        if (player.level().getChunkAt(player.blockPosition()).isEmpty()) {
            return;
        }
        if (type == 0 && ((Boolean)ServerConfiguration.CAN_USE_ROLL.get()).booleanValue() && !player.isSwimming()) {
            RollAttachment roll = RollAttachment.fromPlayer(player);
            for (int i = 0; i < roll.rollCount(); ++i) {
                if (!(roll.getCooldown(i) <= 0.0f) || !roll.startRoll(player, leftImpulse, forwardImpulse)) continue;
                roll.startCooldown(i);
                break;
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, DodgeRollMessage::handleData);
    }
}

