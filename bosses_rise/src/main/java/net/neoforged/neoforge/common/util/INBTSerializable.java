package net.neoforged.neoforge.common.util;

import net.minecraft.nbt.Tag;
import net.minecraft.core.HolderLookup;

public interface INBTSerializable<T extends Tag> {
    T serializeNBT(HolderLookup.Provider provider);
    void deserializeNBT(HolderLookup.Provider provider, T nbt);
}
