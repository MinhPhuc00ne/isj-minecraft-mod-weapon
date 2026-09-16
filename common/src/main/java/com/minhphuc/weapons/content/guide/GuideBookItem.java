package com.minhphuc.weapons.content.guide;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GuideBookItem extends Item {

    public GuideBookItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            com.minhphuc.weapons.client.gui.ClientGuideOpener.openScreen();
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Ánh kim thần thoại
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6§l[BÍ TÍCH THẦN THOẠI]"));
        tooltip.add(Component.literal("§b§lThánh Thư Thần Khí & Ma Vương"));
        tooltip.add(Component.literal("§7Cuốn sách ghi chép toàn bộ công thức rèn đúc,"));
        tooltip.add(Component.literal("§7cách thức tỉnh Chân Ma Vương và bí kíp tuyệt kỹ."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e👉 Nhấn §a[Chuột Phải] §eđể mở giao diện hướng dẫn trực quan!"));
    }
}
