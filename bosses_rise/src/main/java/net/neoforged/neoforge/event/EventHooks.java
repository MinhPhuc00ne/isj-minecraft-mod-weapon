package net.neoforged.neoforge.event;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;

public class EventHooks {
    public static SpawnGroupData finalizeMobSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData) {
        return mob.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    public static boolean onProjectileImpact(Projectile projectile, HitResult hitResult) {
        return false;
    }
}
