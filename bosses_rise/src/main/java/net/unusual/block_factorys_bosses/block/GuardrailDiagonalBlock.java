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
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GuardrailDiagonalBlock
extends Block {
    private static final VoxelShape EAST_SHAPE = Shapes.or((VoxelShape)GuardrailDiagonalBlock.box((double)0.0, (double)0.0, (double)6.0, (double)4.0, (double)4.0, (double)10.0), (VoxelShape[])new VoxelShape[]{GuardrailDiagonalBlock.box((double)4.0, (double)0.0, (double)6.0, (double)8.0, (double)8.0, (double)10.0), GuardrailDiagonalBlock.box((double)8.0, (double)0.0, (double)6.0, (double)12.0, (double)12.0, (double)10.0), GuardrailDiagonalBlock.box((double)12.0, (double)0.0, (double)6.0, (double)16.0, (double)16.0, (double)10.0)});
    private static final VoxelShape SOUTH_SHAPE = Shapes.or((VoxelShape)GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)0.0, (double)10.0, (double)4.0, (double)4.0), (VoxelShape[])new VoxelShape[]{GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)4.0, (double)10.0, (double)8.0, (double)8.0), GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)8.0, (double)10.0, (double)12.0, (double)12.0), GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)12.0, (double)10.0, (double)16.0, (double)16.0)});
    private static final VoxelShape WEST_SHAPE = Shapes.or((VoxelShape)GuardrailDiagonalBlock.box((double)12.0, (double)0.0, (double)6.0, (double)16.0, (double)4.0, (double)10.0), (VoxelShape[])new VoxelShape[]{GuardrailDiagonalBlock.box((double)8.0, (double)0.0, (double)6.0, (double)12.0, (double)8.0, (double)10.0), GuardrailDiagonalBlock.box((double)4.0, (double)0.0, (double)6.0, (double)8.0, (double)12.0, (double)10.0), GuardrailDiagonalBlock.box((double)0.0, (double)0.0, (double)6.0, (double)4.0, (double)16.0, (double)10.0)});
    private static final VoxelShape NORTH_SHAPE = Shapes.or((VoxelShape)GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)12.0, (double)10.0, (double)4.0, (double)16.0), (VoxelShape[])new VoxelShape[]{GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)8.0, (double)10.0, (double)8.0, (double)12.0), GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)4.0, (double)10.0, (double)12.0, (double)8.0), GuardrailDiagonalBlock.box((double)6.0, (double)0.0, (double)0.0, (double)10.0, (double)16.0, (double)4.0)});
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public GuardrailDiagonalBlock() {
        super(BlockBehaviour.Properties.of().destroyTime(2.5f).ignitedByLava().explosionResistance(2.5f).noOcclusion().sound(SoundType.WOOD));
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch ((Direction)state.getValue(FACING)) {
            case Direction.EAST -> EAST_SHAPE;
            case Direction.SOUTH -> SOUTH_SHAPE;
            case Direction.WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }
}

