package net.neoforged.neoforge.network.handling;

import java.util.concurrent.CompletableFuture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;

public interface IPayloadContext {
    Player player();

    default CompletableFuture<Void> enqueueWork(Runnable runnable) {
        try {
            runnable.run();
            return CompletableFuture.completedFuture(null);
        } catch (Throwable t) {
            return CompletableFuture.failedFuture(t);
        }
    }

    default PacketFlow flow() {
        return PacketFlow.CLIENTBOUND;
    }

    default ConnectionDummy connection() {
        return new ConnectionDummy();
    }

    class ConnectionDummy {
        public void disconnect(Component reason) {}
    }
}
