package net.neoforged.neoforge.event.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEvent {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;

    public BlockEvent(LevelAccessor level, BlockPos pos, BlockState state) {
        this.level = level;
        this.pos = pos;
        this.state = state;
    }

    public LevelAccessor getLevel() { return level; }
    public BlockPos getPos() { return pos; }
    public BlockState getState() { return state; }

    public static class BreakEvent extends BlockEvent {
        private final Player player;
        private boolean canceled;

        public BreakEvent(LevelAccessor level, BlockPos pos, BlockState state, Player player) {
            super(level, pos, state);
            this.player = player;
        }

        public Player getPlayer() { return player; }
        public void setCanceled(boolean canceled) { this.canceled = canceled; }
        public boolean isCanceled() { return canceled; }
    }

    public static class EntityPlaceEvent extends BlockEvent {
        private final Entity entity;
        private boolean canceled;

        public EntityPlaceEvent(LevelAccessor level, BlockPos pos, BlockState state, Entity entity) {
            super(level, pos, state);
            this.entity = entity;
        }

        public Entity getEntity() { return entity; }
        public void setCanceled(boolean canceled) { this.canceled = canceled; }
        public boolean isCanceled() { return canceled; }
    }
}
