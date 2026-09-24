/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent
 *  net.neoforged.neoforge.network.PacketDistributor
 */
package net.unusual.block_factorys_bosses.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.AttachmentDiedMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IParticleAttachment {

    @EventBusSubscriber
    public static class Listen {
        @SubscribeEvent
        private static void onDied(EntityLeaveLevelEvent event) {
            Level level = event.getLevel();
            if (level instanceof ServerLevel) {
                ServerLevel level2 = (ServerLevel)level;
                if (event.getEntity() instanceof IParticleAttachment) {
                    PacketDistributor.sendToPlayersTrackingChunk((ServerLevel)level2, (ChunkPos)event.getEntity().chunkPosition(), (CustomPacketPayload)new AttachmentDiedMessage(event.getEntity().getId()), (CustomPacketPayload[])new CustomPacketPayload[0]);
                }
            }
        }
    }
}

