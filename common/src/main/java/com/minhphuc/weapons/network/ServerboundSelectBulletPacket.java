package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.tensura.CarreraBulletItem.BulletType;
import com.minhphuc.weapons.content.tensura.GoldenGunItem;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ServerboundSelectBulletPacket {
    private final int bulletId;

    public ServerboundSelectBulletPacket(int bulletId) {
        this.bulletId = bulletId;
    }

    public ServerboundSelectBulletPacket(FriendlyByteBuf buf) {
        this.bulletId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.bulletId);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            ItemStack gunStack = player.getMainHandItem();
            if (!(gunStack.getItem() instanceof GoldenGunItem)) {
                gunStack = player.getOffhandItem();
            }

            if (gunStack.getItem() instanceof GoldenGunItem) {
                BulletType selected = BulletType.fromId(bulletId);
                GoldenGunItem.setSelectedBullet(gunStack, selected);

                // Thử nạp đạn vào ổ xoay nếu ổ đạn trống
                GoldenGunItem.reloadFromInventory(player, gunStack, selected);

                int ammo = GoldenGunItem.getAmmo(gunStack);
                player.displayClientMessage(
                        Component.literal("§6§l[SÚNG HOÀNG KIM] §aĐã chọn: " + selected.vietName + " §e(Ổ đạn: " + ammo + "/6 viên)"),
                        true
                );

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.PLAYERS, 1.5F, 1.4F);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.2F);
            }
        });
    }
}
