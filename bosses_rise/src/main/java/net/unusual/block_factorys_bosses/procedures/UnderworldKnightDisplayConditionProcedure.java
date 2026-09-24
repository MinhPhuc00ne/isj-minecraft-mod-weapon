/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;

public class UnderworldKnightDisplayConditionProcedure {
    public static boolean execute(Entity entity) {
        int n;
        if (entity == null) {
            return false;
        }
        if (entity instanceof UnderworldKnightEntity) {
            UnderworldKnightEntity _datEntI = (UnderworldKnightEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(UnderworldKnightEntity.DATA_BOSS_PHASE);
        } else {
            n = 0;
        }
        return n <= 2;
    }
}

