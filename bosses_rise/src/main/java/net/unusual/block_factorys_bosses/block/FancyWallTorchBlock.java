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
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
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
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.block;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FancyWallTorchBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SOUTH = Shapes.or((VoxelShape)FancyWallTorchBlock.box((double)7.0, (double)-3.5, (double)3.0, (double)9.0, (double)9.5, (double)5.0), (VoxelShape[])new VoxelShape[]{FancyWallTorchBlock.box((double)5.0, (double)0.0, (double)0.0, (double)11.0, (double)6.0, (double)2.0), FancyWallTorchBlock.box((double)6.5, (double)-0.5, (double)2.5, (double)9.5, (double)3.5, (double)5.5), FancyWallTorchBlock.box((double)6.0, (double)5.5, (double)2.0, (double)10.0, (double)9.5, (double)6.0)});
    private static final VoxelShape NORTH = Shapes.or((VoxelShape)FancyWallTorchBlock.box((double)7.0, (double)-3.5, (double)11.0, (double)9.0, (double)9.5, (double)13.0), (VoxelShape[])new VoxelShape[]{FancyWallTorchBlock.box((double)5.0, (double)0.0, (double)14.0, (double)11.0, (double)6.0, (double)16.0), FancyWallTorchBlock.box((double)6.5, (double)-0.5, (double)10.5, (double)9.5, (double)3.5, (double)13.5), FancyWallTorchBlock.box((double)6.0, (double)5.5, (double)10.0, (double)10.0, (double)9.5, (double)14.0)});
    private static final VoxelShape EAST = Shapes.or((VoxelShape)FancyWallTorchBlock.box((double)3.0, (double)-3.5, (double)7.0, (double)5.0, (double)9.5, (double)9.0), (VoxelShape[])new VoxelShape[]{FancyWallTorchBlock.box((double)0.0, (double)0.0, (double)5.0, (double)2.0, (double)6.0, (double)11.0), FancyWallTorchBlock.box((double)2.5, (double)-0.5, (double)6.5, (double)5.5, (double)3.5, (double)9.5), FancyWallTorchBlock.box((double)2.0, (double)5.5, (double)6.0, (double)6.0, (double)9.5, (double)10.0)});
    private static final VoxelShape WEST = Shapes.or((VoxelShape)FancyWallTorchBlock.box((double)11.0, (double)-3.5, (double)7.0, (double)13.0, (double)9.5, (double)9.0), (VoxelShape[])new VoxelShape[]{FancyWallTorchBlock.box((double)14.0, (double)0.0, (double)5.0, (double)16.0, (double)6.0, (double)11.0), FancyWallTorchBlock.box((double)10.5, (double)-0.5, (double)6.5, (double)13.5, (double)3.5, (double)9.5), FancyWallTorchBlock.box((double)10.0, (double)5.5, (double)6.0, (double)14.0, (double)9.5, (double)10.0)});
    private final Supplier<SimpleParticleType> particleType;

    public FancyWallTorchBlock(Supplier<SimpleParticleType> particleType) {
        super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f, 10.0f).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
        this.particleType = particleType;
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
        builder.add(new Property[]{FACING});
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace().getAxis() == Direction.Axis.Y) {
            return null;
        }
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy((BlockGetter)level, blockpos, direction);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        return !state.canSurvive((LevelReader)world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = (Direction)state.getValue(FACING);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (direction == Direction.WEST) {
            level.addParticle((ParticleOptions)this.particleType.get(), (double)x + 0.68, (double)y + 0.64, (double)z + 0.5, 0.0, 0.0, 0.0);
            level.addParticle((ParticleOptions)ParticleTypes.SMOKE, (double)x + 0.68, (double)y + 0.64, (double)z + 0.5, 0.0, 0.0, 0.0);
        } else if (direction == Direction.EAST) {
            level.addParticle((ParticleOptions)this.particleType.get(), (double)x + 0.32, (double)y + 0.64, (double)z + 0.5, 0.0, 0.0, 0.0);
            level.addParticle((ParticleOptions)ParticleTypes.SMOKE, (double)x + 0.32, (double)y + 0.64, (double)z + 0.5, 0.0, 0.0, 0.0);
        } else if (direction == Direction.NORTH) {
            level.addParticle((ParticleOptions)this.particleType.get(), (double)x + 0.5, (double)y + 0.64, (double)z + 0.68, 0.0, 0.0, 0.0);
            level.addParticle((ParticleOptions)ParticleTypes.SMOKE, (double)x + 0.5, (double)y + 0.64, (double)z + 0.68, 0.0, 0.0, 0.0);
        } else if (direction == Direction.SOUTH) {
            level.addParticle((ParticleOptions)this.particleType.get(), (double)x + 0.5, (double)y + 0.64, (double)z + 0.32, 0.0, 0.0, 0.0);
            level.addParticle((ParticleOptions)ParticleTypes.SMOKE, (double)x + 0.5, (double)y + 0.64, (double)z + 0.32, 0.0, 0.0, 0.0);
        }
    }
}

