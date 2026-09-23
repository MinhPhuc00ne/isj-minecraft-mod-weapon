/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ProjectileUtil
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
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animatable.GeoEntity
 *  software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
 *  software.bernie.geckolib.animation.AnimatableManager$ControllerRegistrar
 *  software.bernie.geckolib.util.GeckoLibUtil
 */
package net.unusual.block_factorys_bosses.entity.boss.yeti;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
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
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeProjectileEntity
extends Projectile
implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);
    private double baseDamage = 2.0;
    private int knockback = 0;

    public IceSpikeProjectileEntity(EntityType<? extends IceSpikeProjectileEntity> type, Level world) {
        super(type, world);
    }

    public IceSpikeProjectileEntity(EntityType<? extends IceSpikeProjectileEntity> type, LivingEntity owner, Level level) {
        this(type, level);
        this.setOwner((Entity)owner);
        this.setPos(owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ());
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        double bb = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(bb)) {
            bb = 1.0;
        }
        return distance < (bb *= 64.0 * IceSpikeProjectileEntity.getViewScale()) * bb;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public boolean isAttackable() {
        return false;
    }

    protected float getWaterInertia() {
        return 0.6f;
    }

    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(3.0);
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

    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (player == this.getOwner()) {
            return;
        }
        ItemStack stack = new ItemStack((ItemLike)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).copy();
        player.setTicksFrozen(200);
        Level level = this.level();
        if (level instanceof ServerLevel) {
            YetiEntity yeti;
            double distance;
            ServerLevel level2 = (ServerLevel)level;
            level2.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX(), this.getY(), this.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            player.hurt(this.damageSources().freeze(), 8.0f);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            Entity entity = this.getOwner();
            if (entity instanceof YetiEntity && (distance = (double)(yeti = (YetiEntity)entity).distanceTo((Entity)player)) >= 10.0 && distance <= 20.0) {
                yeti.doAttack(YetiEntity.YetiState.RANGED_CONTROL.getAttack());
            }
            this.discard();
        }
    }

    protected double getDefaultGravity() {
        return 0.05;
    }

    protected void onHitEntity(EntityHitResult result) {
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
            this.breakIce();
        } else {
            target.setRemainingFireTicks(fireTicks);
            this.breakIce();
        }
    }

    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.breakIce();
    }

    public void breakIce() {
        ItemStack stack = new ItemStack((ItemLike)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).copy();
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel level2 = (ServerLevel)level;
            level2.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.9f, (float)Mth.nextDouble((RandomSource)this.getRandom(), (double)0.15, (double)0.35));
            level2.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), this.getX(), this.getY(), this.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            this.discard();
        }
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
        for (int i = 0; i < 4; ++i) {
            this.level().addParticle((ParticleOptions)BossesRiseParticleTypes.SNOW_FLAKE.get(), this.getX() + dX * (double)i / 4.0, this.getY() + dY * (double)i / 4.0, this.getZ() + dZ * (double)i / 4.0, -dX * 0.02, -(dY + 0.2) * 0.02, -dZ * 0.02);
        }
        double newX = this.getX() + dX;
        double newY = this.getY() + dY;
        double newZ = this.getZ() + dZ;
        double d4 = delta.horizontalDistance();
        this.setYRot((float)(Mth.atan2((double)dX, (double)dZ) * 180.0 / 3.1415927410125732));
        this.setXRot((float)(Mth.atan2((double)dY, (double)d4) * 180.0 / 3.1415927410125732));
        this.setXRot(IceSpikeProjectileEntity.lerpRotation((float)this.xRotO, (float)this.getXRot()));
        this.setYRot(IceSpikeProjectileEntity.lerpRotation((float)this.yRotO, (float)this.getYRot()));
        float f = 0.99f;
        if (this.isInWater()) {
            for (int j = 0; j < 4; ++j) {
                this.level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, newX - dX * 0.25, newY - dY * 0.25, newZ - dZ * 0.25, dX, dY, dZ);
            }
            f = this.getWaterInertia();
        }
        this.setDeltaMovement(delta.scale((double)f));
        this.applyGravity();
        this.setPos(newX, newY, newZ);
        this.checkInsideBlocks();
    }

    public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return IceSpikeProjectileEntity.shoot(world, entity, source, 1.0f, 5.0, 0);
    }

    public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return IceSpikeProjectileEntity.shoot(world, entity, source, pullingPower, 5.0, 0);
    }

    public static IceSpikeProjectileEntity shoot(Level world, LivingEntity entity, RandomSource ignoredRandom, float power, double damage, int knockback) {
        IceSpikeProjectileEntity iceSpike = new IceSpikeProjectileEntity((EntityType<? extends IceSpikeProjectileEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get()), entity, world);
        iceSpike.shoot(entity.getViewVector((float)1.0f).x, entity.getViewVector((float)1.0f).y, entity.getViewVector((float)1.0f).z, power * 2.0f, 0.0f);
        iceSpike.setSilent(true);
        iceSpike.setBaseDamage(damage);
        iceSpike.setKnockback(knockback);
        world.addFreshEntity((Entity)iceSpike);
        return iceSpike;
    }

    public static IceSpikeProjectileEntity shoot(LivingEntity entity, LivingEntity target) {
        IceSpikeProjectileEntity iceSpike = new IceSpikeProjectileEntity((EntityType<? extends IceSpikeProjectileEntity>)((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get()), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + (double)target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        iceSpike.shoot(dx, dy - iceSpike.getY() + Math.hypot(dx, dz) * (double)0.2f, dz, 2.0f, 12.0f);
        iceSpike.setSilent(true);
        iceSpike.setBaseDamage(5.0);
        iceSpike.setKnockback(0);
        entity.level().addFreshEntity((Entity)iceSpike);
        return iceSpike;
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult((Level)this.level(), (Entity)this, (Vec3)startVec, (Vec3)endVec, (AABB)this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), x$0 -> this.canHitEntity((Entity)x$0));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putDouble("damage", this.baseDamage);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("damage", 99)) {
            this.baseDamage = compound.getDouble("damage");
        }
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}

