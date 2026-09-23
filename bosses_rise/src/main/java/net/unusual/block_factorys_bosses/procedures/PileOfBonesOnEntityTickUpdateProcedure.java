/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;

public class PileOfBonesOnEntityTickUpdateProcedure {
    public static void execute(Entity entity) {
        int n;
        if (entity == null) {
            return;
        }
        if (entity instanceof PileOfBonesEntity) {
            PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
            n = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time);
        } else {
            n = 0;
        }
        if (0 < n && entity instanceof PileOfBonesEntity) {
            int n2;
            PileOfBonesEntity _datEntSetI = (PileOfBonesEntity)entity;
            SynchedEntityData synchedEntityData = _datEntSetI.getEntityData();
            if (entity instanceof PileOfBonesEntity) {
                PileOfBonesEntity _datEntI = (PileOfBonesEntity)entity;
                n2 = (Integer)_datEntI.getEntityData().get(PileOfBonesEntity.DATA_hit_animation_time);
            } else {
                n2 = 0;
            }
            synchedEntityData.set(PileOfBonesEntity.DATA_hit_animation_time, (n2 - 1));
        }
    }
}

