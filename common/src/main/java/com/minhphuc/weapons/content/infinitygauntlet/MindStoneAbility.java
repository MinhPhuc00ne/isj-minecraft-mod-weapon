package com.minhphuc.weapons.content.infinitygauntlet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MindStoneAbility {

    public static void executeMindStone(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        if (player.isShiftKeyDown()) {
            // Shift + Chuột phải: Telekinesis Thao Túng Vật Lý
            executeTelekinesis(level, player, gauntlet);
        } else {
            // Chuột phải: Laze Tâm Trí Vision (Mind Beam)
            executeMindBeam(level, player, gauntlet);
        }
    }


    /**
     * Chế độ 1: 👑 Vương Quyền Chi Phối - Tẩy não quái vật xung quanh thành tay sai phục tùng
     */
    private static void executeMindControl(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        BlockPos center = player.blockPosition();
        int radius = 20;

        AABB area = new AABB(center).inflate(radius);
        List<Mob> mobs = level.getEntitiesOfClass(Mob.class, area, Mob::isAlive);

        for (Mob mob : mobs) {
            // Hiệu ứng phát sáng vàng kim & sóng não choáng váng
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1200, 0, false, false, true));
            mob.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1, false, false, true));

            // Xóa mục tiêu tấn công người chơi
            if (mob.getTarget() == player) {
                mob.setTarget(null);
            }

            // Đổi mục tiêu tấn công sang quái vật thù địch khác gần đó
            List<Mob> enemies = level.getEntitiesOfClass(Mob.class, mob.getBoundingBox().inflate(15.0D), e -> e != mob && e.isAlive());
            if (!enemies.isEmpty()) {
                mob.setTarget(enemies.get(0));
            }

            // Creeper bị tẩy não sẽ chạy nhanh đến phát nổ vào quái thù địch khác
            if (mob instanceof Creeper creeper) {
                creeper.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 2, false, false, true));
                if (!enemies.isEmpty()) {
                    creeper.setTarget(enemies.get(0));
                }
            }

            // Hạt hào quang ánh sáng vàng kim bao quanh quái vật bị tẩy não
            level.sendParticles(ParticleTypes.WAX_OFF, mob.getX(), mob.getY() + 1.0D, mob.getZ(), 10, 0.3D, 0.5D, 0.3D, 0.05D);
            level.sendParticles(ParticleTypes.CRIT, mob.getX(), mob.getY() + 1.0D, mob.getZ(), 5, 0.2D, 0.3D, 0.2D, 0.05D);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.5F);

        level.sendParticles(ParticleTypes.INSTANT_EFFECT, player.getX(), player.getY() + 1.0D, player.getZ(), 30, 1.5D, 1.0D, 1.5D, 0.05D);

        player.displayClientMessage(
            Component.literal("§e§l[ĐÁ TÂM TRÍ - VƯƠNG QUYỀN CHI PHỐI] §fBáo cáo. Đã tẩy não & chi phối " + mobs.size() + " cá thể quái vật thành tay sai! 👑"),
            true
        );

        player.getCooldowns().addCooldown(gauntlet.getItem(), 30);
    }

    /**
     * Chế độ 2: 🌀 Telekinesis - Nhấc bổng & ném sinh vật bay xa bằng lực tâm trí
     */
    private static void executeTelekinesis(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(35.0D));

        List<LivingEntity> targets = level.getEntitiesOfClass(
            LivingEntity.class,
            new AABB(start, end).inflate(3.0D),
            e -> e != player && e.isAlive()
        );

        if (targets.isEmpty()) {
            player.displayClientMessage(
                Component.literal("§e[ĐÁ TÂM TRÍ - TELEKINESIS] Báo cáo. Không tìm thấy mục tiêu cá thể phía trước!"),
                true
            );
            return;
        }

        LivingEntity target = targets.get(0);

        // Lực ném mạnh mẽ kéo mục tiêu ném bay xa
        Vec3 flingVec = look.scale(3.5D).add(0, 1.2D, 0);
        target.setDeltaMovement(flingVec);
        target.hasImpulse = true;
        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 3, false, false, true));

        // Hạt chùm tia kéo dài từ người chơi đến mục tiêu
        double distance = start.distanceTo(target.position());
        for (double d = 0; d <= distance; d += 0.5D) {
            Vec3 p = start.add(look.scale(d));
            level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 1, 0.05D, 0.05D, 0.05D, 0.01D);
        }

        level.sendParticles(ParticleTypes.SONIC_BOOM, target.getX(), target.getY() + 1.0D, target.getZ(), 1, 0, 0, 0, 0);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.4F);

        player.displayClientMessage(
            Component.literal("§e[ĐÁ TÂM TRÍ - TELEKINESIS] §fBáo cáo. Đã thao túng & ném cá thể " + target.getName().getString() + " bay xa! 🌀"),
            true
        );

        player.getCooldowns().addCooldown(gauntlet.getItem(), 15);
    }

    /**
     * Chế độ 3: ⚡ Mind Beam Laser - Bắn chùm laze tâm trí vàng kim từ trán Vision
     */
    private static void executeMindBeam(ServerLevel level, ServerPlayer player, ItemStack gauntlet) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.5F, 1.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 1.2F);

        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDistance = 60.0D;
        double step = 0.5D;

        Set<LivingEntity> killedEntities = new HashSet<>();

        Vec3 end = start.add(look.scale(maxDistance));
        AABB beamBox = new AABB(start, end).inflate(2.0D);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, beamBox, e -> e != player && e.isAlive());

        // 1. Quét tiêu diệt các sinh vật dọc chùm tia (1 query duy nhất thay vì 120 queries)
        for (LivingEntity target : targets) {
            Vec3 targetVec = target.position().add(0, target.getBbHeight() * 0.5D, 0).subtract(start);
            double proj = targetVec.dot(look);
            if (proj >= 0 && proj <= maxDistance) {
                double distSq = targetVec.lengthSqr() - (proj * proj);
                if (distSq <= 2.25D) { // Bán kính 1.5 blocks
                    com.minhphuc.weapons.content.tensura.TensuraEvents.handleMobDeathDrop(player, target);
                    target.hurt(level.damageSources().playerAttack(player), 100000.0F);
                    if (target.isAlive()) {
                        target.discard();
                    }
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
                    killedEntities.add(target);
                }
            }
        }

        // 2. Hiệu ứng hạt laze vàng kim thanh thoát (step = 1.0m)
        for (double d = 0; d <= maxDistance; d += 1.0D) {
            Vec3 currentVec = start.add(look.scale(d));

            level.sendParticles(ParticleTypes.INSTANT_EFFECT, currentVec.x, currentVec.y, currentVec.z, 1, 0.05D, 0.05D, 0.05D, 0.01D);
            if ((int) d % 2 == 0) {
                level.sendParticles(ParticleTypes.WAX_OFF, currentVec.x, currentVec.y, currentVec.z, 1, 0.04D, 0.04D, 0.04D, 0.02D);
            }

            if ((int) d % 10 == 0) {
                level.sendParticles(ParticleTypes.FLASH, currentVec.x, currentVec.y, currentVec.z, 1, 0, 0, 0, 0);
            }
        }

        player.displayClientMessage(
            Component.literal("§e[ĐÁ TÂM TRÍ - MIND BEAM] §fBáo cáo. Bắn chùm laze tâm trí Vision hủy diệt! (Tiêu diệt " + killedEntities.size() + " cá thể) ⚡"),
            true
        );

        player.getCooldowns().addCooldown(gauntlet.getItem(), 15);
    }
}
