package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DemonLordSeedItem extends Item {

    public DemonLordSeedItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Hiệu ứng hào quang ma pháp phát sáng
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            int souls = player.getPersistentData().getInt("TensuraCollectedSouls");
            boolean isTrueDemonLord = player.getPersistentData().getBoolean("TensuraTrueDemonLord");

            if (isTrueDemonLord) {
                player.displayClientMessage(
                    Component.literal("§d[HẠT GIỐNG MA VƯƠNG] §fBạn đã là §d§lChân Ma Vương! §fKỹ năng Bạo Thực Vương Beelzebuth đã được mở khóa hoàn toàn."),
                    true
                );
            } else if (souls >= 64) {
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §aĐã thu thập đủ " + souls + "/64 Linh Hồn! Hãy leo lên giường ĐI NGỦ để thức tỉnh Ma Vương!"),
                    true
                );
            } else {
                player.displayClientMessage(
                    Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §fTiến độ thu thập Linh Hồn: §c" + souls + "/64§f. Tiêu diệt thêm sinh vật để tích lũy linh hồn."),
                    true
                );
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§d§lVật Phẩm Huyền Thoại Anime Tensura"));
        tooltip.add(Component.literal("§7Bằng chứng về tư chất trở thành kẻ thống trị thế giới."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Điều Kiện Thức Tỉnh Chân Ma Vương:"));
        tooltip.add(Component.literal("§71. Cầm Hạt Giống Ma Vương trong túi đồ"));
        tooltip.add(Component.literal("§72. Diệt quái tích lũy đủ §664 Linh Hồn Ma Vương"));
        tooltip.add(Component.literal("§73. Leo lên giường §aĐI NGỦ §7để khởi động Lễ Hội Thức Tỉnh!"));
    }
}
