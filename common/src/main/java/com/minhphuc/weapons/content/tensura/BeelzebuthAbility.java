package com.minhphuc.weapons.content.tensura;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeelzebuthAbility {

    public static void executeBeelzebuth(ServerLevel level, ServerPlayer player) {
        // Âm thanh vồ nuốt chửng của Đầu Rồng Hư Không Tím
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.5F, 0.6F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 0.5F);

        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 25.0D;

        int devouredEntities = 0;
        int devouredItems = 0;
        int devouredExplosives = 0;
        int devouredBlocks = 0;

        Set<Entity> processedEntities = new HashSet<>();

        // 1. Tạo Chùm Xoáy Rồng Hư Không Tím (Mô phỏng hiệu ứng Anime Beelzebuth)
        for (double d = 1.0D; d <= maxDist; d += 0.5D) {
            Vec3 centerPos = start.add(look.scale(d));
            double coneRadius = 0.5D + (d * 0.25D); // Mở rộng dần thành hình nón miệng rồng

            for (int i = 0; i < 360; i += 25) {
                double rad = Math.toRadians(i + (d * 30.0D)); // Xoáy cuộn 3D
                double ox = Math.cos(rad) * coneRadius;
                double oy = Math.sin(rad) * coneRadius;

                Vec3 particlePos = centerPos.add(ox, oy, 0);

                level.sendParticles(ParticleTypes.DRAGON_BREATH, particlePos.x, particlePos.y, particlePos.z, 2, 0.1D, 0.1D, 0.1D, 0.02D);
                level.sendParticles(ParticleTypes.PORTAL, particlePos.x, particlePos.y, particlePos.z, 1, 0.05D, 0.05D, 0.05D, 0.01D);
                level.sendParticles(ParticleTypes.SQUID_INK, particlePos.x, particlePos.y, particlePos.z, 1, 0.05D, 0.05D, 0.05D, 0.01D);

                // Mắt rồng màu vàng sáng chói ở trung tâm miệng rồng
                if (d == 5.0D && (i == 0 || i == 180)) {
                    level.sendParticles(ParticleTypes.WAX_OFF, particlePos.x, particlePos.y, particlePos.z, 3, 0.1D, 0.1D, 0.1D, 0.01D);
                }
            }

            if ((int)(d * 2) % 10 == 0) {
                level.sendParticles(ParticleTypes.FLASH, centerPos.x, centerPos.y, centerPos.z, 1, 0, 0, 0, 0);
            }

            // 2. Kéo & Nuốt Chửng Sinh Vật < 50% HP
            AABB hitBox = new AABB(
                centerPos.x - coneRadius, centerPos.y - coneRadius, centerPos.z - coneRadius,
                centerPos.x + coneRadius, centerPos.y + coneRadius, centerPos.z + coneRadius
            );

            List<Entity> nearby = level.getEntities((Entity) null, hitBox, e -> e != player && !processedEntities.contains(e));

            for (Entity entity : nearby) {
                processedEntities.add(entity);

                // Lực hút vầng xoáy kéo mục tiêu về phía người chơi
                Vec3 pullVec = start.subtract(entity.position()).normalize().scale(1.2D);
                entity.setDeltaMovement(pullVec.x, 0.3D, pullVec.z);
                entity.hasImpulse = true;

                if (entity instanceof LivingEntity living && entity.isAlive()) {
                    // Sinh vật dưới 50% máu -> Bị Beelzebuth nuốt chửng mất tích hoàn toàn!
                    if (living.getHealth() <= living.getMaxHealth() * 0.5F) {
                        level.sendParticles(ParticleTypes.FLASH, living.getX(), living.getY() + 1.0D, living.getZ(), 1, 0, 0, 0, 0);
                        level.sendParticles(ParticleTypes.DRAGON_BREATH, living.getX(), living.getY() + 1.0D, living.getZ(), 30, 0.5D, 0.8D, 0.5D, 0.1D);

                        living.discard(); // Nuốt chửng không rớt đồ
                        devouredEntities++;
                    }
                } else if (entity instanceof ItemEntity itemEntity) {
                    // Hấp thụ item rớt dưới đất
                    itemEntity.discard();
                    devouredItems++;
                } else if (entity instanceof PrimedTnt tnt) {
                    // Nuốt chửng khối TNT đang nổ
                    tnt.discard();
                    devouredExplosives++;
                } else if (entity instanceof Creeper creeper) {
                    // Nuốt chửng Creeper đang chuẩn bị nổ
                    if (creeper.isIgnited() || creeper.getSwellDir() > 0) {
                        creeper.discard();
                        devouredExplosives++;
                    }
                }
            }
        }

        // 3. Nuốt chửng Căn Nhà Dân Làng (Nuốt toàn bộ khối kiến trúc kín)
        BlockPos targetBlockPos = player.blockPosition().relative(player.getDirection(), 4);
        if (isVillagerHouseStructure(level, targetBlockPos)) {
            devouredBlocks = devourHouseBlocks(level, targetBlockPos);
        }

        player.displayClientMessage(
            Component.literal("§d§l[BẠO THỰC VƯƠNG BEELZEBUTH] §fĐã nuốt chửng " + devouredEntities + " sinh vật (<50% HP), "
                    + devouredItems + " vật phẩm, " + devouredExplosives + " đạn nổ và " + devouredBlocks + " khối nhà dân! 🌀"),
            true
        );

        player.getCooldowns().addCooldown(com.minhphuc.weapons.init.ModItems.DEMON_LORD_SEED.get(), 30); // 1.5s cooldown
    }

    /**
     * Kỹ Năng Tối Thượng: Bạo Thực Vương Beelzebuth - Phân nhánh 2: Hủ Hóa & Bạo Liệt (Corrosion & Disintegration)
     * Phóng thích chùm hắc hỏa rồng tím ăn mòn cực đại, phân rã và thiêu hủy mọi sinh vật phía trước.
     */
    public static void executeCorrosion(ServerLevel level, ServerPlayer player) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 2.0F, 0.7F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 1.8F, 0.6F);

        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 20.0D;

        Set<LivingEntity> hitEntities = new HashSet<>();

        for (double d = 1.0D; d <= maxDist; d += 0.5D) {
            Vec3 centerPos = eyePos.add(look.scale(d));
            double spread = 0.8D + (d * 0.15D);

            for (int i = 0; i < 6; i++) {
                double ox = (level.random.nextDouble() - 0.5D) * spread * 2;
                double oy = (level.random.nextDouble() - 0.5D) * spread * 2;
                double oz = (level.random.nextDouble() - 0.5D) * spread * 2;

                level.sendParticles(ParticleTypes.DRAGON_BREATH, centerPos.x + ox, centerPos.y + oy, centerPos.z + oz, 2, 0.05D, 0.05D, 0.05D, 0.02D);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, centerPos.x + ox, centerPos.y + oy, centerPos.z + oz, 1, 0.05D, 0.05D, 0.05D, 0.03D);
                level.sendParticles(ParticleTypes.WITCH, centerPos.x + ox, centerPos.y + oy, centerPos.z + oz, 1, 0.05D, 0.05D, 0.05D, 0.01D);
            }

            AABB hitBox = new AABB(
                centerPos.x - spread, centerPos.y - spread, centerPos.z - spread,
                centerPos.x + spread, centerPos.y + spread, centerPos.z + spread
            );

            List<LivingEntity> victims = level.getEntitiesOfClass(LivingEntity.class, hitBox, e -> e != player && e.isAlive() && !hitEntities.contains(e));
            for (LivingEntity victim : victims) {
                hitEntities.add(victim);
                victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WITHER, 200, 4));
                victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
                victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 3));

                // Sát thương ăn mòn cực đại
                victim.hurt(level.damageSources().magic(), 120.0F);

                level.sendParticles(ParticleTypes.SOUL, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 10, 0.3D, 0.5D, 0.3D, 0.05D);
                level.sendParticles(ParticleTypes.DRAGON_BREATH, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 15, 0.4D, 0.4D, 0.4D, 0.08D);
            }
        }

        player.displayClientMessage(
            Component.literal("§c§l[BẠO THỰC VƯƠNG - HỦ HÓA] §fĐã phóng thích luồng hắc hỏa rồng tím ăn mòn và phân hủy " + hitEntities.size() + " sinh vật phía trước! ☠"),
            true
        );

        player.getCooldowns().addCooldown(com.minhphuc.weapons.init.ModItems.DEMON_LORD_SEED.get(), 30); // 1.5s cooldown
    }

    /**
     * Kiểm tra có phải căn nhà kín 4 bức tường hay không
     */
    private static boolean isVillagerHouseStructure(ServerLevel level, BlockPos center) {
        int solidCount = 0;
        int radius = 4;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos p1 = center.offset(x, 0, z);
                BlockPos p2 = center.offset(x, 2, z);
                if (!level.getBlockState(p1).isAir()) solidCount++;
                if (!level.getBlockState(p2).isAir()) solidCount++;
            }
        }
        return solidCount >= 20; // Có kiến trúc tường khối đặc xung quanh
    }

    /**
     * Hấp thụ toàn bộ khối của căn nhà dân làng thành hư vô
     */
    private static int devourHouseBlocks(ServerLevel level, BlockPos center) {
        int radius = 5;
        int destroyed = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos p = center.offset(x, y, z);
                    BlockState state = level.getBlockState(p);

                    if (!state.isAir() && !state.is(Blocks.BEDROCK) && !state.is(Blocks.BARRIER)) {
                        level.removeBlock(p, false); // Xóa khối không rớt item
                        destroyed++;

                        if (level.random.nextInt(5) == 0) {
                            level.sendParticles(ParticleTypes.DRAGON_BREATH, p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, 2, 0.1D, 0.1D, 0.1D, 0.02D);
                        }
                    }
                }
            }
        }

        level.playSound(null, center.getX(), center.getY(), center.getZ(),
                SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 1.5F, 0.8F);

        return destroyed;
    }
}
