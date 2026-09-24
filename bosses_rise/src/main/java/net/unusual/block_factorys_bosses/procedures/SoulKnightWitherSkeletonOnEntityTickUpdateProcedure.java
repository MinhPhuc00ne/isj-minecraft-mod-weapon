/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;

public class SoulKnightWitherSkeletonOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        int n;
        LivingEntity livingEntity;
        if (entity == null) {
            return;
        }
        if (entity instanceof Mob) {
            Mob _mobEnt = (Mob)entity;
            livingEntity = _mobEnt.getTarget();
        } else {
            livingEntity = null;
        }
        if (livingEntity instanceof UnderworldKnightEntity && entity instanceof Mob) {
            Mob _entity = (Mob)entity;
            _entity.setTarget(null);
        }
        if (entity instanceof SoulKnightWitherSkeletonEntity) {
            SoulKnightWitherSkeletonEntity _datEntI = (SoulKnightWitherSkeletonEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime);
        } else {
            n = 0;
        }
        if (n > 0) {
            int n2;
            if (entity instanceof SoulKnightWitherSkeletonEntity) {
                SoulKnightWitherSkeletonEntity _datEntI = (SoulKnightWitherSkeletonEntity)entity;
                n2 = (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime);
            } else {
                n2 = 0;
            }
            if (n2 == 10) {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    LivingEntity livingEntity2;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity2 = _mobEnt.getTarget();
                    } else {
                        livingEntity2 = null;
                    }
                    if (entityiterator != livingEntity2) continue;
                    if (entity.isAlive()) {
                        LivingEntity _entity;
                        LivingEntity _livEnt14;
                        entityiterator.hurt(entity.damageSources().mobAttack(entity instanceof LivingEntity _le ? _le : null), (float)(entity instanceof LivingEntity _le && _le.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _le.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0.0));
                        if (!(entity instanceof LivingEntity && (_livEnt14 = (LivingEntity)entity).isBlocking() || !(entityiterator instanceof LivingEntity) || (_entity = (LivingEntity)entityiterator).level().isClientSide())) {
                            _entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                        }
                    }
                    entityiterator.setDeltaMovement(new Vec3(entityiterator.getDeltaMovement().x() + entity.getLookAngle().x * 0.2, entityiterator.getDeltaMovement().y() + 0.1, entityiterator.getDeltaMovement().z() + entity.getLookAngle().z * 0.2));
                    break;
                }
            }
            if (entity instanceof SoulKnightWitherSkeletonEntity) {
                int n3;
                SoulKnightWitherSkeletonEntity _datEntSetI = (SoulKnightWitherSkeletonEntity)entity;
                SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                if (entity instanceof SoulKnightWitherSkeletonEntity) {
                    SoulKnightWitherSkeletonEntity _datEntI = (SoulKnightWitherSkeletonEntity)entity;
                    n3 = (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime);
                } else {
                    n3 = 0;
                }
                synchedEntityData.set(SoulKnightWitherSkeletonEntity.DATA_attack_animtime, (n3 - 1));
            }
        } else {
            int n4;
            if (entity instanceof SoulKnightWitherSkeletonEntity) {
                SoulKnightWitherSkeletonEntity _datEntI = (SoulKnightWitherSkeletonEntity)entity;
                n4 = (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown);
            } else {
                n4 = 0;
            }
            if (n4 >= 30) {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.75), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    SoulKnightWitherSkeletonEntity _datEntSetI;
                    LivingEntity livingEntity3;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity3 = _mobEnt.getTarget();
                    } else {
                        livingEntity3 = null;
                    }
                    if (entityiterator != livingEntity3) continue;
                    if (entity instanceof SoulKnightWitherSkeletonEntity) {
                        _datEntSetI = (SoulKnightWitherSkeletonEntity)entity;
                        _datEntSetI.getEntityData().set(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown, 0);
                    }
                    if (!(entity instanceof SoulKnightWitherSkeletonEntity)) break;
                    _datEntSetI = (SoulKnightWitherSkeletonEntity)entity;
                    _datEntSetI.getEntityData().set(SoulKnightWitherSkeletonEntity.DATA_attack_animtime, 20);
                    break;
                }
            } else {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    int n5;
                    LivingEntity livingEntity4;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity4 = _mobEnt.getTarget();
                    } else {
                        livingEntity4 = null;
                    }
                    if (entityiterator != livingEntity4) continue;
                    if (!(entity instanceof SoulKnightWitherSkeletonEntity)) break;
                    SoulKnightWitherSkeletonEntity _datEntSetI = (SoulKnightWitherSkeletonEntity)entity;
                    SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                    if (entity instanceof SoulKnightWitherSkeletonEntity) {
                        SoulKnightWitherSkeletonEntity _datEntI = (SoulKnightWitherSkeletonEntity)entity;
                        n5 = (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown);
                    } else {
                        n5 = 0;
                    }
                    synchedEntityData.set(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown, (n5 + 1));
                    break;
                }
            }
        }
        if (Math.random() < 0.05 && world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            _level.sendParticles((ParticleOptions)ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
        }
    }
}

