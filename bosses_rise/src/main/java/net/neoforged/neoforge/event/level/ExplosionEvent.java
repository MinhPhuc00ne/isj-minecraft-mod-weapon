package net.neoforged.neoforge.event.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import java.util.ArrayList;
import java.util.List;

public class ExplosionEvent {
    public static class Detonate {
        private final Level level;
        private final Explosion explosion;
        private final List<Entity> entityList;
        private final List<BlockPos> blockList = new ArrayList<>();

        public Detonate(Level level, Explosion explosion, List<Entity> entityList) {
            this.level = level;
            this.explosion = explosion;
            this.entityList = entityList;
        }

        public Level getLevel() { return level; }
        public Explosion getExplosion() { return explosion; }
        public List<Entity> getAffectedEntities() { return entityList; }
        public List<BlockPos> getAffectedBlocks() { return blockList; }
    }
}
