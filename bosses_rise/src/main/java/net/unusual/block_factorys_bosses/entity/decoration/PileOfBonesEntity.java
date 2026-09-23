/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.AreaEffectCloud
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.ThrownPotion
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.neoforged.neoforge.common.NeoForgeMod
 *  net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent
 */
package net.unusual.block_factorys_bosses.entity.decoration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.procedures.PileOfBonesEntityIsHurtProcedure;
import net.unusual.block_factorys_bosses.procedures.PileOfBonesOnEntityTickUpdateProcedure;

public class PileOfBonesEntity
extends PathfinderMob {
    public static final EntityDataAccessor<Integer> DATA_remaning_hit = SynchedEntityData.defineId(PileOfBonesEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_hit_animation_time = SynchedEntityData.defineId(PileOfBonesEntity.class, (EntityDataSerializer)EntityDataSerializers.INT);

    public PileOfBonesEntity(EntityType<PileOfBonesEntity> type, Level world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(true);
        this.setPersistenceRequired();
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_remaning_hit, 5);
        builder.define(DATA_hit_animation_time, 0);
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.BONE_BLOCK_BREAK;
    }

    public boolean hurt(DamageSource damagesource, float amount) {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Level world = this.level();
        PileOfBonesEntity entity = this;
        Entity sourceentity = damagesource.getEntity();
        Entity immediatesourceentity = damagesource.getDirectEntity();
        if (sourceentity instanceof Player) {
            Player player = (Player)sourceentity;
            if (!player.getAbilities().mayBuild) {
                return false;
            }
            if (player.getAbilities().instabuild) {
                this.discard();
                return true;
            }
        }
        PileOfBonesEntityIsHurtProcedure.execute((LevelAccessor)world, x, y, z, (Entity)entity);
        if (damagesource.is(DamageTypes.IN_FIRE)) {
            return false;
        }
        if (damagesource.getDirectEntity() instanceof AbstractArrow) {
            return false;
        }
        if (damagesource.getDirectEntity() instanceof Player) {
            return false;
        }
        if (damagesource.getDirectEntity() instanceof ThrownPotion || damagesource.getDirectEntity() instanceof AreaEffectCloud || damagesource.typeHolder().is(NeoForgeMod.POISON_DAMAGE)) {
            return false;
        }
        if (damagesource.is(DamageTypes.FALL)) {
            return false;
        }
        if (damagesource.is(DamageTypes.CACTUS)) {
            return false;
        }
        if (damagesource.is(DamageTypes.DROWN)) {
            return false;
        }
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) {
            return false;
        }
        if (damagesource.is(DamageTypes.EXPLOSION) || damagesource.is(DamageTypes.PLAYER_EXPLOSION)) {
            return false;
        }
        if (damagesource.is(DamageTypes.TRIDENT)) {
            return false;
        }
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) {
            return false;
        }
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) {
            return false;
        }
        if (damagesource.is(DamageTypes.WITHER) || damagesource.is(DamageTypes.WITHER_SKULL)) {
            return false;
        }
        return super.hurt(damagesource, amount);
    }

    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    public boolean fireImmune() {
        return true;
    }

    public void die(DamageSource source) {
        super.die(source);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Dataremaning_hit", ((Integer)this.entityData.get(DATA_remaning_hit)).intValue());
        compound.putInt("Datahit_animation_time", ((Integer)this.entityData.get(DATA_hit_animation_time)).intValue());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Dataremaning_hit")) {
            this.entityData.set(DATA_remaning_hit, compound.getInt("Dataremaning_hit"));
        }
        if (compound.contains("Datahit_animation_time")) {
            this.entityData.set(DATA_hit_animation_time, compound.getInt("Datahit_animation_time"));
        }
    }

    public void baseTick() {
        super.baseTick();
        PileOfBonesOnEntityTickUpdateProcedure.execute((Entity)this);
    }

    public boolean canCollideWith(Entity entity) {
        return true;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        return builder;
    }
}

