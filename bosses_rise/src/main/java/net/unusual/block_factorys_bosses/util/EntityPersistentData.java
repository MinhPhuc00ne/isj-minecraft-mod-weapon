package net.unusual.block_factorys_bosses.util;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class EntityPersistentData {
    private static final Map<Entity, CompoundTag> DATA = Collections.synchronizedMap(new WeakHashMap<>());

    public static CompoundTag get(Entity entity) {
        return DATA.computeIfAbsent(entity, e -> new CompoundTag());
    }
}
