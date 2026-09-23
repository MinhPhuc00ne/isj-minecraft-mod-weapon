/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.TraceableEntity
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.EntityGetter
 */
package net.unusual.block_factorys_bosses.entity;

import java.util.EnumSet;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityGetter;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface OwnableByAllEntity
extends OwnableEntity,
TraceableEntity {
    @Nullable
    default public LivingEntity getOwner() {
        UUID uuid = this.getOwnerUUID();
        if (uuid == null) {
            return null;
        }
        EntityGetter entityGetter = this.level();
        if (entityGetter instanceof ServerLevel) {
            LivingEntity living;
            ServerLevel level = (ServerLevel)entityGetter;
            Entity entity = level.getEntity(uuid);
            return entity instanceof LivingEntity ? (living = (LivingEntity)entity) : null;
        }
        return this.level().getPlayerByUUID(uuid);
    }

    public static class DoNotAttackOwnerGoal<T extends Mob, R extends LivingEntity>
    extends NearestAttackableTargetGoal<R> {
        public DoNotAttackOwnerGoal(T mob, Class<R> targetType, boolean mustSee, boolean mustReach) {
            super(mob, targetType, mustSee, mustReach);
        }

        protected void findTarget() {
            this.target = this.targetType != Player.class && this.targetType != ServerPlayer.class ? this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), entity -> this.canAttack((LivingEntity)entity, this.targetConditions)), this.targetConditions, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ()) : this.mob.level().getNearestPlayer(this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.getFollowDistance(), entity -> this.canAttack((LivingEntity)((Player)entity), this.targetConditions));
        }

        protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            OwnableEntity ownable;
            if (potentialTarget == ((OwnableEntity)this.mob).getOwner()) {
                return false;
            }
            if (potentialTarget instanceof OwnableEntity && (ownable = (OwnableEntity)potentialTarget).getOwner() == ((OwnableEntity)this.mob).getOwner()) {
                return false;
            }
            return super.canAttack(potentialTarget, targetPredicate);
        }
    }

    public static class OwnerHurtTargetGoal<T extends Mob>
    extends TargetGoal {
        private final T ownable;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public OwnerHurtTargetGoal(T ownable) {
            super(ownable, false);
            this.ownable = ownable;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity owner = ((OwnableEntity)this.ownable).getOwner();
            if (owner == null) {
                return false;
            }
            this.ownerLastHurt = owner.getLastHurtMob();
            int i = owner.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurt);
            LivingEntity owner = ((OwnableEntity)this.ownable).getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtMobTimestamp();
            }
            super.start();
        }

        protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            OwnableEntity ownableTarget;
            if (potentialTarget == ((OwnableEntity)this.ownable).getOwner()) {
                return false;
            }
            if (potentialTarget instanceof OwnableEntity && (ownableTarget = (OwnableEntity)potentialTarget).getOwner() == ((OwnableEntity)this.ownable).getOwner()) {
                return false;
            }
            return super.canAttack(potentialTarget, targetPredicate);
        }
    }

    public static class OwnerHurtByTargetGoal<T extends Mob>
    extends TargetGoal {
        private final T ownable;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public OwnerHurtByTargetGoal(T ownable) {
            super(ownable, false);
            this.ownable = ownable;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity owner = ((OwnableEntity)this.ownable).getOwner();
            if (owner == null) {
                return false;
            }
            this.ownerLastHurtBy = owner.getLastHurtByMob();
            int i = owner.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity owner = ((OwnableEntity)this.ownable).getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtByMobTimestamp();
            }
            super.start();
        }

        protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
            OwnableEntity ownableTarget;
            if (potentialTarget == ((OwnableEntity)this.ownable).getOwner()) {
                return false;
            }
            if (potentialTarget instanceof OwnableEntity && (ownableTarget = (OwnableEntity)potentialTarget).getOwner() == ((OwnableEntity)this.ownable).getOwner()) {
                return false;
            }
            return super.canAttack(potentialTarget, targetPredicate);
        }
    }
}

