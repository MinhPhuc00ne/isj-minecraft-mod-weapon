/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface AnimatableId {
    public static long ofEntity(Entity entity) {
        return entity.getId();
    }

    public static long ofBlockEntity(BlockEntity blockEntity) {
        return blockEntity.getBlockPos().asLong();
    }
}

