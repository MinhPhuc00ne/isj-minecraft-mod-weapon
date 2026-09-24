/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.entity.boss.kraken.goals;

import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.util.SpatialUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTentacleRunawayGoal
extends Goal {
    protected KrakenTentacleEntity tentacleEntity;
    protected final String attackAnimation;
    protected final float rotationRangeScale;
    protected final int duration;
    protected final int hitOnTick;
    protected int tick;

    public KrakenTentacleRunawayGoal(KrakenTentacleEntity tentacleEntity, String attackAnimation, float rotationRangeScale, int duration, int hitOnTick) {
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
        return this.tentacleEntity.getTentacleType() == KrakenTentacleEntity.TentacleType.RUNAWAY;
    }

    public boolean canContinueToUse() {
        if (this.tentacleEntity.getTarget() == null) {
            return false;
        }
        return this.tick < this.duration;
    }

    public void start() {
        super.start();
        this.tentacleEntity.triggerAnim(this.attackAnimation);
    }

    public void stop() {
        super.stop();
        this.tentacleEntity.discard();
    }

    public void tick() {
        super.tick();
        ++this.tick;
        LivingEntity target = this.tentacleEntity.getTarget();
        if (target instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)target;
            KrakenEntity kraken = this.tentacleEntity.getOwnerKraken();
            if (kraken == null || !KrakenEntity.shouldChasePlayer(player, kraken.getShipPosition().getCenter())) {
                this.stop();
                return;
            }
            this.tentacleEntity.getLookControl().setLookAt((Entity)target);
            Vec3 offset = new Vec3(-1.75, -13.0, 0.0).yRot((-this.tentacleEntity.getYRot() - 180.0f) * ((float)Math.PI / 180));
            this.tentacleEntity.moveTo(target.position().add(offset));
        }
        if (this.tick == this.hitOnTick) {
            this.performHit();
        }
    }

    public void performHit() {
        LivingEntity livingEntity = this.tentacleEntity.getOwner();
        if (livingEntity instanceof KrakenEntity) {
            KrakenEntity kraken = (KrakenEntity)livingEntity;
            BlockPos pos = ((Optional<BlockPos>)kraken.getEntityData().get(KrakenEntity.DATA_SHIP_POSITION)).orElse(null);
            if (pos == null) {
                return;
            }
            Vec3 center = pos.getCenter();
            LivingEntity target = this.tentacleEntity.getTarget();
            if (target instanceof Player) {
                Player player = (Player)target;
                SpatialUtil.pushEntity((Entity)player, this.getThrowVector(center, player.position()));
            } else if (target != null) {
                target.push(this.getThrowVector(center, target.position()));
            }
            this.stop();
        }
    }

    private Vec3 getThrowVector(Vec3 center, Vec3 targetPos) {
        return center.subtract(targetPos).scale((double)0.05f).add(0.0, 0.35, 0.0).scale(3.0);
    }
}

