package com.minhphuc.weapons.content.infinitygauntlet;

import com.minhphuc.weapons.network.ClientboundOpenSpaceTeleportScreenPacket;
import com.minhphuc.weapons.network.ModMessages;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.minhphuc.weapons.data.ItemStackDataHelper;
import java.util.List;

public class SpaceStoneAbility {

    public static void executeSpaceStone(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        if (player.isShiftKeyDown()) {
            // Shift + Chuột phải: Mở Menu Dịch Chuyển Đa Chiều & Địa Danh
            executeOpenTeleportMenu(level, player, gauntlet);
        } else {
            // Chuột phải: Kích hoạt Kỹ năng "Vương Cung Thành Trì"
            executeSpaceCitadel(level, player, gauntlet);
        }
    }

    /**
     * Kỹ Năng: Vương Cung Thành Trì (Kết Giới Bất Khả Xâm Phạm Bán Kính 3 Blocks)
     */
    public static void executeSpaceCitadel(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        int duration = 600; // 30 giây bảo hộ
        ItemStackDataHelper.putInt(gauntlet, "SpaceCitadelActiveTicks", duration);

        // Ban hiệu ứng bất tử 100% sát thương (Kháng sát thương tối cao, Kháng lửa)
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 255, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, duration, 0, false, false, true));

        // Âm thanh kết giới hình thành chói lọi
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0F, 1.5F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.8F);

        // Tác động đẩy lùi quái vật & xóa đạn bắn ngay tại thời điểm kích hoạt
        pulseCitadelBarrier(level, player);

        player.displayClientMessage(
            Component.literal("§9§l[ĐÁ KHÔNG GIAN - VƯƠNG CUNG THÀNH TRÌ] §fKích hoạt Kết Giới Không Gian Bất Khả Xâm Phạm! 🛡️"),
            true
        );

        player.getCooldowns().addCooldown(gauntlet.getItem(), 40); // 2 giây cooldown
    }

    /**
     * Xử lý liên tục hạt Kết Giới 3 blocks & Đẩy văng sinh vật / Đạn bắn ra xa
     */
    public static void tickCitadelBarrier(ServerLevel level, net.minecraft.world.entity.player.Player player, ItemStack gauntlet) {
        int ticks = ItemStackDataHelper.getInt(gauntlet, "SpaceCitadelActiveTicks");
        if (ticks <= 0) return;

        ItemStackDataHelper.putInt(gauntlet, "SpaceCitadelActiveTicks", ticks - 1);

        double radius = 3.0D;
        Vec3 center = player.position().add(0, 1.0D, 0);

        // Render Hào Quang Kết Giới Xanh Dương Nhạt bao quanh 3 blocks
        if (level.getGameTime() % 2 == 0) {
            for (int i = 0; i < 360; i += 20) {
                double rad = Math.toRadians(i);
                double x = center.x + Math.cos(rad) * radius;
                double z = center.z + Math.sin(rad) * radius;

                level.sendParticles(ParticleTypes.PORTAL, x, center.y, z, 1, 0.05D, 0.2D, 0.05D, 0.02D);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, center.y - 0.5D, z, 1, 0.02D, 0.1D, 0.02D, 0.01D);
                level.sendParticles(ParticleTypes.END_ROD, x, center.y + 0.5D, z, 1, 0.02D, 0.1D, 0.02D, 0.01D);
            }
        }

        pulseCitadelBarrier(level, player);
    }

    private static void pulseCitadelBarrier(ServerLevel level, net.minecraft.world.entity.player.Player player) {
        double radius = 3.5D;
        AABB barrierBox = player.getBoundingBox().inflate(radius);

        // 1. Đẩy lùi toàn bộ quái vật/sinh vật khỏi phạm vi 3 blocks
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, barrierBox, e -> e != player && e.isAlive());
        for (LivingEntity entity : entities) {
            Vec3 entityPos = entity.position();
            Vec3 playerPos = player.position();
            Vec3 pushDir = entityPos.subtract(playerPos).normalize();
            if (pushDir.lengthSqr() == 0) pushDir = new Vec3(1, 0, 0);

            // Đẩy văng mạnh mẽ ra ngoài bán kính kết giới
            entity.setDeltaMovement(pushDir.x * 2.0D, 0.5D, pushDir.z * 2.0D);
            entity.hasImpulse = true;

            level.sendParticles(ParticleTypes.SONIC_BOOM, entity.getX(), entity.getY() + 1.0D, entity.getZ(), 1, 0, 0, 0, 0);
        }

        // 2. Phá hủy & dội ngược mọi loại đạn (Cung tên, Tiếng thét Warden, Sọ Wither, Đạn lửa...)
        List<Entity> projectiles = level.getEntities((Entity) null, barrierBox, e -> e instanceof Projectile);
        for (Entity projectile : projectiles) {
            level.sendParticles(ParticleTypes.FLASH, projectile.getX(), projectile.getY(), projectile.getZ(), 1, 0, 0, 0, 0);
            level.playSound(null, projectile.getX(), projectile.getY(), projectile.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.5F, 1.5F);
            projectile.discard(); // Tiêu biến đạn tấn công tức thì
        }
    }

    private static void executeOpenTeleportMenu(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        ModMessages.sendToPlayer(new ClientboundOpenSpaceTeleportScreenPacket(), player);
    }
}
