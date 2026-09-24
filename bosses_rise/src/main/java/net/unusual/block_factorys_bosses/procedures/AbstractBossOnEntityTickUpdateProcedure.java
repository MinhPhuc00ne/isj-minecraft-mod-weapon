/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageTypes
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.AbstractBossEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import org.jetbrains.annotations.NotNull;

public class AbstractBossOnEntityTickUpdateProcedure {
    public static Direction getDirection(BlockState state) {
        EnumProperty _ep;
        Property property = state.getBlock().getStateDefinition().getProperty("facing");
        if (property instanceof DirectionProperty) {
            DirectionProperty _dp = (DirectionProperty)property;
            return (Direction)state.getValue((Property)_dp);
        }
        property = state.getBlock().getStateDefinition().getProperty("axis");
        return property instanceof EnumProperty && (_ep = (EnumProperty)property).getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis)((Direction.Axis)state.getValue((Property)_ep)), (Direction.AxisDirection)Direction.AxisDirection.POSITIVE) : Direction.NORTH;
    }

    public static void updateRotations(@NotNull AbstractBossEntity boss) {
        boss.setYRot((float)net.unusual.block_factorys_bosses.util.EntityPersistentData.get(boss).getDouble("angle"));
        boss.setXRot(0.0f);
        boss.setYBodyRot(boss.getYRot());
        boss.setYHeadRot(boss.getYRot());
        boss.yRotO = boss.getYRot();
        boss.xRotO = boss.getXRot();
        boss.yBodyRotO = boss.getYRot();
        boss.yHeadRotO = boss.getYRot();
    }

    protected static boolean onDying(@NotNull AbstractBossEntity boss, LevelAccessor world) {
        SynchedEntityData entityData = boss.getEntityData();
        int dieAnimtime = (Integer)entityData.get(AbstractBossEntity.DATA_DIE_ANIMTIME);
        if (dieAnimtime > 0) {
            boss.setDeltaMovement(new Vec3(0.0, -1.0, 0.0));
            entityData.set(AbstractBossEntity.DATA_ATTACK_ANIMTIME, 0);
            entityData.set(AbstractBossEntity.DATA_HIT_ANIMTIME, 0);
            entityData.set(AbstractBossEntity.DATA_DIE_ANIMTIME, (--dieAnimtime));
            if (dieAnimtime == 1) {
                boss.simulatePlayerKill();
            }
            return true;
        }
        return false;
    }

    protected static void incrementAttackCooldown(@NotNull AbstractBossEntity boss, double aabbSize) {
        if (boss.isTargetNear(aabbSize)) {
            SynchedEntityData entityData = boss.getEntityData();
            entityData.set(AbstractBossEntity.DATA_ATTACK_COOLDOWN, ((Integer)entityData.get(AbstractBossEntity.DATA_ATTACK_COOLDOWN) + 1));
        }
    }

    protected static boolean onSpawn(@NotNull AbstractBossEntity boss, LevelAccessor world, int startSpawnAnimtime) {
        SynchedEntityData entityData = boss.getEntityData();
        int spawnAnimtime = (Integer)entityData.get(AbstractBossEntity.DATA_SPAWN_ANIMTIME);
        if (spawnAnimtime <= 0) {
            return false;
        }
        CompoundTag persistentData = net.unusual.block_factorys_bosses.util.EntityPersistentData.get(boss);
        double x = boss.getX();
        double y = boss.getY();
        double z = boss.getZ();
        boss.setHealth((float)boss.getAttributeValue(Attributes.MAX_HEALTH));
        boss.setDeltaMovement(Vec3.ZERO);
        if (!world.isClientSide() && spawnAnimtime == startSpawnAnimtime) {
            boss.forEachNearbyPlayer(32.0, player -> {
                double xOffset = Math.abs(x - player.getX()) + Math.abs(z - player.getZ());
                player.teleportTo(x + xOffset * 0.5, y, z + (Math.random() - 0.5) * 4.0);
                double angle = persistentData.getDouble("angle");
                double random = (Math.random() - 0.5) * 4.0;
                double tpX = 0.0;
                double tpZ = 0.0;
                if (angle == 0.0) {
                    tpX = random;
                    tpZ = xOffset * 0.75;
                } else if (angle == 180.0) {
                    tpX = random;
                    tpZ = xOffset * -0.75;
                } else if (angle == -90.0) {
                    tpX = xOffset * 0.75;
                    tpZ = random;
                } else if (angle == 90.0) {
                    tpX = xOffset * -0.75;
                    tpZ = random;
                }
                player.teleportTo(x + tpX, y, z + tpZ);
                BlockPos blockPos = BlockPos.containing((double)x, (double)y, (double)z);
                if (world.getBlockState(blockPos).getBlock() == BossesRiseBlocks.BOSS_SPAWNER.get()) {
                    world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                }
            });
        }
        AbstractBossOnEntityTickUpdateProcedure.updateRotations(boss);
        if (spawnAnimtime > 10 && spawnAnimtime <= startSpawnAnimtime) {
            boss.forEachNearbyPlayer(32.0, player -> {
                player.setDeltaMovement(Vec3.ZERO);
                player.setYRot((float)(persistentData.getDouble("angle") - 180.0));
                player.setXRot(0.0f);
                player.setYBodyRot(player.getYRot());
                player.setYHeadRot(player.getYRot());
                player.yRotO = player.getYRot();
                player.xRotO = player.getXRot();
                player.yBodyRotO = player.getYRot();
                player.yHeadRotO = player.getYRot();
            });
        }
        entityData.set(AbstractBossEntity.DATA_SPAWN_ANIMTIME, (spawnAnimtime - 1));
        return true;
    }
}

