/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
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
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.DragonGuardSwordEntity;

public class DragonGuardSwordOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        block25: {
            int n;
            block26: {
                int n2;
                int n3;
                int n4;
                if (entity == null) {
                    return;
                }
                if (entity instanceof DragonGuardSwordEntity) {
                    DragonGuardSwordEntity _datEntI = (DragonGuardSwordEntity)entity;
                    n4 = (Integer)_datEntI.getEntityData().get(DragonGuardSwordEntity.DATA_attack_animtime);
                } else {
                    n4 = 0;
                }
                if (n4 <= 0) break block26;
                if (entity instanceof DragonGuardSwordEntity) {
                    DragonGuardSwordEntity _datEntI = (DragonGuardSwordEntity)entity;
                    n3 = (Integer)_datEntI.getEntityData().get(DragonGuardSwordEntity.DATA_attack_animtime);
                } else {
                    n3 = 0;
                }
                if (n3 == 12) {
                    Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        LivingEntity livingEntity;
                        if (entity instanceof Mob) {
                            Mob _mobEnt = (Mob)entity;
                            livingEntity = _mobEnt.getTarget();
                        } else {
                            livingEntity = null;
                        }
                        if (entityiterator != livingEntity) continue;
                        if (entity.isAlive()) {
                            entityiterator.hurt(entity.damageSources().mobAttack(entity instanceof LivingEntity _le ? _le : null), (float)(entity instanceof LivingEntity _le && _le.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _le.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0.0));
                        }
                        entityiterator.setDeltaMovement(new Vec3(entityiterator.getDeltaMovement().x() + entity.getLookAngle().x * 0.2, entityiterator.getDeltaMovement().y() + 0.1, entityiterator.getDeltaMovement().z() + entity.getLookAngle().z * 0.2));
                        break;
                    }
                }
                if (!(entity instanceof DragonGuardSwordEntity)) break block25;
                DragonGuardSwordEntity _datEntSetI = (DragonGuardSwordEntity)entity;
                SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                if (entity instanceof DragonGuardSwordEntity) {
                    DragonGuardSwordEntity _datEntI = (DragonGuardSwordEntity)entity;
                    n2 = (Integer)_datEntI.getEntityData().get(DragonGuardSwordEntity.DATA_attack_animtime);
                } else {
                    n2 = 0;
                }
                synchedEntityData.set(DragonGuardSwordEntity.DATA_attack_animtime, (n2 - 1));
                break block25;
            }
            if (entity instanceof DragonGuardSwordEntity) {
                DragonGuardSwordEntity _datEntI = (DragonGuardSwordEntity)entity;
                n = (Integer)_datEntI.getEntityData().get(DragonGuardSwordEntity.DATA_attack_cooldown);
            } else {
                n = 0;
            }
            if (n >= 40) {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.75), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    DragonGuardSwordEntity _datEntSetI;
                    LivingEntity livingEntity;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity = _mobEnt.getTarget();
                    } else {
                        livingEntity = null;
                    }
                    if (entityiterator != livingEntity) continue;
                    if (entity instanceof DragonGuardSwordEntity) {
                        _datEntSetI = (DragonGuardSwordEntity)entity;
                        _datEntSetI.getEntityData().set(DragonGuardSwordEntity.DATA_attack_cooldown, 0);
                    }
                    if (!(entity instanceof DragonGuardSwordEntity)) break;
                    _datEntSetI = (DragonGuardSwordEntity)entity;
                    _datEntSetI.getEntityData().set(DragonGuardSwordEntity.DATA_attack_animtime, 20);
                    break;
                }
            } else {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    int n5;
                    LivingEntity livingEntity;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity = _mobEnt.getTarget();
                    } else {
                        livingEntity = null;
                    }
                    if (entityiterator != livingEntity) continue;
                    if (!(entity instanceof DragonGuardSwordEntity)) break;
                    DragonGuardSwordEntity _datEntSetI = (DragonGuardSwordEntity)entity;
                    SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                    if (entity instanceof DragonGuardSwordEntity) {
                        DragonGuardSwordEntity _datEntI = (DragonGuardSwordEntity)entity;
                        n5 = (Integer)_datEntI.getEntityData().get(DragonGuardSwordEntity.DATA_attack_cooldown);
                    } else {
                        n5 = 0;
                    }
                    synchedEntityData.set(DragonGuardSwordEntity.DATA_attack_cooldown, (n5 + 1));
                    break;
                }
            }
        }
    }
}

