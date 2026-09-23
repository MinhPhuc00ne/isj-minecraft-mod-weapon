/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken.goals;

import java.util.Comparator;
import java.util.EnumSet;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntityPart;
import net.unusual.block_factorys_bosses.entity.decoration.CratePileEntity;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownCrateEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleCrateGoal
extends Goal {
    protected KrakenTentacleEntity tentacleEntity;
    protected final String attackAnimation;
    protected final float rotationRangeScale;
    protected final int duration;
    protected final int hitOnTick;
    protected int tick;
    @Nullable
    protected LivingEntity target = null;

    public KrakenTentacleCrateGoal(KrakenTentacleEntity tentacleEntity, String attackAnimation, float rotationRangeScale, int duration, int hitOnTick) {
        this.tentacleEntity = tentacleEntity;
        this.attackAnimation = attackAnimation;
        this.rotationRangeScale = rotationRangeScale;
        this.duration = this.adjustedTickDelay(duration);
        this.hitOnTick = this.adjustedTickDelay(hitOnTick);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean isInterruptable() {
        return false;
    }

    public boolean canUse() {
        if (this.tentacleEntity.getTentacleType() != KrakenTentacleEntity.TentacleType.CRATE) {
            return false;
        }
        LivingEntity target = this.tentacleEntity.getTarget();
        if (target == null) {
            return false;
        }
        if (this.tentacleEntity.getRandom().nextFloat() >= 0.2f) {
            return false;
        }
        KrakenEntity kraken = this.tentacleEntity.getOwnerKraken();
        if (kraken != null && (kraken.isKnockedDown() || kraken.allTentaclesBusy())) {
            return false;
        }
        return this.tentacleEntity.hasLineOfSight((Entity)target, this.tentacleEntity.getTargetRotationRange() * this.rotationRangeScale);
    }

    public boolean canContinueToUse() {
        if (this.target == null || this.target.isDeadOrDying()) {
            return false;
        }
        return this.tick < this.duration;
    }

    public void start() {
        super.start();
        this.target = this.tentacleEntity.getTarget();
        this.tentacleEntity.triggerAnim(this.attackAnimation);
        if (this.target == null) {
            return;
        }
        this.target.level().getEntitiesOfClass(CratePileEntity.class, this.tentacleEntity.getBoundingBox().inflate(32.0)).stream().min(Comparator.comparingDouble(value -> value.distanceTo((Entity)this.tentacleEntity))).ifPresent(crateEntity -> {
            this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_POSITION, crateEntity.position());
            this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_TIME, this.tentacleEntity.level().getGameTime());
        });
    }

    public void stop() {
        super.stop();
        this.tick = 0;
        this.target = null;
        this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_POSITION, Vec3.ZERO);
        this.tentacleEntity.getEntityData().set(KrakenTentacleEntity.DATA_LERP_TIME, 0L);
        this.tentacleEntity.stopTriggeredAnim(this.attackAnimation);
    }

    public void tick() {
        super.tick();
        ++this.tick;
        if (this.target != null) {
            this.tentacleEntity.getLookControl().setLookAt((Entity)this.target);
        }
        if (this.tick == this.hitOnTick) {
            this.performHit();
        }
    }

    public void performHit() {
        Level level;
        if (this.target == null || !((level = this.tentacleEntity.level()) instanceof ServerLevel)) {
            return;
        }
        ServerLevel level2 = (ServerLevel)level;
        KrakenTentacleEntityPart part = this.tentacleEntity.getThrowPart();
        ThrownCrateEntity snowball = new ThrownCrateEntity((EntityType<? extends ThrownCrateEntity>)((EntityType)BossesRiseEntities.THROWN_CRATE.get()), (Level)level2);
        snowball.setOwner((Entity)this.tentacleEntity);
        snowball.setPos(part.getX(), part.getEyeY(), part.getZ());
        double d0 = this.target.getEyeY() - (double)1.1f;
        double d1 = this.target.getX() - part.getX();
        double d2 = d0 - snowball.getY();
        double d3 = this.target.getZ() - part.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2f;
        snowball.shoot(d1, d2 + d4, d3, 3.2f, 6.0f);
        this.tentacleEntity.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0f, 0.4f / (this.tentacleEntity.getRandom().nextFloat() * 0.4f + 0.8f));
        level2.addFreshEntity((Entity)snowball);
    }
}

