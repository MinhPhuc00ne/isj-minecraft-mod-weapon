package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DemonLordSoulItem extends Item {

    public DemonLordSoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§b§lLinh Hồn Tích Lũy Ma Vương"));
        tooltip.add(Component.literal("§7Nguồn năng lượng huyền bí cần thiết để nuôi dưỡng Hạt Giống Ma Vương."));
        tooltip.add(Component.literal("§e⚡ Cần đủ §664 Linh Hồn §eđể kích hoạt Lễ Hội Thức Tỉnh (Harvest Festival)."));
    }
}
