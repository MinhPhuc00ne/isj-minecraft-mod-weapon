/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Optional
 *  com.google.common.collect.Iterators
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.ItemInteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.block;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeDoorBlock
extends Block
implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Part> PART = EnumProperty.create((String)"type", Part.class, Part.ROOT, Part.BLOCK, Part.HALF_BLOCK);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty MOVING = BooleanProperty.create((String)"moving");
    private static final VoxelShape VERTICAL_SLAB_NORTH = Shapes.box((double)0.0, (double)0.0, (double)0.5, (double)1.0, (double)1.0, (double)1.0);
    private static final VoxelShape VERTICAL_SLAB_EAST = Shapes.box((double)0.0, (double)0.0, (double)0.0, (double)0.5, (double)1.0, (double)1.0);
    private static final VoxelShape VERTICAL_SLAB_SOUTH = Shapes.box((double)0.0, (double)0.0, (double)0.0, (double)1.0, (double)1.0, (double)0.5);
    private static final VoxelShape VERTICAL_SLAB_WEST = Shapes.box((double)0.5, (double)0.0, (double)0.0, (double)1.0, (double)1.0, (double)1.0);
    private final Predicate<ItemStack> isValidKeyItem;
    private final BiFunction<BlockPos, BlockState, ? extends HugeDoorBlockEntity> blockEntitySupplier;
    public static final int openingDuration = 150;
    public static final int closingDuration = 0;

    public HugeDoorBlock(BlockBehaviour.Properties properties, Predicate<ItemStack> isValidKeyItem, BiFunction<BlockPos, BlockState, ? extends HugeDoorBlockEntity> blockEntitySupplier) {
        super(properties);
        this.isValidKeyItem = isValidKeyItem;
        this.blockEntitySupplier = blockEntitySupplier;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH)).setValue(PART, Part.ROOT)).setValue(OPEN, Boolean.valueOf(false))).setValue(MOVING, Boolean.valueOf(false)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, PART, OPEN, MOVING});
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(PART) != Part.ROOT) {
            return null;
        }
        return this.blockEntitySupplier.apply(pos, state);
    }

    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (((Boolean)state.getValue(MOVING)).booleanValue()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (!(blockEntity instanceof HugeDoorBlockEntity)) {
                return;
            }
            HugeDoorBlockEntity blockEntity2 = (HugeDoorBlockEntity)blockEntity;
            Boolean isClosing = (Boolean)state.getValue(OPEN);
            if (blockEntity2.isFinishedMoving(isClosing != false ? 0 : 150)) {
                state = (BlockState)state.setValue(MOVING, Boolean.valueOf(false));
                if (!isClosing.booleanValue()) {
                    this.openDoor((Level)level, state, pos);
                }
            }
        }
    }

    private boolean scheduleDoorToggle(Level level, BlockState rootBlockState, BlockPos rootPos) {
        BlockEntity blockEntity = level.getBlockEntity(rootPos);
        if (!(blockEntity instanceof HugeDoorBlockEntity)) {
            return false;
        }
        HugeDoorBlockEntity blockEntity2 = (HugeDoorBlockEntity)blockEntity;
        if (((Boolean)rootBlockState.getValue(MOVING)).booleanValue()) {
            return false;
        }
        boolean isClosing = (Boolean)rootBlockState.getValue(OPEN);
        if (isClosing) {
            return false;
        }
        level.setBlock(rootPos, (BlockState)rootBlockState.setValue(MOVING, Boolean.valueOf(true)), 3);
        blockEntity2.setStartedMoving();
        level.scheduleTick(rootPos, rootBlockState.getBlock(), isClosing ? 0 : 150);
        return true;
    }

    public boolean isValidKeyItem(ItemStack stack) {
        return this.isValidKeyItem.test(stack);
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!this.isValidKeyItem(stack)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        BlockPos rootPos = HugeDoorBlock.findRootBlockPos((LevelAccessor)level, state, pos);
        if (rootPos == null) {
            return ItemInteractionResult.FAIL;
        }
        BlockState rootState = level.getBlockState(rootPos);
        if (((Boolean)rootState.getValue(OPEN)).booleanValue()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!this.scheduleDoorToggle(level, rootState, rootPos)) {
            return ItemInteractionResult.FAIL;
        }
        stack.consume(1, (LivingEntity)player);
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockState state = (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        Vec3i offsetToCorner = HugeDoorBlock.getTopOuterOffset(state);
        BlockPos rootPos = context.getClickedPos().offset(offsetToCorner.getX() / -2, 0, offsetToCorner.getZ() / -2);
        BlockPos otherCornerPos = rootPos.offset(offsetToCorner);
        if (level.isOutsideBuildHeight(otherCornerPos)) {
            return null;
        }
        for (BlockPos blockPos : BlockPos.betweenClosed((BlockPos)rootPos, (BlockPos)otherCornerPos)) {
            if (level.getBlockState(blockPos).canBeReplaced(context) && level.isInWorldBounds(blockPos)) continue;
            return null;
        }
        if (!rootPos.equals((Object)context.getClickedPos())) {
            state = (BlockState)state.setValue(PART, Part.BLOCK);
        }
        return state;
    }

    public void setPlacedBy(Level level, BlockPos placedPos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        this.placeDoor(level, state, placedPos);
        super.setPlacedBy(level, placedPos, state, placer, stack);
    }

    private void placeDoor(Level level, BlockState state, BlockPos placedPos) {
        Vec3i offsetToCorner = HugeDoorBlock.getTopOuterOffset(state);
        BlockPos rootPos = placedPos.offset(offsetToCorner.getX() / -2, 0, offsetToCorner.getZ() / -2);
        BlockState rootState = (BlockState)state.setValue(PART, Part.ROOT);
        BlockState blockState = (BlockState)state.setValue(PART, Part.BLOCK);
        BlockState halfBlockState = (BlockState)state.setValue(PART, Part.HALF_BLOCK);
        Boolean isOpen = (Boolean)rootState.getValue(OPEN);
        HugeDoorBlock.getDoorBlocks(rootState, rootPos, true, isOpen == false, isOpen, false).forEachRemaining(blockPos -> {
            if (blockPos.equals((Object)placedPos)) {
                return;
            }
            level.setBlock(blockPos, blockPos.equals((Object)rootPos) ? rootState : blockState, 3);
        });
        HugeDoorBlock.getDoorBlocks(rootState, rootPos, false, false, isOpen, isOpen).forEachRemaining(blockPos -> level.setBlock(blockPos, halfBlockState, 3));
    }

    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            this.removeDoor(level, state, pos);
        }
    }

    private void removeDoor(Level level, BlockState initiatingState, BlockPos initiatingPos) {
        BlockPos rootPos = HugeDoorBlock.findRootBlockPos((LevelAccessor)level, initiatingState, initiatingPos);
        if (rootPos == null) {
            return;
        }
        if (!rootPos.equals((Object)initiatingPos)) {
            if (initiatingState.getValue(OPEN) != level.getBlockState(rootPos).getValue(OPEN)) {
                return;
            }
            level.setBlock(rootPos, Blocks.AIR.defaultBlockState(), 3);
            return;
        }
        Boolean isOpen = (Boolean)initiatingState.getValue(OPEN);
        HugeDoorBlock.getDoorBlocks(initiatingState, initiatingPos, true, isOpen == false, isOpen, true).forEachRemaining(blockPos -> {
            if (level.getBlockState(blockPos).is(initiatingState.getBlock())) {
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            }
        });
    }

    private void openDoor(Level level, BlockState rootBlockState, BlockPos rootPos) {
        level.setBlock(rootPos, (BlockState)rootBlockState.setValue(OPEN, Boolean.valueOf(true)), 3);
        HugeDoorBlock.getDoorBlocks(rootBlockState, rootPos, false, true, false, false).forEachRemaining(blockPos -> level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3));
        BlockState openState = (BlockState)((BlockState)rootBlockState.setValue(PART, Part.BLOCK)).setValue(OPEN, Boolean.valueOf(true));
        BlockState openHalfState = (BlockState)openState.setValue(PART, Part.HALF_BLOCK);
        HugeDoorBlock.getDoorBlocks(rootBlockState, rootPos, true, false, true, false).forEachRemaining(blockPos -> {
            if (rootPos.equals(blockPos)) {
                return;
            }
            BlockState existingBlockState = level.getBlockState(blockPos);
            if (!existingBlockState.is(rootBlockState.getBlock()) && !existingBlockState.canBeReplaced()) {
                return;
            }
            level.setBlock(blockPos, openState, 3);
        });
        HugeDoorBlock.getDoorBlocks(rootBlockState, rootPos, false, false, false, true).forEachRemaining(blockPos -> {
            if (!level.getBlockState(blockPos).canBeReplaced()) {
                return;
            }
            level.setBlock(blockPos, openHalfState, 3);
        });
    }

    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(PART) != Part.HALF_BLOCK) {
            return Shapes.block();
        }
        Direction facing = (Direction)state.getValue(FACING);
        return switch (facing) {
            case Direction.NORTH -> VERTICAL_SLAB_NORTH;
            case Direction.EAST -> VERTICAL_SLAB_EAST;
            case Direction.SOUTH -> VERTICAL_SLAB_SOUTH;
            case Direction.WEST -> VERTICAL_SLAB_WEST;
            default -> throw new IllegalStateException();
        };
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : (BlockState)state.setValue(FACING, mirror.getRotation((Direction)state.getValue(FACING)).rotate((Direction)state.getValue(FACING)));
    }

    @Nullable
    public static BlockPos findRootBlockPos(LevelAccessor level, BlockState originState, BlockPos originPos) {
        if (!(originState.getBlock() instanceof HugeDoorBlock)) {
            return null;
        }
        if (originState.getValue(PART) == Part.ROOT) {
            return originPos;
        }
        Boolean isOpen = (Boolean)originState.getValue(OPEN);
        Direction facing = (Direction)originState.getValue(FACING);
        BlockPos maxRootPos = originPos.subtract(HugeDoorBlock.getTopOuterOffset(originState));
        if (isOpen.booleanValue()) {
            maxRootPos = maxRootPos.relative(facing, (HugeDoorBlock.getWidth(originState) - 1) / -2);
        }
        Optional rootPos = Iterators.tryFind(HugeDoorBlock.getDoorBlocks(originState, maxRootPos, true, isOpen == false, isOpen, true), pos -> {
            BlockState state = level.getBlockState(pos);
            if (!state.is(originState.getBlock())) {
                return false;
            }
            return state.getValue(PART) == Part.ROOT && state.getValue(FACING) == facing;
        });
        return rootPos.isPresent() ? ((BlockPos) rootPos.get()).immutable() : null;
    }

    public static Iterator<@NotNull BlockPos> getDoorBlocks(BlockState rootState, BlockPos rootPos, boolean hinges, boolean closedWings, boolean openWings, boolean includeOpenWingTips) {
        Direction facing = (Direction)rootState.getValue(FACING);
        Direction otherHingeDirection = HugeDoorBlock.getDirectionToOtherHinge(facing);
        int width = HugeDoorBlock.getWidth(rootState);
        int height = HugeDoorBlock.getHeight(rootState);
        Vec3i doorWingOffset = new Vec3i(0, height - 1, 0).relative(facing, (width - 1) / 2);
        BlockPos otherHingePos = rootPos.relative(otherHingeDirection, width - 1);
        ArrayList iterators = new ArrayList(4);
        if (hinges && closedWings) {
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos, (BlockPos)rootPos.offset(HugeDoorBlock.getTopOuterOffset(rootState))).iterator());
        } else if (hinges) {
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos, (BlockPos)rootPos.above(height - 1)).iterator());
            iterators.add(BlockPos.betweenClosed((BlockPos)otherHingePos, (BlockPos)otherHingePos.above(height - 1)).iterator());
        } else if (closedWings) {
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos.relative(otherHingeDirection), (BlockPos)rootPos.relative(otherHingeDirection, width - 1).above(height - 1)).iterator());
        }
        if (openWings && includeOpenWingTips) {
            iterators.add(BlockPos.betweenClosed((BlockPos)otherHingePos, (BlockPos)otherHingePos.offset(doorWingOffset)).iterator());
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos, (BlockPos)rootPos.offset(doorWingOffset)).iterator());
        } else if (openWings) {
            Vec3i doorWingTiplessOffset = new Vec3i(0, height - 1, 0).relative(facing, (width - 2) / 2);
            iterators.add(BlockPos.betweenClosed((BlockPos)otherHingePos, (BlockPos)otherHingePos.offset(doorWingTiplessOffset)).iterator());
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos, (BlockPos)rootPos.offset(doorWingTiplessOffset)).iterator());
        } else if (includeOpenWingTips && width % 2 == 1) {
            iterators.add(BlockPos.betweenClosed((BlockPos)otherHingePos.offset(doorWingOffset.getX(), 0, doorWingOffset.getZ()), (BlockPos)otherHingePos.offset(doorWingOffset)).iterator());
            iterators.add(BlockPos.betweenClosed((BlockPos)rootPos.offset(doorWingOffset.getX(), 0, doorWingOffset.getZ()), (BlockPos)rootPos.offset(doorWingOffset)).iterator());
        }
        return Iterators.concat((Iterator[])iterators.toArray(new Iterator[0]));
    }

    public static int getWidth(BlockState state) {
        return 5;
    }

    public static int getHeight(BlockState state) {
        return 8;
    }

    public static Direction getDirectionToOtherHinge(BlockState blockState) {
        return HugeDoorBlock.getDirectionToOtherHinge((Direction)blockState.getValue(FACING));
    }

    public static Direction getDirectionToOtherHinge(Direction facingDirection) {
        return facingDirection.getCounterClockWise();
    }

    public static Vec3i getTopOuterOffset(BlockState state) {
        return new Vec3i(0, HugeDoorBlock.getHeight(state) - 1, 0).relative(HugeDoorBlock.getDirectionToOtherHinge(state), HugeDoorBlock.getWidth(state) - 1);
    }

    public static Vector3f getHorizontalCenterOffset(BlockState state) {
        Vec3i offset = HugeDoorBlock.getTopOuterOffset(state);
        return new Vector3f((float)offset.getX() / 2.0f, 0.0f, (float)offset.getZ() / 2.0f);
    }

    public static enum Part implements StringRepresentable
    {
        ROOT("root"),
        BLOCK("block"),
        HALF_BLOCK("half_block");

        private final String name;

        private Part(String name) {
            this.name = name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}

