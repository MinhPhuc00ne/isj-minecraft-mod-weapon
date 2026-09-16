package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.content.divine.DivineArmorItem;
import com.minhphuc.weapons.data.EntityDataHelper;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Kỹ Năng Tối Thượng Thần Cấp: Long Tinh Bộc Viêm Bá - Dragon Nova (Drago-Nova / 竜星爆炎覇)
 * Tuyệt kỹ từng được Ma Vương Milim Nava dùng để diệt trừ Tinh Long (Chaos Dragon):
 * - Giai đoạn 1 (0 -> 29 ticks): Tụ Ma Tố & Hội Tụ Hàng Vạn Linh Tử (Spiritrons) từ không gian bay vào ngực,
 *   Địa Ma Trận phát quang tím thẫm (0x9400D3) xoay phóng đại dưới chân (không có vòng trước mặt).
 * - Giai đoạn 2 (30 -> 54 ticks): Phóng đại pháo cực quang xuyên không gian 55m,
 *   PHÁ HỦY VÀ BỐC HƠI MỌI KHỐI BLOCK TRÊN ĐƯỜNG ĐI, hút quái vào tâm, 100 sát thương ma pháp mỗi 2 ticks!
 * - Giai đoạn 3 (55 -> 75 ticks): Đại vụ nổ Siêu Tân Tinh: Cột năng lượng chọc trời 45m, tạo hố bom crater 5m,
 *   8 vòng sóng xung kích 26m, 3,000 sát thương quái thường, rút 88% HP Boss kèm hất tung 18m!
 */
public class DragonNovaAbility {

    public static class ActiveDragonNova {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public int currentTick = 0;
        public final int totalTicks = 75;
        public Vec3 lastHitPos = null;

        // Ma Pháp Trận Tím 3D Dưới Chân (Duy Nhất)
        public Display.ItemDisplay groundCircle = null;

        public ActiveDragonNova(ServerLevel level, ServerPlayer caster) {
            this.level = level;
            this.caster = caster;
        }

        public void cleanupDisplays() {
            if (groundCircle != null && groundCircle.isAlive()) {
                groundCircle.discard();
                groundCircle = null;
            }
        }
    }

    public static final List<ActiveDragonNova> ACTIVE_NOVAS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    // Hệ thống hạt bụi năng lượng cao cấp
    private static final DustParticleOptions NEON_MAGENTA_DUST = new DustParticleOptions(new Vector3f(1.0F, 0.02F, 0.55F), 2.0F);
    private static final DustParticleOptions DEEP_PURPLE_DUST = new DustParticleOptions(new Vector3f(0.65F, 0.0F, 0.95F), 1.8F);
    private static final DustParticleOptions ELECTRIC_CYAN_DUST = new DustParticleOptions(new Vector3f(0.0F, 0.95F, 1.0F), 1.8F);
    private static final DustParticleOptions STARLIGHT_WHITE_DUST = new DustParticleOptions(new Vector3f(0.95F, 1.0F, 1.0F), 2.2F);

    /**
     * Kiểm tra điều kiện mở khóa: Chân Ma Vương + Đủ 4 món Giáp Thần Linh
     */
    public static boolean canCast(ServerPlayer player) {
        if (player == null) return false;
        boolean isTrueDemonLord = EntityDataHelper.getCustomData(player).getBoolean("TensuraTrueDemonLord");
        boolean hasFullDivineArmor = DivineArmorItem.isWearingFullSet(player);
        return isTrueDemonLord && hasFullDivineArmor;
    }

    /**
     * Kích hoạt Long Tinh Bộc Viêm Bá
     */
    public static boolean cast(ServerLevel level, ServerPlayer player) {
        if (!canCast(player)) {
            player.displayClientMessage(
                Component.literal("§e§l[GIỌNG NÓI THẾ GIỚI] §cBáo cáo. Yêu cầu Thức Tỉnh Chân Ma Vương và Mặc Đủ 4 Món Giáp Thần Linh để khai mở Long Tinh Bộc Viêm Bá!"),
                true
            );
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.2F, 0.5F);
            return false;
        }

        // Kiểm tra hồi chiêu (45 giây = 900 ticks)
        if (player.getCooldowns().isOnCooldown(ModItems.MOONLIGHT_SWORD.get())
                || player.getCooldowns().isOnCooldown(ModItems.DEMON_LORD_SEED.get())) {
            player.displayClientMessage(
                Component.literal("§c⚠️ Kỹ năng Long Tinh Bộc Viêm Bá đang trong thời gian hồi ma lực!"),
                true
            );
            return false;
        }

