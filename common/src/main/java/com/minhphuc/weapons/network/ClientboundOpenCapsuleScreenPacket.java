package com.minhphuc.weapons.network;

import com.minhphuc.weapons.client.gui.ClientDemonCommandOpener;
import com.minhphuc.weapons.client.gui.IncubationCapsuleScreen;
import com.minhphuc.weapons.entity.tensura.DemonType;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class ClientboundOpenCapsuleScreenPacket {
    private final int x;
    private final int y;
    private final int z;
    private final int demonTypeIndex;

    public ClientboundOpenCapsuleScreenPacket(int x, int y, int z, int demonTypeIndex) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.demonTypeIndex = demonTypeIndex;
    }

    public ClientboundOpenCapsuleScreenPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.demonTypeIndex = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.demonTypeIndex);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            DemonType demonType = DemonType.byIndex(demonTypeIndex);
            Minecraft.getInstance().setScreen(new IncubationCapsuleScreen(new BlockPos(x, y, z), demonType));
        });
    }
}
