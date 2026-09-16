package com.minhphuc.weapons.client;

import com.minhphuc.weapons.content.infinitygauntlet.InfinityGauntletItem;
import com.minhphuc.weapons.content.infinitygauntlet.InfinityStoneSelectScreen;
import com.minhphuc.weapons.content.infinitygauntlet.ServerboundCycleRealitySubModePacket;
import com.minhphuc.weapons.init.ModKeyBindings;
import com.minhphuc.weapons.network.ModMessages;
import com.minhphuc.weapons.network.ServerboundCastBeelzebuthPacket;
import com.minhphuc.weapons.data.ItemStackDataHelper;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ClientInputEvents {
    private static long lastJumpPressTime = 0;

    public static void register() {
        ClientTickEvent.CLIENT_POST.register(mc -> {
            if (mc.player == null) return;

            // Nhấn Phím Cách 2 lần (Double-Tap Space): Bay lên và thoát khỏi kết giới Lục Nhậm Thần Khóa
            if (mc.options.keyJump.consumeClick()) {
                long now = System.currentTimeMillis();
                if (now - lastJumpPressTime <= 380) {
                    ModMessages.sendToServer(new com.minhphuc.weapons.network.ServerboundExitLiuRenBarrierPacket());
                }
                lastJumpPressTime = now;
            }

            if (ModKeyBindings.SELECT_STONE_KEY.consumeClick()) {
                if (mc.screen == null) {
                    mc.setScreen(new InfinityStoneSelectScreen());
                }
            }

            if (ModKeyBindings.BEELZEBUTH_KEY.consumeClick()) {
                // Nhấn phím Z: Chuyển đổi skill cho cả Nguyệt Quang Thần Tế Kiếm và Chân Ma Vương
                ModMessages.sendToServer(new com.minhphuc.weapons.network.ServerboundCycleSkillPacket());
            }
        });

        // Chuột phải vào không khí khi tay không: Thi triển Kỹ Năng Tối Thượng của Chân Ma Vương (chỉ gửi từ tay chính)
        InteractionEvent.CLIENT_RIGHT_CLICK_AIR.register((player, hand) -> {
            if (player == null || hand != net.minecraft.world.InteractionHand.MAIN_HAND) return;

            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.isEmpty()) {
                ModMessages.sendToServer(new ServerboundCastBeelzebuthPacket());
            }
        });

        InteractionEvent.CLIENT_LEFT_CLICK_AIR.register((player, hand) -> {
            if (player == null) return;

            // Bắn Tinh Tú Tuyệt Diệt khi nhấn Chuột Trái
            ModMessages.sendToServer(new com.minhphuc.weapons.network.ServerboundFireTaisuiStarPacket());

            ItemStack heldStack = player.getItemInHand(hand);
            if (heldStack.getItem() instanceof InfinityGauntletItem) {
                int mainMode = ItemStackDataHelper.getInt(heldStack, InfinityGauntletItem.NBT_MODE);
                if (mainMode == 3 || mainMode == 4 || mainMode == 5 || mainMode == 6) {
                    ModMessages.sendToServer(new ServerboundCycleRealitySubModePacket());
                }
            }
        });
    }
}
