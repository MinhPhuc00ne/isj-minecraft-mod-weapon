package com.minhphuc.weapons.content.divine;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Random;

/**
 * Hệ thống hiệu ứng hình ảnh (VFX) và âm thanh (SFX) cho đòn:
 * HẮC THIỂM (Black Flash / Kokusen - Jujutsu Kaisen) & HAKI BÁ VƯƠNG CẤP CAO (ACoC - One Piece)
 * được tích hợp độc quyền vào chiêu Thần Tỵ (Divine Departure) của Nguyệt Quang Thần Tế Kiếm.
 */
public class BlackFlashVFX {

    // Màu sắc hạt bụi chú lực:
    // Lõi đen kịt hư không tuyệt đối
    private static final DustParticleOptions BLACK_CORE_PARTICLE =
            new DustParticleOptions(new Vector3f(0.01F, 0.0F, 0.02F), 1.7F);
    // Viền hào quang đỏ neon rực sáng
    private static final DustParticleOptions CRIMSON_GLOW_PARTICLE =
            new DustParticleOptions(new Vector3f(1.0F, 0.03F, 0.12F), 1.5F);
    // Lớp viền đỏ thẫm đẫm máu
    private static final DustParticleOptions DARK_RED_PARTICLE =
            new DustParticleOptions(new Vector3f(0.65F, 0.0F, 0.04F), 1.3F);

    private static final Random RANDOM = new Random();

    /**
     * Phát tổ hợp âm thanh rung chuyển đất trời của Hắc Thiểm & Haki Bá Vương
     */
    public static void playBlackFlashSounds(ServerLevel level, ServerPlayer player) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        // 1. Tiếng Bass trầm rung chuyển không gian (Warden Sonic Boom cực trầm)
        level.playSound(null, x, y, z,
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 0.62F);

        // 2. Tiếng sấm sét xé toạc không gian (Lightning Thunder rền vang)
        level.playSound(null, x, y, z,
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.2F, 0.85F);

        // 3. Tiếng giật điện từ cao áp (Lightning Impact nổ đanh)
        level.playSound(null, x, y, z,
                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 2.2F, 1.35F);

        // 4. Tiếng sấm chấn động kéo dài (Trident Thunder vang dội)
        level.playSound(null, x, y, z,
                SoundEvents.TRIDENT_THUNDER, SoundSource.PLAYERS, 2.5F, 0.65F);

        // 5. Tiếng vung trảm kích xé gió cực mạnh
        level.playSound(null, x, y, z,
                SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.5F, 0.55F);

