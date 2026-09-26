package com.minhphuc.weapons.entity.darkgathering;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Không Vong (Kūbō / The Black Sun God Embryo) - Dark Gathering.
 * Mặt trời đen khổng lồ lơ lửng trên bầu trời với con mắt trung tâm liếc nhìn xung quanh
 * và các vật thể/xúc tu lúc nhúc lúc nhúc uốn lượn liên tục.
 * Tạm thời chỉ có đòn đánh thường (va chạm hư vô nghiền nát).
 */
public class KuboEntity extends Monster {

    public KuboEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
        this.setCustomName(Component.literal("§0§l[KHÔNG VONG] §4§lKūbō - Hắc Nhật Phôi Thai"));
        this.setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 1500.0D)
                .add(Attributes.FLYING_SPEED, 0.45D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 55.0D)
                .add(Attributes.ARMOR, 24.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new KuboFloatGoal(this));
        this.goalSelector.addGoal(2, new KuboChargeAttackGoal(this));
        this.goalSelector.addGoal(5, new KuboHoverRandomlyGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);

        // Hiệu ứng hạt hư vô màu đen tím toả ra từ mặt trời đen
        if (this.level() instanceof ServerLevel sl && this.tickCount % 2 == 0) {
            double r = 1.6D;
            double ox = (this.random.nextDouble() - 0.5D) * r * 2.0D;
            double oy = (this.random.nextDouble() - 0.5D) * r * 2.0D;
            double oz = (this.random.nextDouble() - 0.5D) * r * 2.0D;
            sl.sendParticles(ParticleTypes.SQUID_INK, this.getX() + ox, this.getY() + 1.2D + oy, this.getZ() + oz, 2, 0, 0, 0, 0.01D);
            sl.sendParticles(ParticleTypes.DRAGON_BREATH, this.getX() + ox, this.getY() + 1.2D + oy, this.getZ() + oz, 1, 0, 0, 0, 0.01D);
        }
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hurt = super.doHurtTarget(target);
        if (hurt && target instanceof LivingEntity living) {
            // Đòn đánh thường bằng va chạm lực trường hư vô
            living.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 1));
            living.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2));
            this.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1.8F, 0.6F);

            if (this.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.SONIC_BOOM, living.getX(), living.getY() + 1.0D, living.getZ(), 1, 0, 0, 0, 0);
                sl.sendParticles(ParticleTypes.EXPLOSION, living.getX(), living.getY() + 1.0D, living.getZ(), 3, 0.2D, 0.2D, 0.2D, 0.0D);
            }
        }
        return hurt;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    // ==========================================
    // AI GOALS: BAY LƠ LỬNG VÀ TIẾP CẬN CẬN CHIẾN
    // ==========================================
    static class KuboFloatGoal extends Goal {
        private final KuboEntity kubo;

        public KuboFloatGoal(KuboEntity kubo) {
            this.kubo = kubo;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            // Giữ độ cao lơ lửng trên mặt đất 2-4 blocks
            BlockPos ground = kubo.blockPosition().below(3);
            if (kubo.level().isEmptyBlock(ground)) {
                kubo.setDeltaMovement(kubo.getDeltaMovement().add(0, -0.01D, 0));
            } else {
                kubo.setDeltaMovement(kubo.getDeltaMovement().add(0, 0.015D, 0));
            }
        }
    }

    static class KuboHoverRandomlyGoal extends Goal {
        private final KuboEntity kubo;

        public KuboHoverRandomlyGoal(KuboEntity kubo) {
            this.kubo = kubo;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !kubo.getMoveControl().hasWanted() && kubo.getRandom().nextInt(7) == 0 && kubo.getTarget() == null;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            Vec3 pos = kubo.position();
            double tx = pos.x + (kubo.getRandom().nextDouble() - 0.5D) * 16.0D;
            double ty = pos.y + (kubo.getRandom().nextDouble() - 0.5D) * 6.0D;
            double tz = pos.z + (kubo.getRandom().nextDouble() - 0.5D) * 16.0D;
            kubo.getMoveControl().setWantedPosition(tx, ty, tz, 0.8D);
        }
    }

    static class KuboChargeAttackGoal extends Goal {
        private final KuboEntity kubo;

        public KuboChargeAttackGoal(KuboEntity kubo) {
            this.kubo = kubo;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = kubo.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public void tick() {
            LivingEntity target = kubo.getTarget();
            if (target == null) return;

            kubo.getLookControl().setLookAt(target, 30.0F, 30.0F);
            kubo.getMoveControl().setWantedPosition(target.getX(), target.getY() + 1.0D, target.getZ(), 1.25D);

            double distSq = kubo.distanceToSqr(target);
            if (distSq <= 9.0D && kubo.tickCount % 20 == 0) {
                kubo.doHurtTarget(target);
            }
        }
    }
}
