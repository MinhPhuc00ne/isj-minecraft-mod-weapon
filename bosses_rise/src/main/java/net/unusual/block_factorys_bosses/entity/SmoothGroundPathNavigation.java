/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmoothGroundPathNavigation
extends GroundPathNavigation {
    public SmoothGroundPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    protected void followThePath() {
        int maxIndex;
        Vec3 currentPos = this.getTempMobPos();
        if (this.path == null) {
            return;
        }
        int nextIndex = this.path.getNextNodeIndex();
        if (nextIndex < (maxIndex = this.path.getNodeCount())) {
            Node nextNode = this.path.getNode(nextIndex);
            Vec3 nextNodePos = new Vec3((double)nextNode.x + 0.5, (double)nextNode.y, (double)nextNode.z + 0.5);
            double dx = nextNodePos.x - currentPos.x;
            double dz = nextNodePos.z - currentPos.z;
            double distance = dx * dx + dz * dz;
            boolean advanced = false;
            if (distance < 0.25) {
                this.path.advance();
                advanced = true;
            }
            double targetAngle = Math.atan2(dz, dx) * 57.2957763671875 - 90.0;
            double currentAngle = this.mob.getYRot();
            double angleDiff = Mth.wrapDegrees((double)(targetAngle - currentAngle));
            double maxTurn = 6.0;
            double clampedTurn = Mth.clamp((double)angleDiff, (double)(-maxTurn), (double)maxTurn);
            this.mob.setYRot((float)(currentAngle + clampedTurn));
            this.mob.yBodyRot = this.mob.getYRot();
            this.mob.yHeadRot = this.mob.getYRot();
            float f = this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75f ? this.mob.getBbWidth() / 2.0f : 0.75f - this.mob.getBbWidth() / 2.0f;
            if (!advanced && (this.isFlag(this.path) || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(currentPos))) {
                this.path.advance();
            }
            this.doStuckDetection(currentPos);
        }
    }

    private boolean isFlag(Path path) {
        BlockPos nextPos = path.getNextNodePos();
        double x = Math.abs(this.mob.getX() - ((double)nextPos.getX() + (double)((int)(this.mob.getBbWidth() + 1.0f)) / 2.0));
        double y = Math.abs(this.mob.getY() - (double)nextPos.getY());
        double z = Math.abs(this.mob.getZ() - ((double)nextPos.getZ() + (double)((int)(this.mob.getBbWidth() + 1.0f)) / 2.0));
        return x <= (double)this.maxDistanceToWaypoint && z <= (double)this.maxDistanceToWaypoint && y < 1.0;
    }
}

