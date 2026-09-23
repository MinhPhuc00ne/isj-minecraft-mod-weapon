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
 *  net.minecraft.network.syncher.SynchedEntityData$Builder
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
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
package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntityPart;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThrownCrateEntity
extends Projectile
implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache((GeoAnimatable)this);

    public ThrownCrateEntity(EntityType<? extends ThrownCrateEntity> type, Level world) {
        super(type, world);
    }

    protected double getDefaultGravity() {
        return 0.05;
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
        entityhitresult = this.findHitEntity(pos, moved);
        if (entityhitresult != null) {
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
        }
        double newX = this.getX() + dX;
        double newY = this.getY() + dY;
        double newZ = this.getZ() + dZ;
        double d4 = delta.horizontalDistance();
        this.setYRot((float)(Mth.atan2((double)dX, (double)dZ) * 180.0 / 3.1415927410125732));
        this.setXRot((float)(Mth.atan2((double)dY, (double)d4) * 180.0 / 3.1415927410125732));
        this.setXRot(ThrownCrateEntity.lerpRotation((float)this.xRotO, (float)this.getXRot()));
        this.setYRot(ThrownCrateEntity.lerpRotation((float)this.yRotO, (float)this.getYRot()));
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

    protected float getWaterInertia() {
        return 0.6f;
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return ProjectileUtil.getEntityHitResult((Level)this.level(), (Entity)this, (Vec3)startVec, (Vec3)endVec, (AABB)this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), x$0 -> this.canHitEntity((Entity)x$0));
    }

    protected void onHitEntity(EntityHitResult result) {
        Level level;
        Level level2;
        DamageSource damagesource;
        Entity entity = result.getEntity();
        if (entity instanceof KrakenTentacleEntityPart || entity instanceof KrakenTentacleEntity) {
            return;
        }
        super.onHitEntity(result);
        if (this.getOwner() != entity && entity.hurt(damagesource = this.damageSources().magic(), 2.0f) && (level2 = this.level()) instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)level2;
            EnchantmentHelper.doPostAttackEffects((ServerLevel)serverlevel, (Entity)entity, (DamageSource)damagesource);
        }
        if ((level = this.level()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.explode((Entity)this, this.getX(), this.getY(), this.getZ(), 1.5f, false, Level.ExplosionInteraction.MOB);
            this.playSound((SoundEvent)BossesRiseSounds.CRATE_LAND.value(), 1.0f, 1.0f);
            this.spawnAtLocation(new ItemStack((ItemLike)BossesRiseItems.CANNONBALL.get(), 2));
            this.discard();
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Level level = this.level();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.explode((Entity)this, this.getX(), this.getY(), this.getZ(), 1.5f, false, Level.ExplosionInteraction.MOB);
            this.playSound((SoundEvent)BossesRiseSounds.CRATE_LAND.value(), 1.0f, 1.0f);
            this.spawnAtLocation(new ItemStack((ItemLike)BossesRiseItems.CANNONBALL.get(), 2));
            this.discard();
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 10.0;
        if (Double.isNaN(d0)) {
            d0 = 0.0;
        }
        return distance < (d0 *= 64.0 * ThrownCrateEntity.getViewScale()) * d0;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}

