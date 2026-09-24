package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.ModEntities;
import com.minhphuc.weapons.entity.tensura.VelgryndEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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

import java.util.List;

public class ScorchDragonCoreItem extends Item {

    public ScorchDragonCoreItem(Properties properties) {
        super(properties.stacksTo(16).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
            Player player = context.getPlayer();

            summonVelgrynd(serverLevel, spawnPos, player);

            if (player != null && !player.getAbilities().instabuild) {
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
            BlockPos spawnPos = player.blockPosition().relative(player.getDirection(), 4);
            summonVelgrynd(serverLevel, spawnPos, player);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void summonVelgrynd(ServerLevel serverLevel, BlockPos pos, Player player) {
        VelgryndEntity velgrynd = new VelgryndEntity(ModEntities.VELGRYND.get(), serverLevel);
        velgrynd.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, player != null ? player.getYRot() + 180.0F : 0.0F, 0.0F);

        serverLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 5.0F, 0.9F);
        serverLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 4.0F, 0.8F);
        serverLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 4.0F, 1.2F);

        serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 4, 0.5D, 0.5D, 0.5D, 0);
        serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 150, 1.5D, 2.0D, 1.5D, 0.1D);
        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 1.2D, 1.8D, 1.2D, 0.15D);

        serverLevel.addFreshEntity(velgrynd);

        velgrynd.broadcastDialogue("Lũ giun dế hạ đẳng... Các ngươi nghĩ ai cho phép các ngươi ngẩng đầu nhìn thẳng vào Chước Nhiệt Long Velgrynd ta?!");
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§c§l[VẬT PHẨM TRIỆU HỒI LONG CHỦNG]"));
        tooltip.add(Component.literal("§6§lHỏa Diễm Long Tinh (Scorch Dragon Core)"));
        tooltip.add(Component.literal("§7Kết tinh ma tố nhiệt lượng vô tận từ Chước Nhiệt Long Velgrynd."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Nhấn Chuột Phải để triệu hồi:"));
        tooltip.add(Component.literal("§c- Chước Nhiệt Long Velgrynd (Boss Tối Thượng)"));
        tooltip.add(Component.literal("§7- Miễn nhiễm hầu hết đòn đánh thường, cần §e4 đòn đánh tối thượng §7để hạ gục"));
        tooltip.add(Component.literal("§7- Ngoại lệ: §dLong Tinh Bộc Viêm Bá (Dragon Nova) §7sẽ xóa sổ trực tiếp"));
        tooltip.add(Component.literal("§7- Tự động triệu hồi §6Tồn Tại Song Song (1v1) §7khi bị hội đồng"));
    }
}
