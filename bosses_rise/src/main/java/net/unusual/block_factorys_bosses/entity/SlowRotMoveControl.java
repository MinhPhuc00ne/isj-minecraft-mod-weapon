/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.PathType
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlowRotMoveControl
extends MoveControl {
    private float additionalRot;

    public SlowRotMoveControl(Mob mob) {
        super(mob);
    }

    public void tick() {
        if (this.operation == MoveControl.Operation.STRAFE) {
            float f8;
            float f = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float f1 = (float)this.speedModifier * f;
            float f2 = this.strafeForwards;
            float f3 = this.strafeRight;
            float f4 = Mth.sqrt((float)(f2 * f2 + f3 * f3));
            if (f4 < 1.0f) {
                f4 = 1.0f;
            }
            f4 = f1 / f4;
            float f5 = Mth.sin((float)(this.mob.getYRot() * ((float)Math.PI / 180)));
            float f6 = Mth.cos((float)(this.mob.getYRot() * ((float)Math.PI / 180)));
            float f7 = (f2 *= f4) * f6 - (f3 *= f4) * f5;
            if (!this.isWalkable(f7, f8 = f3 * f6 + f2 * f5)) {
                this.strafeForwards = 1.0f;
                this.strafeRight = 0.0f;
            }
            this.mob.setSpeed(f1);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = MoveControl.Operation.WAIT;
        } else if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if (d3 < 2.500000277905201E-7) {
                this.mob.setZza(0.0f);
                if (this.additionalRot > 0.0f) {
                    this.additionalRot -= 0.03f;
                }
                return;
            }
            float f9 = (float)(Mth.atan2((double)d1, (double)d0) * 57.2957763671875) - 90.0f;
            if (this.additionalRot < 1.0f) {
                this.additionalRot += 0.1f;
            }
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 5.0f + this.additionalRot * 5.0f));
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockstate = this.mob.level().getBlockState(blockpos);
            VoxelShape voxelshape = blockstate.getCollisionShape((BlockGetter)this.mob.level(), blockpos);
            if (d2 > (double)this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double)Math.max(1.0f, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double)blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                this.mob.getJumpControl().jump();
                this.operation = MoveControl.Operation.JUMPING;
            }
        } else if (this.operation == MoveControl.Operation.JUMPING) {
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.onGround()) {
                this.operation = MoveControl.Operation.WAIT;
            }
        } else {
            this.mob.setZza(0.0f);
            if (this.additionalRot > 0.0f) {
                this.additionalRot -= 0.03f;
            }
        }
    }

    private boolean isWalkable(float dx, float dz) {
        NodeEvaluator evaluator;
        PathNavigation navigation = this.mob.getNavigation();
        if (navigation != null && (evaluator = navigation.getNodeEvaluator()) != null) {
            BlockPos pos = new BlockPos(Mth.floor((double)(this.mob.getX() + (double)dx)), this.mob.getBlockY(), Mth.floor((double)(this.mob.getZ() + (double)dz)));
            PathType type = evaluator.getPathType(this.mob, pos);
            return type == PathType.WALKABLE;
        }
        return true;
    }
}

