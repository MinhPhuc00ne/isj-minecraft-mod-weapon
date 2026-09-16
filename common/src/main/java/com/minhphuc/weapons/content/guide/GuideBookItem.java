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
        tooltip.add(Component.translatable("tooltip.weapons.celestial_tome.title"));
        tooltip.add(Component.translatable("tooltip.weapons.celestial_tome.name"));
        tooltip.add(Component.translatable("tooltip.weapons.celestial_tome.desc1"));
        tooltip.add(Component.translatable("tooltip.weapons.celestial_tome.desc2"));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.weapons.celestial_tome.usage"));
    }
}
