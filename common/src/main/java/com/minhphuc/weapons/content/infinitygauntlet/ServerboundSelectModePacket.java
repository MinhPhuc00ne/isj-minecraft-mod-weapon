package com.minhphuc.weapons.content.infinitygauntlet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ServerboundSelectModePacket {
    private final int modeOrdinal;

    public ServerboundSelectModePacket(int modeOrdinal) {
        this.modeOrdinal = modeOrdinal;
    }

    public ServerboundSelectModePacket(FriendlyByteBuf buf) {
        this.modeOrdinal = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.modeOrdinal);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            ItemStack heldStack = player.getMainHandItem();
            if (!(heldStack.getItem() instanceof InfinityGauntletItem)) {
                heldStack = player.getOffhandItem();
            }

            if (heldStack.getItem() instanceof InfinityGauntletItem) {
                heldStack.getOrCreateTag().putInt(InfinityGauntletItem.NBT_MODE, modeOrdinal);
                String modeName = InfinityGauntletItem.getModeName(modeOrdinal);

                player.displayClientMessage(
                    Component.literal("§a[Găng Tay Vô Cực] Đã chọn chế độ: §e" + modeName),
                    true
                );

                if (modeOrdinal == 7) {
                    player.sendSystemMessage(
                        Component.literal("§d§l[GĂNG TAY VÔ CỰC - GEMINI AI] §fTrí tuệ 6 viên đá đã kích hoạt!\n" +
                                "§e💡 Bất kỳ tin nhắn nào bạn gõ trong chat lúc này đều sẽ trở thành mệnh lệnh cho Gemini AI thực thi!\n" +
                                "§7(Ví dụ: xóa sổ các sinh vật, cho trời mưa, tạo nhà kim cương, triệu hồi 5 rồng ender...)")
                    );
                }

                player.level().playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.EXPERIENCE_ORB_PICKUP,
                    SoundSource.PLAYERS,
                    0.8F, 1.2F
                );
            }
        });
    }
}
