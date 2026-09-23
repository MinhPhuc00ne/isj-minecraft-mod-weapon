/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.component.DataComponentType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.neoforged.neoforge.registries.DeferredRegister
 *  net.neoforged.neoforge.registries.DeferredRegister$DataComponents
 */
package net.unusual.block_factorys_bosses.init;

import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.item.component.OperationMode;

public class BossesRiseDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents((ResourceKey)Registries.DATA_COMPONENT_TYPE, (String)"block_factorys_bosses");
    public static final Supplier<DataComponentType<OperationMode>> OPERATION_MODE = REGISTRAR.registerComponentType("operation_mode", builder -> builder.networkSynchronized(OperationMode.STREAM_CODEC).cacheEncoding());
}

