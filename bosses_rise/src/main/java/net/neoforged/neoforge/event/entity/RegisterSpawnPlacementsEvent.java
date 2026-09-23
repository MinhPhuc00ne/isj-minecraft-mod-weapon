package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class RegisterSpawnPlacementsEvent {
    public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate, Operation operation) {
    }

    public enum Operation {
        AND, OR, REPLACE
    }
}
