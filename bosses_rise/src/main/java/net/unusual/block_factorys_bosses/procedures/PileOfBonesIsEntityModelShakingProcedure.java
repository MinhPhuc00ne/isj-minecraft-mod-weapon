/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesIsEntityModelShakingProcedure {
    public static boolean execute(Entity entity) {
        int n;
        if (entity == null) {
            return false;
        }
        if (entity instanceof PileOfBonesEntity) {
            PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time);
        } else {
            n = 0;
        }
        return n > 0;
    }
}

