/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.arguments.EntityAnchorArgument$Anchor
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SoulShockwaveEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.procedures.AbstractBossOnEntityTickUpdateProcedure;
import org.jetbrains.annotations.NotNull;

public class UnderworldKnightOnEntityTickUpdateProcedure
extends AbstractBossOnEntityTickUpdateProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, @NotNull UnderworldKnightEntity boss) {
        if (UnderworldKnightOnEntityTickUpdateProcedure.onSpawn(boss, world, 226)) {
            return;
        }
    }

    public static void attackCombo1(@NotNull UnderworldKnightEntity boss, LevelAccessor world) {
        double x = boss.getX();
        double y = boss.getY();
        double z = boss.getZ();
        int attackAnimtime = 158 - boss.getTimer();
        if (attackAnimtime >= 158) {
            boss.lookAtTarget();
        }
        if (attackAnimtime == 60 && boss.heavyCounter < (boss.getHealthRatio() > 0.5 ? 3 : 5)) {
            boss.placeMark(0.5f);
        }
        if (attackAnimtime == 122 || attackAnimtime == 76 || attackAnimtime == 58) {
            double distance = 1.0;
            if (boss.getTarget() != null) {
                boss.lookAtTarget();
                distance = (double)boss.distanceTo((Entity)boss.getTarget()) * 0.08;
            }
            boss.push(boss.getLookAngle().x * distance, 0.3, boss.getLookAngle().z * distance);
            boss.meleeAttack(6.0, 2.5, 1.5);
        } else if (attackAnimtime == 108 || attackAnimtime == 94 || attackAnimtime == 62) {
            double distance = 1.0;
            if (boss.getTarget() != null) {
                boss.lookAtTarget();
                distance = (double)boss.distanceTo((Entity)boss.getTarget()) * 0.08;
            }
            boss.push(boss.getLookAngle().x * distance, 0.3, boss.getLookAngle().z * distance);
            boss.meleeAttack(14.0, 2.5, 1.5);
        } else if (attackAnimtime <= 30) {
            if (attackAnimtime == 30) {
                boss.meleeAttack(8.0, 2.5, 3.0);
            }
            if (attackAnimtime == 29) {
                for (int index0 = 0; index0 < 26; ++index0) {
                    world.addParticle((ParticleOptions)BossesRiseParticleTypes.SOUL_CLOUD.get(), x + boss.getLookAngle().x * 4.0, y + 0.1, z + boss.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.0, Math.random() * 0.3, (Math.random() - 0.5) * 2.0);
                    world.addParticle((ParticleOptions)BossesRiseParticleTypes.BLACK_FALLING_DUST.get(), x + boss.getLookAngle().x * 4.0, y + 0.1, z + boss.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.5, Math.random() * 0.4, (Math.random() - 0.5) * 2.5);
                    if (!(Math.random() < 0.3)) continue;
                    world.addParticle((ParticleOptions)BossesRiseParticleTypes.MAGICAL_DOT.get(), x + boss.getLookAngle().x * 4.0, y + 0.1, z + boss.getLookAngle().z * 4.0, (Math.random() - 0.5) * 2.5, Math.random() * 0.4, (Math.random() - 0.5) * 2.5);
                }
            }
            if (attackAnimtime < 29) {
                double nb = 0.0;
                double nb2 = 2.0 + (double)(18 - attackAnimtime) * 0.1;
                int index1 = 0;
                while ((long)index1 < Math.round(18.0 * nb2)) {
                    double new_x = boss.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
                    double new_z = boss.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
                    ItemStack stack = new ItemStack((ItemLike)world.getBlockState(BlockPos.containing((double)Math.floor(x + new_x), (double)(y - 1.0), (double)Math.floor(z + new_z))).getBlock());
                    boss.forEachNearbyEntity(0.5, new Vec3(new_x, 0.0, new_z), entity -> entity.hurt(boss.damageSources().inWall(), 12.0f));
                    if (stack.getItem() != ItemStack.EMPTY.getItem() && world instanceof ServerLevel) {
                        ServerLevel _level = (ServerLevel)world;
                        _level.sendParticles((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, stack), x + new_x, y + 0.1, z + new_z, 3, 0.0, 0.0, 0.0, 0.05);
                    }
                    nb += nb2;
                    ++index1;
                }
            }
        }
        if (attackAnimtime == 36) {
            boss.lookAtTarget();
        }
    }

    public static void attackJumpspin1(@NotNull UnderworldKnightEntity boss, LevelAccessor world) {
        double x = boss.getX();
        double y = boss.getY();
        double z = boss.getZ();
        int attackAnimtime = 78 - boss.getTimer();
        if (attackAnimtime >= 62) {
            boss.lookAtTarget();
        }
        if (attackAnimtime == 62) {
            boss.push(0.0, boss.isInWaterOrBubble() ? 2.1 : 1.2, 0.0);
        }
        if (attackAnimtime < 62 && attackAnimtime > 41 && boss.getTarget() != null) {
            boss.push((boss.getTarget().getX() - x) * 0.05, 0.0, (boss.getTarget().getZ() - z) * 0.05);
            if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                for (int sx = -3; sx <= 3; ++sx) {
                    for (int sy = 3; sy <= 6; ++sy) {
                        for (int sz = -3; sz <= 3; ++sz) {
                            if (!(world.getBlockState(BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))).getDestroySpeed((BlockGetter)world, BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))) > 0.0f) || !(world.getBlockState(BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))).getDestroySpeed((BlockGetter)world, BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz))) < 50.0f)) continue;
                            world.setBlock(BlockPos.containing((double)(Math.floor(x) + (double)sx), (double)(Math.floor(y) + (double)sy), (double)(Math.floor(z) + (double)sz)), Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
        if (attackAnimtime == 41) {
            boss.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, y - 10.0, z));
            if (world instanceof ServerLevel) {
                ServerLevel level = (ServerLevel)world;
                SoulShockwaveEntity shockwave = new SoulShockwaveEntity((EntityType<? extends SoulShockwaveEntity>)((EntityType)BossesRiseEntities.SOUL_SHOCKWAVE.get()), (Level)level);
                shockwave.setOwner((Entity)boss);
                shockwave.setBaseDamage(17.5);
                shockwave.setSilent(true);
                shockwave.setPos(x, boss.getEyeY() - 0.1, z);
                shockwave.shoot(boss.getLookAngle().x, boss.getLookAngle().y, boss.getLookAngle().z, 1.0f, 0.0f);
                level.addFreshEntity((Entity)shockwave);
            }
            if (Math.random() < 0.2) {
                boss.playSound((SoundEvent)BossesRiseSounds.KNIGHT_ATTACK_VOICELINE.value());
            }
        }
        if (attackAnimtime == 19) {
            boss.push(0.0, -1.0, 0.0);
        }
    }
}

