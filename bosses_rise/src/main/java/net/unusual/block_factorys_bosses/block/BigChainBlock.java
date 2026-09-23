/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigChainBlock
extends Block {
    private static final VoxelShape UNROTATED_BOX = Shapes.or((VoxelShape)BigChainBlock.box((double)7.0, (double)-2.0, (double)4.0, (double)9.0, (double)18.0, (double)12.0), (VoxelShape)BigChainBlock.box((double)7.0, (double)0.0, (double)6.0, (double)9.0, (double)16.0, (double)10.0));
    private static final VoxelShape ROTATED_BOX = Shapes.or((VoxelShape)BigChainBlock.box((double)4.0, (double)-2.0, (double)7.0, (double)12.0, (double)18.0, (double)9.0), (VoxelShape)BigChainBlock.box((double)6.0, (double)0.0, (double)7.0, (double)10.0, (double)16.0, (double)9.0));
    public static final IntegerProperty ROTATION = IntegerProperty.create((String)"blockstate", (int)1, (int)2);
    public static final int UNROTATED = 1;
    public static final int ROTATED = 2;

    public BigChainBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.HEAVY_CORE).strength(1.2f, 10.0f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if ((Integer)state.getValue(ROTATION) == 1) {
            return UNROTATED_BOX;
        }
        return ROTATED_BOX;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{ROTATION});
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (facing == Direction.UP && facingState.is((Block)this)) {
            int rot = (Integer)state.getValue(ROTATION);
            if (((Integer)facingState.getValue(ROTATION)).equals(rot)) {
                return (BlockState)state.setValue(ROTATION, Integer.valueOf(rot == 1 ? 2 : 1));
            }
        }
        return state;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState below;
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        int pref = 0;
        BlockState above = level.getBlockState(pos.above());
        if (above.is((Block)this)) {
            int n = pref = (Integer)above.getValue(ROTATION) == 1 ? 2 : 1;
        }
        if (pref == 0 && (below = level.getBlockState(pos.below())).is((Block)this)) {
            int n = pref = (Integer)below.getValue(ROTATION) == 1 ? 2 : 1;
        }
        if (pref != 0) {
            return (BlockState)this.defaultBlockState().setValue(ROTATION, Integer.valueOf(pref));
        }
        return (BlockState)this.defaultBlockState().setValue(ROTATION, Integer.valueOf(context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? 2 : 1));
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return rot == Rotation.CLOCKWISE_180 || rot == Rotation.NONE ? state : (BlockState)state.setValue(ROTATION, Integer.valueOf((Integer)state.getValue(ROTATION) == 2 ? 1 : 2));
    }
}

