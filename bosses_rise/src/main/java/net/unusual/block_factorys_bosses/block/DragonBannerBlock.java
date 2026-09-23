/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.Nullable
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.entity.DragonBannerBlockEntity;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DragonBannerBlock
extends Block
implements EntityBlock {
    public static final IntegerProperty ATTACHED = IntegerProperty.create((String)"blockstate", (int)1, (int)2);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape NORTH_UNATTACHED = Shapes.or((VoxelShape)DragonBannerBlock.box((double)-14.0, (double)7.0, (double)5.0, (double)30.0, (double)12.0, (double)9.0), (VoxelShape[])new VoxelShape[]{DragonBannerBlock.box((double)-12.5, (double)6.5, (double)4.5, (double)28.5, (double)12.5, (double)9.5), DragonBannerBlock.box((double)3.0, (double)0.0, (double)4.0, (double)13.0, (double)15.0, (double)10.0)});
    private static final VoxelShape EAST_UNATTACHED = Shapes.or((VoxelShape)DragonBannerBlock.box((double)7.0, (double)7.0, (double)-14.0, (double)11.0, (double)12.0, (double)30.0), (VoxelShape[])new VoxelShape[]{DragonBannerBlock.box((double)6.5, (double)6.5, (double)-12.5, (double)11.5, (double)12.5, (double)28.5), DragonBannerBlock.box((double)6.0, (double)0.0, (double)3.0, (double)12.0, (double)15.0, (double)13.0)});
    private static final VoxelShape WEST_UNATTACHED = Shapes.or((VoxelShape)DragonBannerBlock.box((double)5.0, (double)7.0, (double)-14.0, (double)9.0, (double)12.0, (double)30.0), (VoxelShape[])new VoxelShape[]{DragonBannerBlock.box((double)4.5, (double)6.5, (double)-12.5, (double)9.5, (double)12.5, (double)28.5), DragonBannerBlock.box((double)4.0, (double)0.0, (double)3.0, (double)10.0, (double)15.0, (double)13.0)});
    private static final VoxelShape SOUTH_UNATTACHED = Shapes.or((VoxelShape)DragonBannerBlock.box((double)-14.0, (double)7.0, (double)7.0, (double)30.0, (double)12.0, (double)11.0), (VoxelShape[])new VoxelShape[]{DragonBannerBlock.box((double)-12.5, (double)6.5, (double)6.5, (double)28.5, (double)12.5, (double)11.5), DragonBannerBlock.box((double)3.0, (double)0.0, (double)6.0, (double)13.0, (double)15.0, (double)12.0)});
    private static final VoxelShape NORTH_ATTACHED = Shapes.or((VoxelShape)NORTH_UNATTACHED, (VoxelShape)DragonBannerBlock.box((double)3.0, (double)0.0, (double)10.0, (double)13.0, (double)15.0, (double)16.0));
    private static final VoxelShape EAST_ATTACHED = Shapes.or((VoxelShape)EAST_UNATTACHED, (VoxelShape)DragonBannerBlock.box((double)0.0, (double)0.0, (double)3.0, (double)6.0, (double)15.0, (double)13.0));
    private static final VoxelShape WEST_ATTACHED = Shapes.or((VoxelShape)WEST_UNATTACHED, (VoxelShape)DragonBannerBlock.box((double)10.0, (double)0.0, (double)3.0, (double)16.0, (double)15.0, (double)13.0));
    private static final VoxelShape SOUTH_ATTACHED = Shapes.or((VoxelShape)SOUTH_UNATTACHED, (VoxelShape)DragonBannerBlock.box((double)3.0, (double)0.0, (double)0.0, (double)13.0, (double)15.0, (double)6.0));
    public static final int UNATTACHED = 1;
    public static final int UN_UNATTACHED = 2;

    public DragonBannerBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.0f, 10.0f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(ATTACHED, Integer.valueOf(1)));
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
        if ((Integer)state.getValue(ATTACHED) == 1) {
            return switch ((Direction)state.getValue(FACING)) {
                case Direction.NORTH -> NORTH_UNATTACHED;
                case Direction.EAST -> EAST_UNATTACHED;
                case Direction.WEST -> WEST_UNATTACHED;
                default -> SOUTH_UNATTACHED;
            };
        }
        return switch ((Direction)state.getValue(FACING)) {
            case Direction.NORTH -> NORTH_ATTACHED;
            case Direction.EAST -> EAST_ATTACHED;
            case Direction.WEST -> WEST_ATTACHED;
            default -> SOUTH_ATTACHED;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, ATTACHED});
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace().getAxis() == Direction.Axis.Y) {
            return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(ATTACHED, Integer.valueOf(1));
        }
        return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace())).setValue(ATTACHED, Integer.valueOf(2));
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DragonBannerBlockEntity(blockPos, blockState);
    }
}

