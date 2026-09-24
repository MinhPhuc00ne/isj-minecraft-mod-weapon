/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.neoforged.neoforge.common.util.INBTSerializable
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package net.unusual.block_factorys_bosses.attachment.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.BlockFactorysBossesModVariables;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlayerVariables
implements INBTSerializable<CompoundTag> {
    public double random = 0.0;
    public boolean boss_no_hit = false;

    public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("random", this.random);
        nbt.putBoolean("boss_no_hit", this.boss_no_hit);
        return nbt;
    }

    public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        this.random = nbt.getDouble("random");
        this.boss_no_hit = nbt.getBoolean("boss_no_hit");
    }

    public void syncPlayerVariables(Entity entity) {
        if (entity instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)entity;
            PacketDistributor.sendToPlayer((ServerPlayer)serverPlayer, (CustomPacketPayload)new BlockFactorysBossesModVariables.PlayerVariablesSyncMessage(this), (CustomPacketPayload[])new CustomPacketPayload[0]);
        }
    }
}

