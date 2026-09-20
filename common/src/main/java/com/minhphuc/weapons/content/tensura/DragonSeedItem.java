package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.data.EntityDataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Hạt Giống Long Chủng (Dragon Factor Seed):
 * - Nếu người dùng chưa là Chân Ma Vương: Bị phản phệ rút 90% Máu hiện tại.
 * - Nếu người dùng đã là Chân Ma Vương: Tiêu hao 30% Máu hiện tại để cung cấp ma tố.
 * - Khởi động Nghi Lễ Giáng Thế 3D hoành tráng của Chước Nhiệt Long Velgrynd!
 */
public class DragonSeedItem extends Item {

    public DragonSeedItem(Properties properties) {
        super(properties.stacksTo(16).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player != null) {
            BlockPos clickPos = context.getClickedPos().relative(context.getClickedFace());
            triggerRitual(serverLevel, player, Vec3.atBottomCenterOf(clickPos));

            if (!player.getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            Vec3 spawnPos = player.position().add(player.getLookAngle().scale(5.0D));
            triggerRitual(serverLevel, player, spawnPos);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void triggerRitual(ServerLevel level, Player player, Vec3 pos) {
        boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");

        if (!isTrueDemonLord) {
            // Chưa là Chân Ma Vương: Phạt rút 90% máu hiện tại!
            float currentHp = player.getHealth();
            float dmg = Math.max(1.0F, currentHp * 0.90F);
            player.hurt(level.damageSources().magic(), dmg);

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 2.5F, 0.65F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0F, 0.8F);

            player.displayClientMessage(
                    Component.literal("§4§l[PHẠT NẶNG] §cNgươi CHƯA PHẢI CHÂN MA VƯƠNG! Ma tố Long Chủng phản phệ rút cạn 90% sinh lực của ngươi!"),
                    false
            );
        } else {
            // Đã là Chân Ma Vương: Tiêu hao 30% máu hiện tại để kích hoạt nghi lễ
            float currentHp = player.getHealth();
            float dmg = Math.max(1.0F, currentHp * 0.30F);
            player.hurt(level.damageSources().magic(), dmg);

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 2.0F, 1.2F);

            player.displayClientMessage(
                    Component.literal("§6§l[HIẾN TẾ CHÂN MA VƯƠNG] §eMa tố của ngươi đã cung cấp 30% sinh lực để đánh thức Chước Nhiệt Long!"),
                    false
            );
        }

        // Bắt đầu nghi lễ giáng thế 3D
        VelgryndSummonRitual.start(level, (net.minecraft.server.level.ServerPlayer) player, pos);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6§l[VẬT PHẨM TRIỆU HỒI LONG TỘC]"));
        tooltip.add(Component.literal("§c§lHạt Giống Long Chủng (Dragon Factor Seed)"));
        tooltip.add(Component.literal("§7Chứa đựng mầm mống ma tố tinh khiết của Chước Nhiệt Long Velgrynd."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Hiệu Ứng Khi Kích Hoạt (Chuột Phải):"));
        tooltip.add(Component.literal("§c- Chưa là Chân Ma Vương: §4Bị phản phệ mất 90% Máu hiện tại!"));
        tooltip.add(Component.literal("§a- Đã là Chân Ma Vương: §eHiến tế 30% Máu hiện tại để mở pháp trận."));
        tooltip.add(Component.literal("§6- Kích hoạt Nghi Lễ Giáng Thế 3D: Sấm sét, ma trận, cột sáng & rồng lượn!"));
    }
}
