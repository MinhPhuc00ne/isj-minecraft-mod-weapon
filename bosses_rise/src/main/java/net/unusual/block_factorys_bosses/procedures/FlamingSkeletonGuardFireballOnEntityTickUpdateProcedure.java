/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardFireballEntity;
import net.unusual.block_factorys_bosses.entity.projectile.BlazingFireBallEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

public class FlamingSkeletonGuardFireballOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        int n;
        int n2;
        FlamingSkeletonGuardFireballEntity _datEntI;
        if (entity == null) {
            return;
        }
        if (Math.random() < 0.05) {
            if (world instanceof ServerLevel _level) {
                _level.sendParticles((ParticleOptions)ParticleTypes.FLAME, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
            }
        } else if (Math.random() < 0.1 && world instanceof ServerLevel _level) {
            _level.sendParticles((ParticleOptions)ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
        }
        if (entity instanceof FlamingSkeletonGuardFireballEntity) {
            _datEntI = (FlamingSkeletonGuardFireballEntity)entity;
            n2 = (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime);
        } else {
            n2 = 0;
        }
        if (n2 > 0) {
            int n3;
            if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                FlamingSkeletonGuardFireballEntity _datEntI2 = (FlamingSkeletonGuardFireballEntity)entity;
                n3 = (Integer)_datEntI2.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime);
            } else {
                n3 = 0;
            }
            if (n3 == 19) {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.5), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    Entity _shootFrom;
                    Level projectileLevel;
                    LivingEntity livingEntity;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity = _mobEnt.getTarget();
                    } else {
                        livingEntity = null;
                    }
                    if (entityiterator != livingEntity) continue;
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY() + (double)entity.getBbHeight() * 0.5, entityiterator.getZ()));
                    if (world instanceof Level) {
                        Level _level = (Level)world;
                        if (!_level.isClientSide()) {
                            _level.playSound(null, BlockPos.containing((double)entity.getX(), (double)entity.getY(), (double)entity.getZ()), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0f, (float)((double)Mth.nextInt((RandomSource)RandomSource.create(), (int)8, (int)12) * 0.1));
                        } else {
                            _level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 1.0f, (float)((double)Mth.nextInt((RandomSource)RandomSource.create(), (int)8, (int)12) * 0.1), false);
                        }
                    }
                    if (!entity.isAlive() || (projectileLevel = (_shootFrom = entity).level()).isClientSide()) break;
                    Projectile _entityToSpawn = new Object(){

                        public Projectile getArrow(Level level, Entity shooter, float damage, final int knockback, final byte piercing) {
                            BlazingFireBallEntity entityToSpawn = new BlazingFireBallEntity((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get(), level){

                                public byte getPierceLevel() {
                                    return piercing;
                                }

                                @Override
                                protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
                                    if (knockback > 0) {
                                        double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                                        Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double)knockback * 0.6 * d1);
                                        if (vec3.lengthSqr() > 0.0) {
                                            livingEntity.push(vec3.x, 0.1, vec3.z);
                                        }
                                    }
                                }
                            };
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.setBaseDamage(damage);
                            entityToSpawn.setSilent(true);
                            return entityToSpawn;
                        }
                    }.getArrow(projectileLevel, entity, 1.0f, 1, (byte)0);
                    _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                    _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1.5f, 3.0f);
                    projectileLevel.addFreshEntity((Entity)_entityToSpawn);
                    break;
                }
            }
            if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                int n4;
                FlamingSkeletonGuardFireballEntity _datEntSetI = (FlamingSkeletonGuardFireballEntity)entity;
                SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                    FlamingSkeletonGuardFireballEntity _datEntI3 = (FlamingSkeletonGuardFireballEntity)entity;
                    n4 = (Integer)_datEntI3.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime);
                } else {
                    n4 = 0;
                }
                synchedEntityData.set(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime, (n4 - 1));
            }
        } else {
            int n5;
            if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                FlamingSkeletonGuardFireballEntity _datEntI4 = (FlamingSkeletonGuardFireballEntity)entity;
                n5 = (Integer)_datEntI4.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown);
            } else {
                n5 = 0;
            }
            if (n5 >= 70) {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    FlamingSkeletonGuardFireballEntity _datEntSetI;
                    LivingEntity livingEntity;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity = _mobEnt.getTarget();
                    } else {
                        livingEntity = null;
                    }
                    if (entityiterator != livingEntity) continue;
                    if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                        _datEntSetI = (FlamingSkeletonGuardFireballEntity)entity;
                        _datEntSetI.getEntityData().set(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown, 0);
                    }
                    if (!(entity instanceof FlamingSkeletonGuardFireballEntity)) break;
                    _datEntSetI = (FlamingSkeletonGuardFireballEntity)entity;
                    _datEntSetI.getEntityData().set(FlamingSkeletonGuardFireballEntity.DATA_attack_animtime, 25);
                    break;
                }
            } else {
                Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16.0), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    int n6;
                    LivingEntity livingEntity;
                    if (entity instanceof Mob) {
                        Mob _mobEnt = (Mob)entity;
                        livingEntity = _mobEnt.getTarget();
                    } else {
                        livingEntity = null;
                    }
                    if (entityiterator != livingEntity) continue;
                    if (!(entity instanceof FlamingSkeletonGuardFireballEntity)) break;
                    FlamingSkeletonGuardFireballEntity _datEntSetI = (FlamingSkeletonGuardFireballEntity)entity;
                    SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
                    if (entity instanceof FlamingSkeletonGuardFireballEntity) {
                        FlamingSkeletonGuardFireballEntity _datEntI5 = (FlamingSkeletonGuardFireballEntity)entity;
                        n6 = (Integer)_datEntI5.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown);
                    } else {
                        n6 = 0;
                    }
                    synchedEntityData.set(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown, (n6 + 1));
                    break;
                }
            }
        }
        if (entity instanceof FlamingSkeletonGuardFireballEntity) {
            _datEntI = (FlamingSkeletonGuardFireballEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(FlamingSkeletonGuardFireballEntity.DATA_attack_cooldown);
        } else {
            n = 0;
        }
        if (n == 50) {
            ServerLevel _level;
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)ParticleTypes.FLAME, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.4, 0.4, 0.4, 0.0);
            }
            if (world instanceof ServerLevel) {
                _level = (ServerLevel)world;
                _level.sendParticles((ParticleOptions)ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 10, 0.4, 0.4, 0.4, 0.0);
            }
        }
    }
}

