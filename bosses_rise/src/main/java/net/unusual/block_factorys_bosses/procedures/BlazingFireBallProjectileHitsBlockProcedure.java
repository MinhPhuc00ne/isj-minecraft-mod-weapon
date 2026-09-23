/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.level.LevelAccessor
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;

public class BlazingFireBallProjectileHitsBlockProcedure {
    public static void execute(LevelAccessor world, Entity immediatesourceentity) {
        if (immediatesourceentity == null) {
            return;
        }
        if (world instanceof ServerLevel) {
            ServerLevel _level = (ServerLevel)world;
            Entity entityToSpawn = ((EntityType)BossesRiseEntities.FIRE_AREA.get()).spawn(_level, BlockPos.containing((double)immediatesourceentity.getX(), (double)immediatesourceentity.getY(), (double)immediatesourceentity.getZ()), MobSpawnType.MOB_SUMMONED);
            if (entityToSpawn != null) {
                entityToSpawn.setYRot(world.getRandom().nextFloat() * 360.0f);
                _level.explode(entityToSpawn, entityToSpawn.position().x, entityToSpawn.position().y, entityToSpawn.position().z, 2.0f, true, Level.ExplosionInteraction.NONE);
            }
        }
        if (!immediatesourceentity.level().isClientSide()) {
            immediatesourceentity.discard();
        }
    }
}

