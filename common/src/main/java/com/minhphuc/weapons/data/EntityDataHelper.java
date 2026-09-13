package com.minhphuc.weapons.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class EntityDataHelper {
    public static CompoundTag getCustomData(Entity entity) {
        if (entity instanceof IEntityDataSaver saver) {
            return saver.weapons$getModData();
        }
        return new CompoundTag();
    }
}
