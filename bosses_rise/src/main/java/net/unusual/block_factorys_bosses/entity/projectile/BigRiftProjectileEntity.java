/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.EntityHitResult
 */
package net.unusual.block_factorys_bosses.entity.projectile;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.unusual.block_factorys_bosses.entity.projectile.RiftProjectileEntity;

public class BigRiftProjectileEntity
extends RiftProjectileEntity {
    public BigRiftProjectileEntity(EntityType<? extends BigRiftProjectileEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity entity2 = (LivingEntity)entity;
            if (this.getOwner() != entity2) {
                entity2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            }
        }
    }
}

