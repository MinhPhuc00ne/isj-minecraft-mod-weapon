/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.api.distmarker.OnlyIn
 */
package net.unusual.block_factorys_bosses.block;

import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CandlesBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape X_BOX = CandlesBlock.box((double)5.0, (double)0.0, (double)0.0, (double)11.0, (double)23.0, (double)16.0);
    private static final VoxelShape Z_BOX = CandlesBlock.box((double)0.0, (double)0.0, (double)5.0, (double)16.0, (double)23.0, (double)11.0);
    public static final Vec3[] FLAME_POINTS_Z = new Vec3[]{new Vec3(0.5, 1.63, 0.5), new Vec3(0.18, 1.55, 0.5), new Vec3(0.87, 1.55, 0.5)};
    public static final Vec3[] FLAME_POINTS_X = new Vec3[]{new Vec3(0.5, 1.63, 0.5), new Vec3(0.5, 1.55, 0.18), new Vec3(0.5, 1.55, 0.87)};
    private final Supplier<SimpleParticleType> particleType;

    public CandlesBlock(Supplier<SimpleParticleType> particleType) {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.0f, 10.0f).lightLevel(s -> 15).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
            case Direction.EAST, Direction.WEST -> X_BOX;
            default -> Z_BOX;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Direction.Axis facing = ((Direction)state.getValue(FACING)).getAxis();
        if (random.nextBoolean()) {
            for (Vec3 point : facing == Direction.Axis.X ? FLAME_POINTS_X : FLAME_POINTS_Z) {
                level.addParticle((ParticleOptions)this.particleType.get(), (double)x + point.x, (double)y + point.y, (double)z + point.z, 0.0, 0.0, 0.0);
            }
        } else {
            for (Vec3 point : facing == Direction.Axis.X ? FLAME_POINTS_X : FLAME_POINTS_Z) {
                level.addParticle((ParticleOptions)this.particleType.get(), (double)x + point.x + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), (double)y + point.y + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), (double)z + point.z + Mth.nextDouble((RandomSource)random, (double)-0.05, (double)0.05), 0.0, 0.0, 0.0);
            }
        }
    }
}

