package com.minhphuc.weapons.network;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.content.infinitygauntlet.ServerboundCycleRealitySubModePacket;
import com.minhphuc.weapons.content.infinitygauntlet.ServerboundSelectModePacket;
import com.minhphuc.weapons.content.infinitygauntlet.ServerboundSpaceTeleportPacket;
import dev.architectury.networking.NetworkChannel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ModMessages {
    private static final NetworkChannel CHANNEL = NetworkChannel.create(ResourceLocation.fromNamespaceAndPath(WeaponsMod.MOD_ID, "messages"));

    public static void register() {
        CHANNEL.register(ServerboundSelectModePacket.class,
                ServerboundSelectModePacket::encode,
                ServerboundSelectModePacket::new,
                ServerboundSelectModePacket::handle);

        CHANNEL.register(ServerboundCycleRealitySubModePacket.class,
                ServerboundCycleRealitySubModePacket::encode,
                ServerboundCycleRealitySubModePacket::new,
                ServerboundCycleRealitySubModePacket::handle);

        CHANNEL.register(ClientboundOpenSpaceTeleportScreenPacket.class,
                ClientboundOpenSpaceTeleportScreenPacket::encode,
                ClientboundOpenSpaceTeleportScreenPacket::new,
                ClientboundOpenSpaceTeleportScreenPacket::handle);

        CHANNEL.register(ServerboundSpaceTeleportPacket.class,
                ServerboundSpaceTeleportPacket::encode,
                ServerboundSpaceTeleportPacket::new,
                ServerboundSpaceTeleportPacket::handle);

        CHANNEL.register(ServerboundCastBeelzebuthPacket.class,
                ServerboundCastBeelzebuthPacket::encode,
                ServerboundCastBeelzebuthPacket::new,
                ServerboundCastBeelzebuthPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        CHANNEL.sendToPlayer(player, message);
    }
}
