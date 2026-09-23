/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractDirectionalDoubleBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CorpseBlock
extends AbstractDirectionalDoubleBlock {
    private static final VoxelShape UPPER = CorpseBlock.box((double)2.0, (double)-16.0, (double)2.0, (double)14.0, (double)16.0, (double)14.0);
    private static final VoxelShape LOWER = CorpseBlock.box((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)32.0, (double)14.0);

    public CorpseBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.BONE_BLOCK).strength(1.0f, 10.0f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    protected VoxelShape getUpperShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return UPPER;
    }

    @Override
    protected VoxelShape getLowerShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return LOWER;
    }
}