        player.getCooldowns().addCooldown(ModItems.MOONLIGHT_SWORD.get(), 900);
        player.getCooldowns().addCooldown(ModItems.DEMON_LORD_SEED.get(), 900);

        ActiveDragonNova nova = new ActiveDragonNova(level, player);

        // 1. Chỉ tạo DUY NHẤT 1 Vòng Tròn Ma Thuật Tím Thẫm dưới chân
        Vec3 groundPos = player.position().add(0, 0.05D, 0);
        nova.groundCircle = createMagicCircleDisplay(level, groundPos, ModItems.BEELZEBUTH_MAGIC_CIRCLE.get(), 3.0F, 0x9400D3);

        ACTIVE_NOVAS.add(nova);

        // 2. Gửi Title chuẩn Kanji & Latin
        if (player.connection != null) {
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§d§l竜星爆炎覇")));
            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§6§l✦ LONG TINH BỘC VIÊM BÁ - DRAGO-NOVA ✦")));
        }

        // 3. Âm thanh mở màn rền vang vũ trụ
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 4.0F, 0.6F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 1.8F);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.5F, 0.7F);

        // 4. Trạng thái bất tử và lơ lửng khi tụ chiêu
        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 32, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 4, false, false));

        return true;
    }

    /**
     * Vòng lặp Server Tick cập nhật hoạt cảnh 3 giai đoạn siêu cấp
     */
    public static void tickDragonNovas(ServerLevel level) {
        if (ACTIVE_NOVAS.isEmpty()) return;

        Iterator<ActiveDragonNova> iterator = ACTIVE_NOVAS.iterator();
        while (iterator.hasNext()) {
            ActiveDragonNova nova = iterator.next();

            if (nova.level != level) continue;

            if (nova.caster == null || !nova.caster.isAlive() || nova.caster.hasDisconnected()) {
                nova.cleanupDisplays();
                iterator.remove();
                continue;
            }

            ServerPlayer caster = nova.caster;
            int tick = nova.currentTick;

            // =========================================================================
            // GIAI ĐOẠN 1: TỤ MA TỐ & HỘI TỤ LINH TỬ (SPIRITRONS) (Ticks 0 -> 29: ~1.5 giây)
            // =========================================================================
            if (tick < 30) {
                float progress = (float) tick / 30.0F;

                // 1. Cập nhật xoay và phóng to Vòng Tròn Ma Thuật Tím Dưới Chân (3.0m -> 9.5m)
                if (nova.groundCircle != null && nova.groundCircle.isAlive()) {
                    Vec3 gPos = caster.position().add(0, 0.05D, 0);
                    nova.groundCircle.moveTo(gPos.x, gPos.y, gPos.z, 0.0F, 0.0F);
                    float groundScale = 3.0F + (progress * 6.5F);
                    Quaternionf gRot = new Quaternionf()
                            .rotateX((float) Math.toRadians(90.0F))
                            .rotateZ((float) Math.toRadians(tick * 7.5F));
                    updateDisplayTransformation(nova.groundCircle, groundScale, gRot);
                }

                // 2. HIỆU ỨNG TẬP HỢP CÁC LINH TỬ (SPIRITRONS GATHERING):
                // Hàng vạn đốm sáng linh tử tinh khiết từ quả cầu 7.0m không gian bay vút vào ngực người chơi
                Vec3 casterChest = caster.position().add(0, 1.2D, 0);
                for (int i = 0; i < 45; i++) {
                    double u = RANDOM.nextDouble();
                    double v = RANDOM.nextDouble();
                    double theta = u * 2.0D * Math.PI;
                    double phi = Math.acos(2.0D * v - 1.0D);
                    double r = 4.0D + RANDOM.nextDouble() * 3.5D;

                    double sx = casterChest.x + r * Math.sin(phi) * Math.cos(theta);
                    double sy = casterChest.y + r * Math.sin(phi) * Math.sin(theta);
                    double sz = casterChest.z + r * Math.cos(phi);

                    Vec3 spawnPos = new Vec3(sx, sy, sz);
                    Vec3 inwardVelocity = casterChest.subtract(spawnPos).normalize().scale(0.85D);

                    // Khi count = 0, dx dy dz là vector vận tốc hạt bay vào tâm
                    if (i % 3 == 0) {
                        level.sendParticles(ParticleTypes.END_ROD, spawnPos.x, spawnPos.y, spawnPos.z, 0,
                                inwardVelocity.x, inwardVelocity.y, inwardVelocity.z, 0.8D);
                    } else if (i % 3 == 1) {
                        level.sendParticles(ParticleTypes.GLOW, spawnPos.x, spawnPos.y, spawnPos.z, 0,
                                inwardVelocity.x, inwardVelocity.y, inwardVelocity.z, 0.8D);
                    } else {
                        level.sendParticles(ELECTRIC_CYAN_DUST, spawnPos.x, spawnPos.y, spawnPos.z, 0,
                                inwardVelocity.x, inwardVelocity.y, inwardVelocity.z, 0.8D);
                    }
                }

                // Luồng lửa ma rồng tím quanh thân
                for (int i = 0; i < 4; i++) {
                    double ang = (tick * 0.3D) + (i * Math.PI / 2.0D);
                    double px = caster.getX() + Math.cos(ang) * 1.5D;
                    double py = caster.getY() + 0.2D + (i * 0.5D);
                    double pz = caster.getZ() + Math.sin(ang) * 1.5D;
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, px, py, pz, 1, 0, 0.05D, 0, 0.01D);
                    level.sendParticles(NEON_MAGENTA_DUST, px, py, pz, 1, 0, 0, 0, 0);
                }

                // Âm thanh nén năng lượng & cộng hưởng linh tử
                if (tick == 14) {
                    level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                            SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 3.5F, 1.3F);
                    level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                            SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 3.0F, 1.2F);
                }

                // 3. Thập Tự Quang Mang (Cross Star Flare) rực sáng cực độ trước khi khai hỏa
                if (tick >= 24) {
                    Vec3 look = caster.getLookAngle();
                    Vec3 focusPos = caster.getEyePosition().add(look.scale(1.8D));
                    Vec3 right = new Vec3(-look.z, 0, look.x).normalize();
                    Vec3 up = look.cross(right).normalize();
                    double flareArmLength = 5.5D;

                    for (double d = -flareArmLength; d <= flareArmLength; d += 0.4D) {
                        Vec3 pHoriz = focusPos.add(right.scale(d));
                        Vec3 pVert = focusPos.add(up.scale(d));
                        level.sendParticles(ParticleTypes.FLASH, pHoriz.x, pHoriz.y, pHoriz.z, 1, 0, 0, 0, 0);
                        level.sendParticles(STARLIGHT_WHITE_DUST, pHoriz.x, pHoriz.y, pHoriz.z, 1, 0, 0, 0, 0);
                        level.sendParticles(ParticleTypes.END_ROD, pVert.x, pVert.y, pVert.z, 1, 0, 0, 0, 0.02D);
                        level.sendParticles(ELECTRIC_CYAN_DUST, pVert.x, pVert.y, pVert.z, 1, 0, 0, 0, 0);
                    }

                    if (tick == 24) {
                        level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                                SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 4.0F, 1.95F);
                        level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.5F, 1.5F);
                    }
                }
            }

            // =========================================================================
            // GIAI ĐOẠN 2: PHÓNG ĐẠI PHÁO 55M & PHÁ HỦY BLOCK TRÊN ĐƯỜNG ĐI (Ticks 30 -> 54)
            // =========================================================================
            else if (tick < 55) {
                if (tick == 30) {
                    nova.cleanupDisplays();
                    level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, caster.getX(), caster.getY() + 1.2D, caster.getZ(), 2, 0.5D, 0.5D, 0.5D, 0);

                    level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                            SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 6.0F, 0.75F);
                    level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                            SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 5.0F, 1.1F);
                    level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                            SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 4.5F, 1.3F);

                    caster.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 50, 0, false, false));
                }

                Vec3 origin = caster.getEyePosition();
                Vec3 look = caster.getLookAngle();
                double maxRange = 55.0D;

                Vec3 rayEnd = origin.add(look.scale(maxRange));
                BlockHitResult blockHit = level.clip(new ClipContext(
                        origin, rayEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster
                ));

                Vec3 hitPos = (blockHit.getType() != HitResult.Type.MISS) ? blockHit.getLocation() : rayEnd;
                nova.lastHitPos = hitPos;
                double beamDist = origin.distanceTo(hitPos);

                Vec3 right = new Vec3(-look.z, 0, look.x).normalize();
                Vec3 up = look.cross(right).normalize();
                double helixRadius = 1.45D;

                // 1. Phá hủy và bốc hơi các khối block trên đường chùm tia đi qua (Bán kính 2.2 blocks)
                for (double d = 1.0D; d < beamDist; d += 1.5D) {
                    Vec3 carvePt = origin.add(look.scale(d));
                    BlockPos centerBp = BlockPos.containing(carvePt);
                    int rBlock = 2;

                    for (int bx = -rBlock; bx <= rBlock; bx++) {
                        for (int by = -rBlock; by <= rBlock; by++) {
                            for (int bz = -rBlock; bz <= rBlock; bz++) {
                                if (bx * bx + by * by + bz * bz <= rBlock * rBlock + 1) {
                                    BlockPos bp = centerBp.offset(bx, by, bz);
                                    BlockState state = level.getBlockState(bp);
                                    // Không phá hủy Bedrock hoặc End Portal
                                    if (!state.isAir() && state.getDestroySpeed(level, bp) >= 0.0F) {
                                        level.destroyBlock(bp, false); // false = không làm rơi item để chống lag
                                    }
                                }
                            }
                        }
                    }
                }

                // 2. Vẽ chùm tia dày đặc siêu cấp
                for (double d = 1.0D; d < beamDist; d += 0.45D) {
                    Vec3 corePt = origin.add(look.scale(d));

                    level.sendParticles(ParticleTypes.END_ROD, corePt.x, corePt.y, corePt.z, 1, 0.05D, 0.05D, 0.05D, 0.01D);
                    level.sendParticles(ELECTRIC_CYAN_DUST, corePt.x, corePt.y, corePt.z, 1, 0, 0, 0, 0);

                    if (((int) (d * 2)) % 7 == 0) {
                        level.sendParticles(ParticleTypes.FLASH, corePt.x, corePt.y, corePt.z, 1, 0, 0, 0, 0);
                    }

                    if (((int) (d * 2)) % 3 == 0) {
                        double a = d * 2.0D;
                        Vec3 auraOff = right.scale(Math.cos(a) * 0.7D).add(up.scale(Math.sin(a) * 0.7D));
                        Vec3 auraPt = corePt.add(auraOff);
                        level.sendParticles(ParticleTypes.GLOW, auraPt.x, auraPt.y, auraPt.z, 1, 0, 0, 0, 0.02D);
                    }

                    double angle = (d * 1.6D) + (tick * 0.85D);
                    Vec3 offset1 = right.scale(Math.cos(angle) * helixRadius).add(up.scale(Math.sin(angle) * helixRadius));
                    Vec3 offset2 = right.scale(-Math.cos(angle) * helixRadius).add(up.scale(-Math.sin(angle) * helixRadius));

                    Vec3 h1 = corePt.add(offset1);
                    Vec3 h2 = corePt.add(offset2);

                    level.sendParticles(ParticleTypes.DRAGON_BREATH, h1.x, h1.y, h1.z, 1, 0, 0, 0, 0.03D);
                    level.sendParticles(NEON_MAGENTA_DUST, h1.x, h1.y, h1.z, 1, 0, 0, 0, 0);
                    level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, h2.x, h2.y, h2.z, 1, 0, 0, 0, 0.03D);
                    level.sendParticles(DEEP_PURPLE_DUST, h2.x, h2.y, h2.z, 1, 0, 0, 0, 0);
                }

                // 3. Vòng sóng Sonic Boom bắn dọc theo chùm tia
                double pulseD = ((tick - 30) * 3.8D) % beamDist;
                Vec3 pulsePos = origin.add(look.scale(pulseD));
                level.sendParticles(ParticleTypes.SONIC_BOOM, pulsePos.x, pulsePos.y, pulsePos.z, 1, 0, 0, 0, 0);

                // 4. Điểm va chạm bắn tung tóe chấn động
                level.sendParticles(ParticleTypes.EXPLOSION, hitPos.x, hitPos.y, hitPos.z, 3, 0.8D, 0.8D, 0.8D, 0.1D);
                level.sendParticles(ParticleTypes.FLASH, hitPos.x, hitPos.y, hitPos.z, 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.LAVA, hitPos.x, hitPos.y, hitPos.z, 4, 0.5D, 0.5D, 0.5D, 0.1D);

                // 5. Lực hút chân không & Sát thương ma pháp cực lớn mỗi 2 ticks (100 Damage/hit)
                if (tick % 2 == 0) {
                    AABB beamBounds = new AABB(origin, hitPos).inflate(4.5D);
                    List<LivingEntity> potentialTargets = level.getEntitiesOfClass(LivingEntity.class, beamBounds,
                            e -> e != caster && e.isAlive());

                    for (LivingEntity target : potentialTargets) {
                        Vec3 targetPos = target.getEyePosition();
                        double perpDistSq = getPerpendicularDistanceSq(origin, hitPos, targetPos);

                        if (perpDistSq <= 16.0D) {
                            Vec3 AB = hitPos.subtract(origin);
                            double t = Mth.clamp(targetPos.subtract(origin).dot(AB) / AB.lengthSqr(), 0.0D, 1.0D);
                            Vec3 beamCenter = origin.add(AB.scale(t));
                            Vec3 pull = beamCenter.subtract(targetPos).normalize().scale(0.75D).add(look.scale(0.8D));
                            target.setDeltaMovement(pull);

                            target.hurt(level.damageSources().playerAttack(caster), 100.0F);
                            target.setRemainingFireTicks(100);

                            level.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY() + 1.0D, target.getZ(), 8, 0.3D, 0.3D, 0.3D, 0.1D);
                        }
                    }
                }
            }

            // =========================================================================
            // GIAI ĐOẠN 3: ĐẠI VỤ NỔ SIÊU TÂN TINH & TẠO HỐ BOM (Tick 55)
            // =========================================================================
            else if (tick == 55) {
                Vec3 hit = nova.lastHitPos;
                if (hit == null) {
                    hit = caster.getEyePosition().add(caster.getLookAngle().scale(35.0D));
                }

                // 1. Phá hủy khối địa hình tạo hố bom bán kính 5 blocks tại tâm nổ
                int craterR = 5;
                BlockPos hitBp = BlockPos.containing(hit);
                for (int bx = -craterR; bx <= craterR; bx++) {
                    for (int by = -craterR; by <= craterR; by++) {
                        for (int bz = -craterR; bz <= craterR; bz++) {
                            if (bx * bx + by * by + bz * bz <= craterR * craterR) {
                                BlockPos bp = hitBp.offset(bx, by, bz);
                                BlockState state = level.getBlockState(bp);
                                if (!state.isAir() && state.getDestroySpeed(level, bp) >= 0.0F) {
                                    level.destroyBlock(bp, false);
                                }
                            }
                        }
                    }
                }

                // 2. Âm thanh bộc phá chấn động kinh hoàng
                level.playSound(null, hit.x, hit.y, hit.z,
                        SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 10.0F, 0.38F);
                level.playSound(null, hit.x, hit.y, hit.z,
                        SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 8.0F, 0.5F);
                level.playSound(null, hit.x, hit.y, hit.z,
                        SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 7.0F, 0.55F);
                level.playSound(null, hit.x, hit.y, hit.z,
                        SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 5.0F, 0.8F);

                // 3. Tâm nổ bộc phát chớp sáng mù lòa
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, hit.x, hit.y, hit.z, 8, 2.0D, 2.0D, 2.0D, 0);
                level.sendParticles(ParticleTypes.FLASH, hit.x, hit.y, hit.z, 4, 1.0D, 1.0D, 1.0D, 0);

                // 4. Cột năng lượng chọc trời 45m (Cosmic Sky Pillar)
                for (double y = 0; y <= 45.0D; y += 1.5D) {
                    level.sendParticles(ParticleTypes.END_ROD, hit.x, hit.y + y, hit.z, 4, 1.0D, 0.4D, 1.0D, 0.06D);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH, hit.x, hit.y + y, hit.z, 5, 1.4D, 0.4D, 1.4D, 0.03D);
                    level.sendParticles(ELECTRIC_CYAN_DUST, hit.x, hit.y + y, hit.z, 3, 1.0D, 0.3D, 1.0D, 0);
                    level.sendParticles(NEON_MAGENTA_DUST, hit.x, hit.y + y, hit.z, 3, 1.2D, 0.3D, 1.2D, 0);
                    if (((int) y) % 6 == 0) {
                        level.sendParticles(ParticleTypes.FLASH, hit.x, hit.y + y, hit.z, 1, 0, 0, 0, 0);
                    }
                }

                // 5. 8 vòng sóng xung kích bán cầu khổng lồ mở rộng từ 3m đến 26m
                double[] shockwaveRadii = {3.0D, 6.0D, 9.0D, 12.0D, 15.0D, 18.0D, 22.0D, 26.0D};
                for (double r : shockwaveRadii) {
                    int ringParticles = 24;
                    for (int step = 0; step < ringParticles; step++) {
                        double ang = step * (Math.PI * 2.0D / ringParticles);
                        double wx = hit.x + Math.cos(ang) * r;
                        double wz = hit.z + Math.sin(ang) * r;

                        level.sendParticles(ParticleTypes.FLASH, wx, hit.y + 0.6D, wz, 1, 0, 0, 0, 0);
                        level.sendParticles(ParticleTypes.GLOW, wx, hit.y + 0.6D, wz, 1, 0, 0.15D, 0, 0.05D);
                        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, wx, hit.y + 0.6D, wz, 1, 0, 0.2D, 0, 0.03D);
                        level.sendParticles(NEON_MAGENTA_DUST, wx, hit.y + 0.6D, wz, 1, 0, 0.1D, 0, 0);
                    }
                }

                // 6. 8 luồng Sonic Boom phóng tỏa ra 8 hướng từ tâm
                for (int dir = 0; dir < 8; dir++) {
                    double ang = dir * (Math.PI * 2.0D / 8.0D);
                    Vec3 boomPos = hit.add(Math.cos(ang) * 6.0D, 1.0D, Math.sin(ang) * 6.0D);
                    level.sendParticles(ParticleTypes.SONIC_BOOM, boomPos.x, boomPos.y, boomPos.z, 1, 0, 0, 0, 0);
                }

                // 7. 12 tia sét ma thuật cắm từ tâm vụ nổ xuống mặt đất xung quanh
                for (int s = 0; s < 12; s++) {
                    double ang = s * (Math.PI * 2.0D / 12.0D);
                    double dist = 8.0D + RANDOM.nextDouble() * 12.0D;
                    Vec3 strikeTarget = hit.add(Math.cos(ang) * dist, 0.0D, Math.sin(ang) * dist);
                    drawJaggedLightning(level, hit.add(0, 8.0D, 0), strikeTarget, 0.5D);
                }

                // 8. Uy Lực Tuyệt Đối (Bán kính 25m: 3,000 sát thương quái thường, rút 88% HP Boss)
                AABB blastArea = new AABB(hit.x - 25.0D, hit.y - 12.0D, hit.z - 25.0D,
                                          hit.x + 25.0D, hit.y + 20.0D, hit.z + 25.0D);
                List<LivingEntity> blastVictims = level.getEntitiesOfClass(LivingEntity.class, blastArea,
                        e -> e != caster && e.isAlive());

                for (LivingEntity victim : blastVictims) {
                    double dist = victim.position().distanceTo(hit);
                    if (dist <= 25.0D) {
                        boolean isBoss = victim instanceof net.minecraft.world.entity.boss.wither.WitherBoss
                                || victim instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                                || victim instanceof net.minecraft.world.entity.monster.warden.Warden
                                || victim instanceof net.minecraft.world.entity.monster.ElderGuardian
                                || victim.getMaxHealth() >= 100.0F;

                        if (isBoss) {
                            float bossDamage = Math.max(500.0F, victim.getHealth() * 0.88F);
                            victim.hurt(level.damageSources().playerAttack(caster), bossDamage);

                            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 3));
                            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 3));
                            victim.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 400, 0));
                            victim.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));

                            victim.setDeltaMovement(new Vec3(0, 1.8D, 0));
                        } else {
                            TensuraEvents.handleMobDeathDrop(caster, victim);
                            victim.hurt(level.damageSources().playerAttack(caster), 3000.0F);
                            if (victim.isAlive()) {
                                victim.discard();
                            }
                        }

                        Vec3 blastDir = victim.position().subtract(hit).normalize().add(0, 0.8D, 0).scale(3.0D);
                        victim.setDeltaMovement(blastDir);
                    }
                }

                caster.displayClientMessage(
                    Component.literal("§d§l[LONG TINH BỘC VIÊM BÁ] §fBáo cáo. Siêu Tân Tinh hủy diệt đã xóa sổ hoàn toàn mục tiêu!"),
                    true
                );
            }

            // =========================================================================
            // DƯ CHẤN TÀN TRO TINH TRẦN (Ticks 56 -> 75)
            // =========================================================================
            else {
                if (nova.lastHitPos != null && tick % 3 == 0) {
                    Vec3 hit = nova.lastHitPos;
                    level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, hit.x, hit.y + 1.0D, hit.z, 2, 1.5D, 0.5D, 1.5D, 0.02D);
                    level.sendParticles(ParticleTypes.GLOW, hit.x, hit.y + 0.8D, hit.z, 3, 1.2D, 0.3D, 1.2D, 0.03D);
                    level.sendParticles(ELECTRIC_CYAN_DUST, hit.x, hit.y + 0.5D, hit.z, 2, 1.0D, 0.2D, 1.0D, 0);
                }
            }

            nova.currentTick++;
            if (nova.currentTick >= nova.totalTicks) {
                nova.cleanupDisplays();
                iterator.remove();
            }
        }
    }

    /**
     * Tạo thực thể ItemDisplay Ma Pháp Trận sắc nét phát quang
     */
    private static Display.ItemDisplay createMagicCircleDisplay(ServerLevel level, Vec3 pos, Item item, float initialScale, int glowColor) {
        Display.ItemDisplay display = EntityType.ITEM_DISPLAY.create(level);
        if (display != null) {
            display.moveTo(pos.x, pos.y, pos.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) display;
            DisplayAccessor dispAcc = (DisplayAccessor) display;

            itemAcc.weapons$setItemStack(new ItemStack(item));
            itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            display.setGlowingTag(true);
            dispAcc.weapons$setGlowColorOverride(glowColor);
            dispAcc.weapons$setViewRange(3.5F);

            Quaternionf rotation = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rotation,
                    new Vector3f(initialScale, initialScale, 0.01F),
                    null
            ));

            level.addFreshEntity(display);
        }
        return display;
    }

    /**
     * Cập nhật kích thước và góc xoay cho Ma Pháp Trận
     */
    private static void updateDisplayTransformation(Display.ItemDisplay display, float scale, Quaternionf rotation) {
        if (display != null && display.isAlive()) {
            ((DisplayAccessor) display).weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rotation,
                    new Vector3f(scale, scale, 0.01F),
                    null
            ));
        }
    }

    /**
     * Sinh tia sét ma thuật ziczac phát sáng giữa 2 điểm
     */
    private static void drawJaggedLightning(ServerLevel level, Vec3 start, Vec3 end, double jitter) {
        int segments = 6;
        Vec3 prev = start;
        Vec3 dir = end.subtract(start);
        double segLen = dir.length() / segments;
        Vec3 segStep = dir.normalize().scale(segLen);

        for (int i = 1; i <= segments; i++) {
            Vec3 target = start.add(segStep.scale(i));
            if (i < segments) {
                double jx = (RANDOM.nextDouble() - 0.5D) * 2.0D * jitter;
                double jy = (RANDOM.nextDouble() - 0.5D) * 2.0D * jitter;
                double jz = (RANDOM.nextDouble() - 0.5D) * 2.0D * jitter;
                target = target.add(jx, jy, jz);
            }

            double subDist = prev.distanceTo(target);
            Vec3 subDir = target.subtract(prev).normalize();
            for (double d = 0; d < subDist; d += 0.35D) {
                Vec3 p = prev.add(subDir.scale(d));
                level.sendParticles(ELECTRIC_CYAN_DUST, p.x, p.y, p.z, 1, 0, 0, 0, 0);
                level.sendParticles(NEON_MAGENTA_DUST, p.x, p.y, p.z, 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 1, 0, 0, 0, 0.01D);
            }
            prev = target;
        }
    }

    /**
     * Tính khoảng cách vuông góc từ điểm P đến đoạn thẳng nối từ A đến B (bình phương)
     */
    private static double getPerpendicularDistanceSq(Vec3 A, Vec3 B, Vec3 P) {
        Vec3 AB = B.subtract(A);
        Vec3 AP = P.subtract(A);
        double lenSq = AB.lengthSqr();
        if (lenSq < 1e-6) return AP.lengthSqr();

        double t = AP.dot(AB) / lenSq;
        t = Mth.clamp(t, 0.0D, 1.0D);
        Vec3 closest = A.add(AB.scale(t));
        return P.distanceToSqr(closest);
    }
}
