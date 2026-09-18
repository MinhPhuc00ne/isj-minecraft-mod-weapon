package com.minhphuc.weapons.content.tensura;

import com.minhphuc.weapons.entity.ModEntities;
import com.minhphuc.weapons.entity.tensura.DemonType;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import com.minhphuc.weapons.init.ModItems;
import com.minhphuc.weapons.mixin.DisplayAccessor;
import com.minhphuc.weapons.mixin.ItemDisplayAccessor;
import com.mojang.math.Transformation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimordialSummonRitual {

    public static class ActiveSummon {
        public final ServerLevel level;
        public final ServerPlayer caster;
        public final Vec3 center;
        public final DemonType demonType;
        public final boolean isWinged;
        public Display.ItemDisplay magicCircle;
        public PrimordialDemonEntity demon;
        public int ticksRemaining;
        public final int totalTicks;

        public ActiveSummon(ServerLevel level, ServerPlayer caster, Vec3 center,
                            DemonType demonType, boolean isWinged, int totalTicks) {
            this.level = level;
            this.caster = caster;
            this.center = center;
            this.demonType = demonType;
            this.isWinged = isWinged;
            this.ticksRemaining = totalTicks;
            this.totalTicks = totalTicks;
        }

        public void cleanup() {
            if (magicCircle != null && magicCircle.isAlive()) {
                magicCircle.discard();
                magicCircle = null;
            }
        }
    }

    private static final List<ActiveSummon> ACTIVE_SUMMONS = new ArrayList<>();

    public static void start(ServerLevel level, ServerPlayer caster, Vec3 groundPos, DemonType demonType, boolean isWinged) {
        Vec3 center = findGroundBelow(level, groundPos);
        ActiveSummon ritual = new ActiveSummon(level, caster, center, demonType, isWinged, 90); // 4.5 giây

        // 1. Tạo Pháp Trận Triệu Hồi Ma Giới (Display Entity trên mặt đất, đường kính 9m)
        Display.ItemDisplay circleDisplay = EntityType.ITEM_DISPLAY.create(level);
        if (circleDisplay != null) {
            circleDisplay.moveTo(center.x, center.y + 0.04D, center.z, 0.0F, 0.0F);
            ItemDisplayAccessor itemAcc = (ItemDisplayAccessor) circleDisplay;
            DisplayAccessor dispAcc = (DisplayAccessor) circleDisplay;

            itemAcc.weapons$setItemStack(new ItemStack(ModItems.DEMON_SUMMONING_CIRCLE.get()));
            itemAcc.weapons$setItemTransform(ItemDisplayContext.FIXED);
            dispAcc.weapons$setBillboardConstraints(Display.BillboardConstraints.FIXED);
            circleDisplay.setGlowingTag(true);
            dispAcc.weapons$setGlowColorOverride(demonType.getGlowColor());
            dispAcc.weapons$setViewRange(6.0F);

            Quaternionf rot = new Quaternionf().rotateX((float) Math.toRadians(90.0F));
            dispAcc.weapons$setTransformation(new Transformation(
                    new Vector3f(0.0F, 0.0F, 0.0F),
                    rot,
                    new Vector3f(9.0F, 9.0F, 0.01F),
                    null
            ));

            level.addFreshEntity(circleDisplay);
            ritual.magicCircle = circleDisplay;
        }

        // 2. Tạo Ác Ma ẩn dưới lòng đất (độ sâu -2.4m)
        PrimordialDemonEntity demon = ModEntities.PRIMORDIAL_DEMON.get().create(level);
        if (demon != null) {
            demon.moveTo(center.x, center.y - 2.4D, center.z, caster.getYRot() + 180.0F, 0.0F);
            demon.setDemonType(demonType);
            demon.setWinged(isWinged);
            demon.setRising(true);
            demon.setRisingProgress(0.0F);
            demon.setTame(false, false); // CHƯA THU PHỤC - SẼ TẤN CÔNG ĐỂ THỬ THÁCH NGƯỜI CHƠI
            demon.setTarget(caster);
            level.addFreshEntity(demon);
            ritual.demon = demon;
        }

        ACTIVE_SUMMONS.add(ritual);

        // Âm thanh khởi động nghi thức triệu hồi
        level.playSound(null, center.x, center.y, center.z, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.0F, 0.7F);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 2.5F, 0.8F);
        level.playSound(null, center.x, center.y, center.z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 2.0F, 0.6F);

        caster.displayClientMessage(
                Component.literal("§c§l[PHÁP TRẬN TRIỆU HỒI] §eBắt đầu nghi lễ hiệu triệu Ác Ma Thủy Tổ §6" + demonType.getColorName() + "§e..."),
                true
        );
    }

    public static void tickRituals(ServerLevel serverLevel) {
        if (ACTIVE_SUMMONS.isEmpty()) return;

        Iterator<ActiveSummon> it = ACTIVE_SUMMONS.iterator();
        while (it.hasNext()) {
            ActiveSummon r = it.next();
            if (r.level != serverLevel) continue;

            r.ticksRemaining--;
            int elapsed = r.totalTicks - r.ticksRemaining;
            float progress = (float) elapsed / (float) r.totalTicks;

            // 1. ÁC MA TỪ TỪ TRỒI TỪ DƯỚI ĐẤT LÊN
            if (r.demon != null && r.demon.isAlive()) {
                double currentY = r.center.y - 2.4D + (2.4D * progress);
                r.demon.moveTo(r.center.x, currentY, r.center.z, (float) (elapsed * 2.5), 0.0F);
                r.demon.setRisingProgress(progress);
                r.demon.setDeltaMovement(0, 0, 0);

                // Hạt sương mù và lửa xoáy bọc quanh thân thể đang trồi lên
                r.level.sendParticles(ParticleTypes.DRAGON_BREATH, r.center.x, currentY + 0.5D, r.center.z, 8, 0.4D, 0.4D, 0.4D, 0.02D);
                r.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, r.center.x, currentY + 0.8D, r.center.z, 4, 0.3D, 0.5D, 0.3D, 0.02D);
            }

            // 2. PHÁP TRẬN XOAY CHẬM TRÊN MẶT ĐẤT
            if (r.magicCircle != null && r.magicCircle.isAlive()) {
                DisplayAccessor dispAcc = (DisplayAccessor) r.magicCircle;
                Quaternionf rot = new Quaternionf()
                        .rotateX((float) Math.toRadians(90.0F))
                        .rotateZ((float) Math.toRadians(elapsed * 1.5F));
                dispAcc.weapons$setTransformation(new Transformation(
                        new Vector3f(0.0F, 0.0F, 0.0F),
                        rot,
                        new Vector3f(9.0F, 9.0F, 0.01F),
                        null
                ));
            }

            // 3. 16 NGỌN NẾN ĐỎ BỐC CHÁY RỰC LỬA TRÊN VÀNH PHÁP TRẬN
            double candleRadius = 4.2D;
            for (int i = 0; i < 16; i++) {
                double angle = (i * 2.0D * Math.PI) / 16.0D + Math.toRadians(elapsed * 1.5D);
                double cx = r.center.x + Math.cos(angle) * candleRadius;
                double cz = r.center.z + Math.sin(angle) * candleRadius;

                if (elapsed % 3 == 0) {
                    r.level.sendParticles(ParticleTypes.FLAME, cx, r.center.y + 0.15D, cz, 1, 0.02D, 0.05D, 0.02D, 0.01D);
                    r.level.sendParticles(ParticleTypes.SMOKE, cx, r.center.y + 0.25D, cz, 1, 0.01D, 0.05D, 0.01D, 0.01D);
                }
            }

            // Âm thanh nhịp đập ma quỷ
            if (elapsed % 25 == 0) {
                r.level.playSound(null, r.center.x, r.center.y, r.center.z,
                        SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 2.5F, 0.7F + (progress * 0.4F));
            }

            // =========================================================================
            // GIAI ĐOẠN CUỐI (Elapsed >= 90): ÁC MA CHÍNH THỨC GIÁNG LÂM & NỔ XUNG KÍCH!
            // =========================================================================
            if (elapsed >= r.totalTicks) {
                if (r.demon != null && r.demon.isAlive()) {
                    r.demon.setRising(false);
                    r.demon.setRisingProgress(1.0F);
                    r.demon.moveTo(r.center.x, r.center.y, r.center.z, r.caster.getYRot() + 180.0F, 0.0F);
                    r.demon.setTarget(r.caster); // Lao vào tấn công người chơi để thử thách!
                }

                // Vụ nổ xung kích bùng nổ dọn sạch pháp trận
                r.level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, r.center.x, r.center.y + 1.0D, r.center.z, 1, 0, 0, 0, 0);
                r.level.sendParticles(ParticleTypes.FLASH, r.center.x, r.center.y + 1.5D, r.center.z, 2, 0, 0, 0, 0);
                r.level.sendParticles(ParticleTypes.DRAGON_BREATH, r.center.x, r.center.y + 1.0D, r.center.z, 60, 1.5D, 1.0D, 1.5D, 0.1D);
                r.level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, r.center.x, r.center.y + 1.0D, r.center.z, 40, 1.2D, 0.8D, 1.2D, 0.08D);

                r.level.playSound(null, r.center.x, r.center.y, r.center.z,
                        SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.5F, 0.9F);
                r.level.playSound(null, r.center.x, r.center.y, r.center.z,
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 2.5F, 1.2F);
                r.level.playSound(null, r.center.x, r.center.y, r.center.z,
                        SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 2.0F, 0.8F);

                // Gửi Title & Giọng Nói Thế Giới thông báo khiêu chiến
                if (r.caster != null && r.caster.connection != null) {
                    r.caster.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§c§l★ THỬ THÁCH MA GIỚI ★")));
                    r.caster.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§e" + r.demonType.getColorName() + " §7đang khiêu chiến! Hãy đánh bại nó để thu phục!")));
                    VoiceOfTheWorld.announce(r.caster, "Báo cáo. Thủy Tổ Ác Ma: " + r.demonType.getColorName() + " (" + r.demonType.getTitleVi() + ") đang khiêu chiến bạn! Hãy đánh bại nó để khiến nó quy phục!");
                    r.caster.displayClientMessage(
                            Component.literal("§d§l[" + r.demonType.getColorName().toUpperCase() + "] §f\"" + r.demonType.getSummonDialogue() + "\""),
                            false
                    );
                }

                r.cleanup();
                it.remove();
            }
        }
    }

    private static Vec3 findGroundBelow(ServerLevel level, Vec3 pos) {
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(
                Math.floor(pos.x),
                Math.floor(pos.y),
                Math.floor(pos.z)
        );
        int upLimit = 0;
        while (isSolid(level, mpos) && upLimit < 10 && mpos.getY() < level.getMaxBuildHeight()) {
            mpos.move(Direction.UP);
            upLimit++;
        }
        int downLimit = 0;
        while (!isSolid(level, mpos) && downLimit < 60 && mpos.getY() > level.getMinBuildHeight()) {
            mpos.move(Direction.DOWN);
            downLimit++;
        }
        return new Vec3(pos.x, mpos.getY() + 1.0D, pos.z);
    }

    private static boolean isSolid(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return !state.isAir() && state.blocksMotion();
    }
}
