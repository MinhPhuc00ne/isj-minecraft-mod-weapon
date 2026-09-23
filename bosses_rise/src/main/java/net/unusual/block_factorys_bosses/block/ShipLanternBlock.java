/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.SupportType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShipLanternBlock
extends Block {
    private static final VoxelShape SHAPE = ShipLanternBlock.box((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)15.0, (double)14.0);

    public ShipLanternBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(3.5f, 6.0f).lightLevel(state -> 14).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy((BlockGetter)level, pos.below(), Direction.UP, SupportType.CENTER);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (facing == Direction.DOWN && !this.canSurvive(state, (LevelReader)level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!this.canSurvive(this.defaultBlockState(), (LevelReader)context.getLevel(), context.getClickedPos())) {
            return null;
        }
        return super.getStateForPlacement(context);
    }
}

