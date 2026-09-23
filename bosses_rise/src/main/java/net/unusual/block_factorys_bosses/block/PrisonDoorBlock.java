/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PrisonDoorBlock
extends Block {
    public static final IntegerProperty DOOR_PART = IntegerProperty.create((String)"blockstate", (int)0, (int)1);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SOUTH = PrisonDoorBlock.box((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0);
    private static final VoxelShape NORTH = PrisonDoorBlock.box((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0);
    private static final VoxelShape EAST = PrisonDoorBlock.box((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0);
    private static final VoxelShape WEST = PrisonDoorBlock.box((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0);
    public static final int HINGE = 0;
    public static final int EDGE = 1;

    public PrisonDoorBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.CHAIN).strength(1.0f, 10.0f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DOOR_PART, Integer.valueOf(1))).setValue(FACING, Direction.NORTH));
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
        return switch ((Direction)state.getValue(FACING)) {
            case Direction.NORTH -> NORTH;
            case Direction.EAST -> EAST;
            case Direction.WEST -> WEST;
            default -> SOUTH;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, DOOR_PART});
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction parallel;
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 2 && level.getBlockState(pos.above(1)).canBeReplaced(context) && level.getBlockState(pos.above(2)).canBeReplaced(context) && level.getBlockState(pos.relative(parallel = context.getHorizontalDirection().getClockWise())).canBeReplaced(context) && level.getBlockState(pos.relative(parallel).above(1)).canBeReplaced(context) && level.getBlockState(pos.relative(parallel).above(2)).canBeReplaced(context)) {
            BlockState state = super.getStateForPlacement(context);
            if (state == null) {
                return null;
            }
            return (BlockState)state.setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                this.preventCreativeDropFromBottomPart(level, pos, state, player);
            } else if ((Integer)state.getValue(DOOR_PART) == 1) {
                PrisonDoorBlock.dropResources((BlockState)state, (Level)level, (BlockPos)pos, null, (Entity)player, (ItemStack)player.getMainHandItem());
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    protected void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        if ((Integer)state.getValue(DOOR_PART) == 1) {
            Direction parallel = ((Direction)state.getValue(FACING)).getCounterClockWise();
            BlockPos hingePos = (BlockPos)this.getHinge(parallel, (LevelAccessor)level, pos).getSecond();
            if (hingePos == null) {
                return;
            }
            level.setBlock(hingePos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, hingePos, Block.getId((BlockState)level.getBlockState(hingePos)));
        }
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if ((Integer)state.getValue(DOOR_PART) == 0) {
            if (facing.getAxis() == Direction.Axis.Y && !facingState.is((Block)this)) {
                return Blocks.AIR.defaultBlockState();
            }
            if (facing == ((Direction)state.getValue(FACING)).getCounterClockWise() && !facingState.is((Block)this)) {
                return Blocks.AIR.defaultBlockState();
            }
        } else {
            Direction parallel = ((Direction)state.getValue(FACING)).getCounterClockWise();
            Vec3i hinge = (Vec3i)this.getHinge(parallel, level, pos).getFirst();
            if (hinge == null) {
                return Blocks.AIR.defaultBlockState();
            }
            int xz = hinge.getX() + hinge.getZ();
            if (xz == 0 ? facing == ((Direction)state.getValue(FACING)).getCounterClockWise() && !facingState.is((Block)this) : facing == ((Direction)state.getValue(FACING)).getClockWise() && !facingState.is((Block)this)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return state;
    }

    protected Pair<Vec3i, BlockPos> getHinge(Direction parallel, LevelAccessor level, BlockPos pos) {
        for (int y = -1; y <= 1; ++y) {
            for (int i = -1; i <= 0; ++i) {
                BlockPos newPos = pos.relative(parallel, i).above(y);
                BlockState parallelState = level.getBlockState(newPos);
                if (!parallelState.is((Block)this) || (Integer)parallelState.getValue(DOOR_PART) != 0) continue;
                return Pair.of(Vec3i.ZERO.relative(parallel, i).above(y), newPos);
            }
        }
        return Pair.of(null, null);
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {
        level.setBlock(pos.above(1), (BlockState)state.setValue(DOOR_PART, Integer.valueOf(0)), 3);
        level.setBlock(pos.above(2), state, 3);
        level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()), state, 3);
        level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()).above(1), state, 3);
        level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()).above(2), state, 3);
    }
}

