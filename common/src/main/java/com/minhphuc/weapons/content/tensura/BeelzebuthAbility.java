package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BeelzebuthAbility {

    public static class ActiveBeelzebuthDragon {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 startPos;
        public final Vec3 lookVec;
        public final Display.ItemDisplay displayEntity;
        public int ticksRemaining;
        public final int totalTicks;

        public ActiveBeelzebuthDragon(ServerLevel level, ServerPlayer caster, Vec3 startPos, Vec3 lookVec, Display.ItemDisplay displayEntity, int durationTicks) {
            this.level = level;
            this.caster = caster;
            this.startPos = startPos;
            this.lookVec = lookVec;
            this.displayEntity = displayEntity;
            this.ticksRemaining = durationTicks;
            this.totalTicks = durationTicks;
        }
    }

    public static final List<ActiveBeelzebuthDragon> ACTIVE_DRAGONS = new ArrayList<>();

    public static void executeBeelzebuth(ServerLevel level, ServerPlayer player) {
        // Âm thanh Đầu Rồng Hư Không Bạo Thực Vương gầm thét vồ mồi
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 2.5F, 0.6F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.0F, 0.7F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 0.5F);

        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getLookAngle();
        double maxDist = 14.0D; // Tối ưu tầm vươn 14m (gọn trong 1 chunk), triệt tiêu hoàn toàn lag

        // Triệu hồi Đầu Rồng Hư Không Bạo Thực Vương (Display Entity 0% lag, Billboard CENTER)
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            Vec3 spawnPos = start.add(look.scale(2.0D));
            display.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, player.getYRot(), player.getXRot());
            ItemDisplayAccessor itemDisplayAcc = (ItemDisplayAccessor) display;
            DisplayAccessor displayAcc = (DisplayAccessor) display;

            itemDisplayAcc.weapons$setItemStack(new ItemStack(ModItems.BEELZEBUTH_DRAGON_MAW.get()));
            itemDisplayAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            displayAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.CENTER);
            display.setGlowingTag(true);
            displayAcc.weapons$setGlowColorOverride(0x9900FF); // Hào quang tím đen ma vương
            displayAcc.weapons$setViewRange(2.0F);

            displayAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    null,
                    new Vector3f(4.0F, 4.0F, 0.01F),
                    null
            ));

            level.addFreshEntity(display);
            ACTIVE_DRAGONS.add(new ActiveBeelzebuthDragon(level, player, start, look, display, 20)); // 1.0 giây
        }

        int devouredEntities = 0;
        int devouredItems = 0;
        int devouredExplosives = 0;
        int devouredBlocks = 0;

        Set<Entity> processedEntities = new HashSet<>();

        // 1. Tạo Chùm Xoáy Rồng Hư Không Tím (Tối ưu step 2.0m, chỉ ~20 hạt, 0% lag)
        for (double d = 1.0D; d <= maxDist; d += 2.0D) {
            Vec3 centerPos = start.add(look.scale(d));
            double coneRadius = 0.5D + (d * 0.18D);

            for (int i = 0; i < 360; i += 120) {
                double rad = Math.toRadians(i + (d * 20.0D));
                double ox = Math.cos(rad) * coneRadius;
                double oy = Math.sin(rad) * coneRadius;

                Vec3 particlePos = centerPos.add(ox, oy, 0);
                level.sendParticles(ParticleTypes.DRAGON_BREATH, particlePos.x, particlePos.y, particlePos.z, 1, 0.02D, 0.02D, 0.02D, 0.01D);

                if (i == 0) {
                    level.sendParticles(ParticleTypes.PORTAL, particlePos.x, particlePos.y, particlePos.z, 1, 0.01D, 0.01D, 0.01D, 0.01D);
                }
            }

            if ((int) d % 6 == 0) {
                level.sendParticles(ParticleTypes.FLASH, centerPos.x, centerPos.y, centerPos.z, 1, 0, 0, 0, 0);
            }
        }

        // 2. Kéo & Nuốt Chửng Sinh Vật (1 truy vấn AABB duy nhất)
        Vec3 end = start.add(look.scale(maxDist));
        double maxConeRadius = 0.5D + (maxDist * 0.18D);
        AABB coneBox = new AABB(start, end).inflate(maxConeRadius + 0.5D);
        List<Entity> nearby = level.getEntities((Entity) null, coneBox, e -> e != player && e.isAlive());

        for (Entity entity : nearby) {
            Vec3 entityPos = entity.position().add(0, entity.getBbHeight() * 0.5D, 0);
            Vec3 toEntity = entityPos.subtract(start);
            double distAlongLook = toEntity.dot(look);

            if (distAlongLook >= 0 && distAlongLook <= maxDist) {
                double perpDistSq = toEntity.lengthSqr() - (distAlongLook * distAlongLook);
                double coneRadiusAtDist = 0.7D + (distAlongLook * 0.22D);

                if (perpDistSq <= coneRadiusAtDist * coneRadiusAtDist) {
                    // Lực hút vầng xoáy kéo mục tiêu về phía người chơi
                    Vec3 pullVec = start.subtract(entity.position()).normalize().scale(1.1D);
                    entity.setDeltaMovement(pullVec.x, 0.25D, pullVec.z);
                    entity.hasImpulse = true;

                    if (entity instanceof LivingEntity living) {
                        // Sinh vật dưới 50% máu -> Bị Beelzebuth nuốt chửng mất tích hoàn toàn!
                        if (living.getHealth() <= living.getMaxHealth() * 0.5F) {
                            level.sendParticles(ParticleTypes.FLASH, living.getX(), living.getY() + 1.0D, living.getZ(), 1, 0, 0, 0, 0);
                            level.sendParticles(ParticleTypes.DRAGON_BREATH, living.getX(), living.getY() + 1.0D, living.getZ(), 8, 0.2D, 0.3D, 0.2D, 0.03D);

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
        }

        // 3. Nuốt chửng Căn Nhà Dân Làng (Nuốt toàn bộ khối kiến trúc kín, tối ưu tầm 3 blocks)
        BlockPos targetBlockPos = player.blockPosition().relative(player.getDirection(), 3);
        if (isVillagerHouseStructure(level, targetBlockPos)) {
            devouredBlocks = devourHouseBlocks(level, targetBlockPos);
        }

        player.displayClientMessage(
            Component.literal("§d§l[BẠO THỰC VƯƠNG BEELZEBUTH] §fBáo cáo. Đã nuốt chửng " + devouredEntities + " cá thể (<50% HP), "
                    + devouredItems + " vật phẩm, " + devouredExplosives + " đạn nổ và " + devouredBlocks + " khối nhà dân! 🌀"),
            true
        );

        player.getCooldowns().addCooldown(com.minhphuc.weapons.init.ModItems.DEMON_LORD_SEED.get(), 30); // 1.5s cooldown
    }

    /**
     * Cập nhật Đầu Rồng Hư Không Bạo Thực Vương đang lao về phía trước và nuốt chửng vạn vật
     */
    public static void tickDragons(ServerLevel serverLevel) {
        if (ACTIVE_DRAGONS.isEmpty()) return;

        Iterator<ActiveBeelzebuthDragon> it = ACTIVE_DRAGONS.iterator();
        while (it.hasNext()) {
            ActiveBeelzebuthDragon d = it.next();
            if (d.level != serverLevel) continue;

            d.ticksRemaining--;
            int elapsed = d.totalTicks - d.ticksRemaining;

            // Đầu rồng lao vút về phía trước theo hướng nhìn (2m -> 11m)
            double distance = 2.0D + ((double) elapsed / d.totalTicks) * 9.0D;
            Vec3 currentPos = d.startPos.add(d.lookVec.scale(distance));

            // Kích thước miệng rồng nở to dần (4m -> 8.5m)
            float scale = 4.0F + ((float) elapsed / d.totalTicks) * 4.5F;
            if (d.ticksRemaining < 4) {
                scale = (d.ticksRemaining / 4.0F) * 8.5F; // Cắn sập miệng lại biến mất
            }

            if (d.displayEntity != null && d.displayEntity.isAlive()) {
                d.displayEntity.setPos(currentPos.x, currentPos.y, currentPos.z);

                ((DisplayAccessor) d.displayEntity).weapons$setTransformation(new Transformation(
                        new Vector3f(0.0F, 0.0F, 0.0F),
                        null,
                        new Vector3f(scale, scale, 0.01F),
                        null
                ));
            }

            // Hạt khói đen tối ưu nhẹ nhàng
            d.level.sendParticles(ParticleTypes.DRAGON_BREATH, currentPos.x, currentPos.y, currentPos.z, 1, 0.3D, 0.3D, 0.3D, 0.02D);
            if (elapsed % 6 == 0) {
                d.level.sendParticles(ParticleTypes.PORTAL, currentPos.x, currentPos.y, currentPos.z, 2, 0.3D, 0.3D, 0.3D, 0.03D);
            }

            // Lực hút kéo vào miệng rồng (kiểm tra mỗi 4 ticks, bán kính 3.5m)
            if (d.ticksRemaining % 4 == 0) {
                AABB pullBox = new AABB(currentPos.x - 3.5D, currentPos.y - 2.5D, currentPos.z - 3.5D,
                        currentPos.x + 3.5D, currentPos.y + 3.0D, currentPos.z + 3.5D);
                List<Entity> pullTargets = d.level.getEntities((Entity) null, pullBox, e -> e != d.caster && e.isAlive());
                for (Entity e : pullTargets) {
                    Vec3 pull = currentPos.subtract(e.position()).normalize().scale(0.7D);
                    e.setDeltaMovement(pull.x, pull.y * 0.4D + 0.1D, pull.z);
                    e.hasImpulse = true;
                }
            }

            // Kết thúc cú táp: Âm thanh chấn động hư không
            if (d.ticksRemaining <= 0) {
                d.level.sendParticles(ParticleTypes.FLASH, currentPos.x, currentPos.y, currentPos.z, 1, 0, 0, 0, 0);
                d.level.playSound(null, currentPos.x, currentPos.y, currentPos.z,
                        SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 0.6F);

                if (d.displayEntity != null && d.displayEntity.isAlive()) {
                    d.displayEntity.discard();
                }
                it.remove();
            }
        }
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
        double maxDist = 14.0D; // Giảm từ 20m xuống 14m để cực kỳ nhẹ và tập trung trong 1 chunk

        Set<LivingEntity> hitEntities = new HashSet<>();

        // 1. Quét sát thương toàn bộ sinh vật nằm trong luồng hắc hỏa (1 query duy nhất)
        Vec3 endPos = eyePos.add(look.scale(maxDist));
        AABB beamBox = new AABB(eyePos, endPos).inflate(2.5D);
        List<LivingEntity> potentialVictims = level.getEntitiesOfClass(LivingEntity.class, beamBox, e -> e != player && e.isAlive());

        for (LivingEntity victim : potentialVictims) {
            Vec3 toVictim = victim.position().add(0, victim.getBbHeight() * 0.5D, 0).subtract(eyePos);
            double distAlongLook = toVictim.dot(look);

            if (distAlongLook >= 0 && distAlongLook <= maxDist) {
                double perpDistSq = toVictim.lengthSqr() - (distAlongLook * distAlongLook);
                double spreadAtDist = 0.7D + (distAlongLook * 0.15D);

                if (perpDistSq <= spreadAtDist * spreadAtDist) {
                    hitEntities.add(victim);
                    victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WITHER, 200, 4));
                    victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
                    victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 3));

                    // Sát thương ăn mòn cực đại
                    victim.hurt(level.damageSources().magic(), 120.0F);

                    level.sendParticles(ParticleTypes.SOUL, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 3, 0.2D, 0.4D, 0.2D, 0.05D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, victim.getX(), victim.getY() + 1.0D, victim.getZ(), 4, 0.3D, 0.3D, 0.3D, 0.05D);
                }
            }
        }

        // 2. Hiệu ứng chùm hắc hỏa rực rỡ mượt mà (step = 2.0m, chỉ ~14 hạt, 0% lag)
        for (double d = 1.0D; d <= maxDist; d += 2.0D) {
            Vec3 centerPos = eyePos.add(look.scale(d));
            double spread = 0.6D + (d * 0.12D);

            double ox = (level.random.nextDouble() - 0.5D) * spread;
            double oy = (level.random.nextDouble() - 0.5D) * spread;
            double oz = (level.random.nextDouble() - 0.5D) * spread;

            level.sendParticles(ParticleTypes.DRAGON_BREATH, centerPos.x + ox, centerPos.y + oy, centerPos.z + oz, 1, 0.02D, 0.02D, 0.02D, 0.02D);
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, centerPos.x + ox, centerPos.y + oy, centerPos.z + oz, 1, 0.02D, 0.02D, 0.02D, 0.02D);
        }

        player.displayClientMessage(
            Component.literal("§d§l[BẠO THỰC VƯƠNG] §fHãy ăn sạch mọi thứ Beezelbuth!"),
            true
        );

        player.getCooldowns().addCooldown(com.minhphuc.weapons.init.ModItems.DEMON_LORD_SEED.get(), 30); // 1.5s cooldown
    }

    /**
     * Kiểm tra có phải căn nhà kín 4 bức tường hay không (Tối ưu quét bán kính 3)
     */
    private static boolean isVillagerHouseStructure(ServerLevel level, BlockPos center) {
        int solidCount = 0;
        int radius = 3;

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos p1 = center.offset(x, 0, z);
                BlockPos p2 = center.offset(x, 2, z);
                if (!level.getBlockState(p1).isAir()) solidCount++;
                if (!level.getBlockState(p2).isAir()) solidCount++;
            }
        }
        return solidCount >= 14; // Có kiến trúc tường khối đặc xung quanh
    }

    /**
     * Hấp thụ toàn bộ khối của căn nhà dân làng thành hư vô (Tối ưu bán kính 2: chỉ quét 100 khối, 0% drop FPS)
     */
    private static int devourHouseBlocks(ServerLevel level, BlockPos center) {
        int radius = 2;
        int destroyed = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos p = center.offset(x, y, z);
                    BlockState state = level.getBlockState(p);

                    if (!state.isAir() && !state.is(Blocks.BEDROCK) && !state.is(Blocks.BARRIER)) {
                        level.setBlock(p, Blocks.AIR.defaultBlockState(), 2); // Xóa khối nhanh, không kích hoạt chuỗi physics updates
                        destroyed++;

                        if (level.random.nextInt(8) == 0) {
                            level.sendParticles(ParticleTypes.DRAGON_BREATH, p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, 1, 0.05D, 0.05D, 0.05D, 0.01D);
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
