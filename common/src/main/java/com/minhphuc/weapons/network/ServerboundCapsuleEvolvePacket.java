package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.tensura.capsule.IncubationCapsuleManager;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class ServerboundCapsuleEvolvePacket {
    private final int x;
    private final int y;
    private final int z;
    private final int choice; // 1: Body, 2: Name, 3: Both
    private final String customName;

    public ServerboundCapsuleEvolvePacket(int x, int y, int z, int choice, String customName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.choice = choice;
        this.customName = customName != null ? customName : "";
    }

    public ServerboundCapsuleEvolvePacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.choice = buf.readInt();
        this.customName = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.choice);
        buf.writeUtf(this.customName);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;
            BlockPos pos = new BlockPos(x, y, z);
            IncubationCapsuleManager.startEvolution(player.serverLevel(), pos, choice, customName);
        });
    }
}