        // 6. Tiếng nổ chân không
        level.playSound(null, x, y, z,
                SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.8F, 0.8F);
    }

    /**
     * Sinh toàn bộ chuỗi tia sét Hắc Thiểm Đỏ - Đen dọc theo quỹ đạo chém
     *
     * @param level ServerLevel
     * @param player Người thi triển
     * @param eyePos Điểm xuất phát (mắt/vũ khí)
     * @param look Vector hướng nhìn
     * @param maxDist Chiều dài tầm chém (mặc định ~10-12 blocks)
     */
    public static void spawnBlackFlashSlashVFX(ServerLevel level, ServerPlayer player, Vec3 eyePos, Vec3 look, double maxDist) {
        // Vector vuông góc ngang tạo mặt phẳng quạt kiếm khí
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();
        Vec3 up = look.cross(right).normalize();

        // 1. Chớp sáng và quét kiếm khí ngay tại vị trí vung kiếm
        Vec3 swingOrigin = eyePos.add(look.scale(1.2D));
        level.sendParticles(ParticleTypes.FLASH, swingOrigin.x, swingOrigin.y, swingOrigin.z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.SWEEP_ATTACK, swingOrigin.x, swingOrigin.y, swingOrigin.z, 2, 0.2D, 0.2D, 0.2D, 0);

        // 2. Tạo quạt lưỡi liềm kiếm khí đỏ - đen và các tia sét Hắc Thiểm chính
        int arcSteps = 9; // Các tia sét chính phóng ra theo hình nan quạt
        for (int i = 0; i < arcSteps; i++) {
            double angleRatio = (double) i / (arcSteps - 1); // 0.0 đến 1.0
            double lateralOffset = (angleRatio - 0.5D) * 4.5D; // Độ xòe quạt kiếm khí
            double verticalOffset = Math.sin(angleRatio * Math.PI) * 0.8D - 0.4D;

            Vec3 rayDir = look.add(right.scale(lateralOffset * 0.25D)).add(up.scale(verticalOffset * 0.25D)).normalize();
            Vec3 endPos = eyePos.add(rayDir.scale(maxDist));

            // Sinh tia sét ziczac chính
            drawJaggedLightning(level, eyePos.add(look.scale(0.8D)), endPos, 0.45D, 0.35D, true);
        }

        // 3. Sinh các nhánh sét phụ (Branching Arcs) giật toé ra không gian xung quanh
        int branchCount = 6;
        for (int b = 0; b < branchCount; b++) {
            double startDist = 2.0D + RANDOM.nextDouble() * (maxDist - 3.0D);
            Vec3 branchOrigin = eyePos.add(look.scale(startDist))
                    .add(right.scale((RANDOM.nextDouble() - 0.5D) * 2.5D))
                    .add(up.scale((RANDOM.nextDouble() - 0.5D) * 1.5D));

            // Hướng nhánh sét toé ra hai bên hoặc cắm xuống đất
            Vec3 branchDir = right.scale((RANDOM.nextDouble() - 0.5D) * 2.0D)
                    .add(up.scale((RANDOM.nextDouble() - 0.7D) * 1.8D))
                    .add(look.scale(0.4D))
                    .normalize();

            double branchLength = 2.5D + RANDOM.nextDouble() * 2.5D;
            Vec3 branchEnd = branchOrigin.add(branchDir.scale(branchLength));

            drawJaggedLightning(level, branchOrigin, branchEnd, 0.35D, 0.25D, false);
        }

        // 4. Sóng xung kích chấn động không khí (Sonic Boom Rings dọc theo đường chém)
        for (double d = 2.5D; d <= maxDist; d += 3.5D) {
            Vec3 shockPos = eyePos.add(look.scale(d));
            level.sendParticles(ParticleTypes.SONIC_BOOM, shockPos.x, shockPos.y, shockPos.z, 1, 0, 0, 0, 0);
            level.sendParticles(ParticleTypes.CRIMSON_SPORE, shockPos.x, shockPos.y, shockPos.z, 6, 0.4D, 0.4D, 0.4D, 0.05D);
            level.sendParticles(ParticleTypes.SQUID_INK, shockPos.x, shockPos.y, shockPos.z, 3, 0.3D, 0.3D, 0.3D, 0.02D);
        }
    }

    /**
     * Hiệu ứng nổ bùng tia sét Hắc Thiểm tại mục tiêu bị đánh trúng (Impact Burst)
     */
    public static void spawnTargetImpactVFX(ServerLevel level, LivingEntity target) {
        double tx = target.getX();
        double ty = target.getY() + (target.getBbHeight() * 0.5D);
        double tz = target.getZ();
        Vec3 center = new Vec3(tx, ty, tz);

        // Chớp sáng & Sóng xung kích nén không khí tại tâm
        level.sendParticles(ParticleTypes.FLASH, tx, ty, tz, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.SONIC_BOOM, tx, ty, tz, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.EXPLOSION, tx, ty, tz, 2, 0.2D, 0.2D, 0.2D, 0);

        // Sinh 8 tia sét Hắc Thiểm bắn toé hình cầu ra mọi hướng từ mục tiêu
        int needleCount = 8;
        for (int i = 0; i < needleCount; i++) {
            double theta = RANDOM.nextDouble() * 2.0D * Math.PI;
            double phi = (RANDOM.nextDouble() - 0.5D) * Math.PI;
            double len = 2.0D + RANDOM.nextDouble() * 2.0D;

            double dx = Math.cos(phi) * Math.cos(theta);
            double dy = Math.sin(phi);
            double dz = Math.cos(phi) * Math.sin(theta);

            Vec3 needleEnd = center.add(new Vec3(dx, dy, dz).scale(len));
            drawJaggedLightning(level, center, needleEnd, 0.3D, 0.2D, false);
        }

        // Khói mực đen chú lực bốc lên
        level.sendParticles(ParticleTypes.SQUID_INK, tx, ty, tz, 8, 0.4D, 0.5D, 0.4D, 0.05D);
        level.sendParticles(ParticleTypes.CRIMSON_SPORE, tx, ty, tz, 12, 0.5D, 0.5D, 0.5D, 0.08D);

        // Âm thanh va chạm đanh thép
        level.playSound(null, tx, ty, tz,
                SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.8F, 1.8F);
        level.playSound(null, tx, ty, tz,
                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 1.8F, 1.6F);
    }

    /**
     * Thuật toán vẽ tia sét ziczac đỏ - đen thủ tục từ điểm start đến end
     *
     * @param level ServerLevel
     * @param start Điểm bắt đầu
     * @param end Điểm kết thúc
     * @param segmentLength Chiều dài mỗi đốt ziczac
     * @param jitter Bán kính lệch ziczac ngẫu nhiên
     * @param isMain Có phát thêm khói đen và chớp hào quang hay không
     */
    private static void drawJaggedLightning(ServerLevel level, Vec3 start, Vec3 end, double segmentLength, double jitter, boolean isMain) {
        Vec3 fullVec = end.subtract(start);
        double totalDist = fullVec.length();
        if (totalDist < 0.1D) return;

        Vec3 dir = fullVec.normalize();
        // Tìm 2 trục vuông góc với dir để tạo độ lệch ziczac 3D
        Vec3 perp1 = Math.abs(dir.y) < 0.9D ? dir.cross(new Vec3(0, 1, 0)).normalize() : dir.cross(new Vec3(1, 0, 0)).normalize();
        Vec3 perp2 = dir.cross(perp1).normalize();

        int segments = Math.max(1, (int) (totalDist / segmentLength));
        Vec3 currentPoint = start;

        for (int s = 1; s <= segments; s++) {
            double fraction = (double) s / segments;
            Vec3 targetPoint = start.add(fullVec.scale(fraction));

            // Nếu chưa đến điểm cuối, tạo độ lệch ngẫu nhiên ziczac
            if (s < segments) {
                double offset1 = (RANDOM.nextDouble() - 0.5D) * 2.0D * jitter;
                double offset2 = (RANDOM.nextDouble() - 0.5D) * 2.0D * jitter;
                targetPoint = targetPoint.add(perp1.scale(offset1)).add(perp2.scale(offset2));
            }

            // Vẽ một đường hạt liên tục giữa currentPoint và targetPoint
            Vec3 segmentVec = targetPoint.subtract(currentPoint);
            double segDist = segmentVec.length();
            int subParticles = Math.max(2, (int) (segDist / 0.18D)); // Mật độ hạt dày đặc liền mạch

            for (int p = 0; p <= subParticles; p++) {
                double subFrac = (double) p / subParticles;
                Vec3 pPos = currentPoint.add(segmentVec.scale(subFrac));

                // 1. Lõi đen kịt hư không ở chính tâm
                level.sendParticles(BLACK_CORE_PARTICLE, pPos.x, pPos.y, pPos.z, 1, 0.01D, 0.01D, 0.01D, 0);

                // 2. Viền đỏ neon bọc ngoài
                double auraOffset = 0.035D;
                level.sendParticles(CRIMSON_GLOW_PARTICLE,
                        pPos.x + (RANDOM.nextDouble() - 0.5D) * auraOffset,
                        pPos.y + (RANDOM.nextDouble() - 0.5D) * auraOffset,
                        pPos.z + (RANDOM.nextDouble() - 0.5D) * auraOffset,
                        1, 0.01D, 0.01D, 0.01D, 0);

                // 3. Đỏ thẫm lan tỏa nhẹ
                if (p % 2 == 0) {
                    level.sendParticles(DARK_RED_PARTICLE,
                            pPos.x + (RANDOM.nextDouble() - 0.5D) * auraOffset * 1.8D,
                            pPos.y + (RANDOM.nextDouble() - 0.5D) * auraOffset * 1.8D,
                            pPos.z + (RANDOM.nextDouble() - 0.5D) * auraOffset * 1.8D,
                            1, 0.01D, 0.01D, 0.01D, 0);
                }

                // 4. Khói chú lực đen nếu là tia sét chính
                if (isMain && RANDOM.nextInt(6) == 0) {
                    level.sendParticles(ParticleTypes.SQUID_INK, pPos.x, pPos.y, pPos.z, 1, 0.02D, 0.02D, 0.02D, 0.01D);
                }
            }

            currentPoint = targetPoint;
        }
    }
}
