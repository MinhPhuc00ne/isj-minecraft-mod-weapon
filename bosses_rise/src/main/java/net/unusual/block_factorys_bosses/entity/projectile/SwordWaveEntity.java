/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ProjectileDeflection
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.neoforge.event.EventHooks
 *  net.neoforged.neoforge.fluids.FluidType
 *  org.jetbrains.annotations.Nullable
 */
package net.unusual.block_factorys_bosses.entity.projectile;

import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidType;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import org.jetbrains.annotations.Nullable;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SwordWaveEntity
extends Projectile {
    public static final EntityDataAccessor<Boolean> DATA_VERTICAL = SynchedEntityData.defineId(SwordWaveEntity.class, (EntityDataSerializer)EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(SwordWaveEntity.class, (EntityDataSerializer)EntityDataSerializers.FLOAT);
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
    private int knockback = 0;
    private double baseDamage = 2.0;
    private int timer = 0;

    public SwordWaveEntity(EntityType<? extends SwordWaveEntity> type, Level world) {
        super(type, world);
    }

    public SwordWaveEntity(EntityType<? extends SwordWaveEntity> type, LivingEntity owner, Level level) {
        this(type, level);
        this.setOwner((Entity)owner);
        this.setPos(owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ());
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        double bb = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(bb)) {
            bb = 1.0;
        }
        return distance < (bb *= 64.0 * SwordWaveEntity.getViewScale()) * bb;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_VERTICAL, false);
        builder.define(DATA_SCALE, Float.valueOf(1.0f));
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_VERTICAL.equals(key) || DATA_SCALE.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    public EntityDimensions getDimensions(Pose pose) {
        float scale = ((Float)this.entityData.get(DATA_SCALE)).floatValue();
        if (((Boolean)this.entityData.get(DATA_VERTICAL)).booleanValue()) {
            return super.getDimensions(pose).scale(0.25f * scale, 12.0f * scale);
        }
        return super.getDimensions(pose).scale(scale);
    }

    public boolean isVertical() {
        return (Boolean)this.entityData.get(DATA_VERTICAL);
    }

    public void setVertical(boolean vertical) {
        this.entityData.set(DATA_VERTICAL, vertical);
    }

    public float getScale() {
        return ((Float)this.entityData.get(DATA_SCALE)).floatValue();
    }

    public void setScale(float scale) {
        this.entityData.set(DATA_SCALE, Float.valueOf(scale));
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
        Vec3 delta = this.getDeltaMovement();
        double length = delta.length();
        this.setDeltaMovement(new Vec3(delta.x, 0.0, delta.z).normalize().scale(length));
    }

    protected void doKnockback(LivingEntity livingEntity) {
        if ((double)this.knockback > 0.0) {
            double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double)this.knockback * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                livingEntity.push(vec3.x, 0.1, vec3.z);
            }
        }
    }

    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        float delta = (float)this.getDeltaMovement().length();
        double damage = this.baseDamage;
        Entity owner = this.getOwner();
        DamageSource damagesource = this.damageSources().indirectMagic((Entity)this, (Entity)(owner != null ? owner : this));
        int ceil = Mth.ceil((double)Mth.clamp((double)((double)delta * damage), (double)0.0, (double)2.147483647E9));
        if (owner instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)owner;
            living.setLastHurtMob(target);
        }
        boolean enderman = target.getType() == EntityType.ENDERMAN;
        int fireTicks = target.getRemainingFireTicks();
        if (this.isOnFire() && !enderman) {
            target.igniteForSeconds(5.0f);
        }
        if (target.hurt(damagesource, (float)ceil)) {
            if (enderman) {
                return;
            }
            if (target instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)target;
                this.doKnockback(living);
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                Entity entity = this.getOwner();
                if (entity instanceof YetiEntity) {
                    double distance;
                    YetiEntity yeti = (YetiEntity)entity;
                    if (living instanceof Player && (distance = (double)yeti.distanceTo((Entity)living)) >= 10.0 && distance <= 20.0) {
                        yeti.doAttack(YetiEntity.YetiState.RANGED_CONTROL.getAttack());
                    }
                }
                if (living != owner && living instanceof Player && owner instanceof ServerPlayer) {
                    ServerPlayer player = (ServerPlayer)owner;
                    if (!this.isSilent()) {
                        player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0f));
                    }
                }
            }
        } else {
            target.setRemainingFireTicks(fireTicks);
        }
    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity entity, @Nullable Entity owner, boolean deflectedByPlayer) {
        this.discardWithParticles();
        return true;
    }

    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discardWithParticles();
    }

    private void discardWithParticles() {
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.MAGICAL_DOT.get()), this.getX(), this.getY(), this.getZ(), 8, 0.0, 0.0, 0.0, Math.random() * 0.1);
            this.discard();
        }
    }

    public boolean fireImmune() {
        return true;
    }

    public boolean isOnFire() {
        return false;
    }

    public void tick() {
        EntityHitResult entityhitresult;
        super.tick();
        Vec3 delta = this.getDeltaMovement();
        if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
            double d0 = delta.horizontalDistance();
            this.setYRot((float)(Mth.atan2((double)delta.x, (double)delta.z) * 180.0 / 3.1415927410125732));
            this.setXRot((float)(Mth.atan2((double)delta.y, (double)d0) * 180.0 / 3.1415927410125732));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW)) {
            this.clearFire();
        }
        Vec3 pos = this.position();
        Vec3 moved = pos.add(delta);
        HitResult hitresult = this.level().clip(new ClipContext(pos, moved, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this));
        if (hitresult.getType() != HitResult.Type.MISS) {
            moved = hitresult.getLocation();
        }
        if ((entityhitresult = this.findHitEntity(pos, moved)) != null) {
            hitresult = entityhitresult;
        }
        if (hitresult instanceof EntityHitResult) {
            EntityHitResult entityHit = (EntityHitResult)hitresult;
            Entity entity = entityHit.getEntity();
            Entity entity1 = this.getOwner();
            if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                hitresult = null;
            }
        }
        if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact((Projectile)this, (HitResult)hitresult)) {
            this.hitTargetOrDeflectSelf((HitResult)hitresult);
            this.hasImpulse = true;
        }
        delta = this.getDeltaMovement();
        double dX = delta.x;
        double dY = delta.y;
        double dZ = delta.z;
        double newX = this.getX() + dX;
        double newY = this.getY() + dY;
        double newZ = this.getZ() + dZ;
        double d4 = delta.horizontalDistance();
        this.setYRot((float)(Mth.atan2((double)dX, (double)dZ) * 180.0 / 3.1415927410125732));
        this.setXRot((float)(Mth.atan2((double)dY, (double)d4) * 180.0 / 3.1415927410125732));
        this.setXRot(SwordWaveEntity.lerpRotation((float)this.xRotO, (float)this.getXRot()));
        this.setYRot(SwordWaveEntity.lerpRotation((float)this.yRotO, (float)this.getYRot()));
        float f = 0.99f;
        if (this.isInWater()) {
            for (int j = 0; j < 4; ++j) {
                this.level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, newX - dX * 0.25, newY - dY * 0.25, newZ - dZ * 0.25, dX, dY, dZ);
            }
        }
        this.setDeltaMovement(delta.scale((double)f));
        this.applyGravity();
        this.setPos(newX, newY, newZ);
        this.checkInsideBlocks();
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            level2.sendParticles((ParticleOptions)((SimpleParticleType)BossesRiseParticleTypes.INTRODUCTION_ATTACK_SOULS_3_DIED.get()), this.getX(), this.getY(), this.getZ(), 1, 1.0, 0.2, 1.0, 0.0);
        }
        if (++this.timer >= 200) {
            this.discardWithParticles();
        }
    }

    @javax.annotation.Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;
        for (Entity entity1 : this.level().getEntities((Entity)this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), x$0 -> this.canHitEntity((Entity)x$0))) {
            double d1;
            AABB aabb = entity1.getBoundingBox().inflate(2.0, 0.0, 2.0);
            Optional optional = aabb.clip(startVec, endVec);
            if (!optional.isPresent() || !((d1 = startVec.distanceToSqr((Vec3)optional.get())) < d0)) continue;
            entity = entity1;
            d0 = d1;
        }
        return entity == null ? null : new EntityHitResult(entity);
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Timer", this.timer);
        tag.putBoolean("Vertical", this.isVertical());
        tag.putFloat("WaveScale", this.getScale());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.timer = tag.getInt("Timer");
        this.setVertical(tag.getBoolean("Vertical"));
        this.setScale(tag.getFloat("WaveScale"));
    }
}

