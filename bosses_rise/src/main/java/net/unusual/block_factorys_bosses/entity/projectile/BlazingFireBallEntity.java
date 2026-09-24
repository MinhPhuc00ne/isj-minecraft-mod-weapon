/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.ItemSupplier
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.procedures.BlazingFireBallProjectileHitsBlockProcedure;
import net.unusual.block_factorys_bosses.procedures.BlazingFireBallProjectileHitsLivingEntityProcedure;
import net.unusual.block_factorys_bosses.procedures.BlazingFireBallWhileProjectileFlyingTickProcedure;

@OnlyIn(value=Dist.CLIENT, _interface=ItemSupplier.class)
public class BlazingFireBallEntity
extends AbstractArrow
implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
    private int knockback = 0;

    public BlazingFireBallEntity(EntityType<? extends BlazingFireBallEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public BlazingFireBallEntity(EntityType<? extends BlazingFireBallEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
        this.setNoGravity(true);
    }

    public BlazingFireBallEntity(EntityType<? extends BlazingFireBallEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
        this.setNoGravity(true);
    }

    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    protected ItemStack getDefaultPickupItem() {
        return new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
    }

    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
        if ((double)this.knockback > 0.0) {
            double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double)this.knockback * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                livingEntity.push(vec3.x, 0.1, vec3.z);
            }
        }
    }

    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        BlazingFireBallProjectileHitsLivingEntityProcedure.execute((LevelAccessor)this.level(), entityHitResult.getEntity(), (Entity)this);
    }

    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        BlazingFireBallProjectileHitsBlockProcedure.execute((LevelAccessor)this.level(), (Entity)this);
    }

    public void tick() {
        super.tick();
        BlazingFireBallWhileProjectileFlyingTickProcedure.execute((LevelAccessor)this.level(), this.getX(), this.getY(), this.getZ());
        if (this.inGround) {
            this.discard();
        }
    }

    public static BlazingFireBallEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return BlazingFireBallEntity.shoot(world, entity, source, 1.0f, 5.0, 0);
    }

    public static BlazingFireBallEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return BlazingFireBallEntity.shoot(world, entity, source, pullingPower * 1.0f, 5.0, 0);
    }

    public static BlazingFireBallEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        BlazingFireBallEntity entityarrow = new BlazingFireBallEntity((EntityType<? extends BlazingFireBallEntity>)((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get()), entity, world, null);
        entityarrow.shoot(entity.getViewVector((float)1.0f).x, entity.getViewVector((float)1.0f).y, entity.getViewVector((float)1.0f).z, power * 2.0f, 0.0f);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity((Entity)entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.5f + 1.0f) + power / 2.0f);
        return entityarrow;
    }

    public static BlazingFireBallEntity shoot(LivingEntity entity, LivingEntity target) {
        BlazingFireBallEntity entityarrow = new BlazingFireBallEntity((EntityType<? extends BlazingFireBallEntity>)((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get()), entity, entity.level(), null);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + (double)target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * (double)0.2f, dz, 2.0f, 12.0f);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(5.0);
        entityarrow.setKnockback(0);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity((Entity)entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f / (RandomSource.create().nextFloat() * 0.5f + 1.0f));
        return entityarrow;
    }
}

