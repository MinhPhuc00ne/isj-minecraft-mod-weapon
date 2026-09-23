/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.FenceBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.unusual.block_factorys_bosses.block.GuardrailDiagonalBlock;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GuardrailBlock
extends FenceBlock {
    public GuardrailBlock() {
        super(BlockBehaviour.Properties.of().destroyTime(2.5f).ignitedByLava().explosionResistance(2.5f).dynamicShape().sound(SoundType.WOOD));
    }

    public boolean connectsTo(BlockState state, boolean isSturdy, Direction dir) {
        if (state.getBlock() instanceof GuardrailDiagonalBlock && state.getValue((Property)GuardrailDiagonalBlock.FACING) == dir) {
            return true;
        }
        return state.getBlock() instanceof GuardrailBlock || isSturdy;
    }
}

