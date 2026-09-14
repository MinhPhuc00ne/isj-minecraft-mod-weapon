package com.minhphuc.weapons.network;

import com.minhphuc.weapons.content.divine.MoonlightSwordItem;
import com.minhphuc.weapons.data.EntityDataHelper;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ServerboundCycleSkillPacket {

    public ServerboundCycleSkillPacket() {
    }

    public ServerboundCycleSkillPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            ItemStack swordStack = mainHand.getItem() instanceof MoonlightSwordItem
                    ? mainHand
                    : (offHand.getItem() instanceof MoonlightSwordItem ? offHand : ItemStack.EMPTY);

            if (!swordStack.isEmpty()) {
                // 1. Chuyển đổi skill cho Nguyệt Quang Thần Tế Kiếm
                MoonlightSwordItem.cycleSkill(player, swordStack);
            } else {
                // 2. Chuyển đổi skill cho Chân Ma Vương (khi không cầm kiếm)
                boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
                if (isTrueDemonLord) {
                    int current = EntityDataHelper.getCustomData(player).getInt("TensuraDemonLordSkill");
                    int next = (current + 1) % 2; // 0: Thôn Phệ, 1: Hủ Hóa
                    EntityDataHelper.getCustomData(player).putInt("TensuraDemonLordSkill", next);

                    String skillName = next == 0
                            ? "§d§l1. Bạo Thực Vương: Thôn Phệ (Predator)"
                            : "§c§l2. Bạo Thực Vương: Hủ Hóa & Bạo Liệt (Corrosion)";

                    player.displayClientMessage(
                        Component.literal("§d§l[CHÂN MA VƯƠNG] §fKỹ năng được chọn: " + skillName + " §7(Chuột Phải để thi triển)"),
                        true
                    );

                    float pitch = 1.0F + (next * 0.4F);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, pitch);
                } else {
                    player.displayClientMessage(
                        Component.literal("§c§l[THÔNG BÁO] §fBạn chưa thức tỉnh thành Chân Ma Vương hoặc chưa cầm Nguyệt Quang Thần Tế Kiếm!"),
                        true
                    );
                }
            }
        });
    }
}
