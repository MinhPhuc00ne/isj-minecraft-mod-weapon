package com.minhphuc.weapons.content.infinitygauntlet;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EmptyInfinityGauntletItem extends Item {

    public EmptyInfinityGauntletItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6§l[BẢO KHÍ NIDAVELLIR]"));
        tooltip.add(Component.literal("§eGăng Tay Vô Cực (Chưa Gắn Đá)"));
        tooltip.add(Component.literal("§7Chiếc găng tay rèn từ vàng ròng và thỏi netherite"));
        tooltip.add(Component.literal("§7bởi các thợ rèn lừng danh Nidavellir."));
        tooltip.add(Component.literal("§7Hiện tại các hốc khảm đá đều đang trống rỗng."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§d👉 Đặt lên Bàn Chế Tạo cùng đủ 6 Viên Đá Vô Cực"));
        tooltip.add(Component.literal("§d   để thức tỉnh Găng Tay Vô Cực Hoàn Chỉnh!"));
    }
}
