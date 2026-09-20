package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.ModEntities;
import com.minhphuc.weapons.entity.tensura.VelgryndEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
 * Nghịch Lân Chước Nhiệt: Lời Thề Long Chủng (Velgrynd Reverse Scale):
 * - Rơi ra khi người chơi đánh thắng Chước Nhiệt Long Velgrynd.
 * - Khi kích hoạt: Tiêu hao vật phẩm, triệu hồi Velgrynd Đồng Minh Trợ Chiến 1 lần duy nhất.
 * - Velgrynd đồng minh thi triển toàn bộ skill của người chơi, quét sạch mọi mối nguy hại (trừ Ác ma đã ký khế ước).
 * - Sau khi tiêu diệt sạch nguy hại xung quanh, cô ấy sẽ biến mất. Muốn có lại phải dùng Hạt Giống Long Chủng đánh thắng lại.
 */
public class VelgryndReverseScaleItem extends Item {

    public VelgryndReverseScaleItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer sp) {
            BlockPos clickPos = context.getClickedPos().relative(context.getClickedFace());
            summonAlliedVelgrynd(serverLevel, sp, Vec3.atBottomCenterOf(clickPos));

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
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer sp) {
            Vec3 spawnPos = player.position().add(player.getLookAngle().scale(3.0D));
            summonAlliedVelgrynd(serverLevel, sp, spawnPos);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void summonAlliedVelgrynd(ServerLevel level, ServerPlayer player, Vec3 pos) {
        VelgryndEntity velgrynd = new VelgryndEntity(ModEntities.VELGRYND.get(), level);
        velgrynd.moveTo(pos.x, pos.y, pos.z, player.getYRot(), 0.0F);
        velgrynd.setAlliedSummon(player);
        level.addFreshEntity(velgrynd);

        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 4.0F, 1.1F);
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.5F, 1.2F);
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.x, pos.y + 1.2D, pos.z, 3, 0.5D, 0.5D, 0.5D, 0.05D);
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + 1.2D, pos.z, 80, 1.0D, 1.5D, 1.0D, 0.1D);

        velgrynd.broadcastDialogue("Theo lời thề của Nghịch Lân, ta sẽ tạm thời quét sạch toàn bộ mối nguy hại cho ngươi! Hãy xem sức mạnh thật sự của Long Chủng!");
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§c§l[BẢO VẬT LONG CHỦNG CÔNG NHẬN]"));
        tooltip.add(Component.literal("§6§lNghịch Lân Chước Nhiệt: Lời Thề Long Chủng"));
        tooltip.add(Component.literal("§7Minh chứng cho kẻ phàm nhân đã đánh bại được Chước Nhiệt Long Velgrynd."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e⚡ Đặc Quyền Triệu Hồi (Dùng 1 Lần):"));
        tooltip.add(Component.literal("§a- Triệu hồi Velgrynd Đồng Minh giáng thế trợ chiến!"));
        tooltip.add(Component.literal("§e- Nhấn Chuột Phải vào cô ấy để xem toàn bộ chỉ số chi tiết."));
        tooltip.add(Component.literal("§c- Cô ấy có thể sử dụng TOÀN BỘ kỹ năng tối thượng của người chơi!"));
        tooltip.add(Component.literal("§b- Quét sạch mọi quái vật xung quanh (Tuyệt đối KHÔNG đánh ác ma đồng minh)."));
        tooltip.add(Component.literal("§7- Tự động biến mất khi an toàn. Muốn cô ấy giúp lại phải khiêu chiến đánh bại từ đầu!"));
    }
}
