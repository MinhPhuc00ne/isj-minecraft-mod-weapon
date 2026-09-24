/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.network.protocol.PacketFlow
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload$Type
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.saveddata.SavedData
 *  net.minecraft.world.level.saveddata.SavedData$Factory
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$Clone
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerChangedDimensionEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.neoforged.neoforge.network.PacketDistributor
 *  net.neoforged.neoforge.network.handling.IPayloadContext
 */
package net.unusual.block_factorys_bosses.network;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;

@EventBusSubscriber
public class BlockFactorysBossesModVariables {
    public static double cinematicX = 0.0;
    public static double cinematicY = 0.0;
    public static double cinematicZ = 0.0;

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        BossesRise.addNetworkMessage(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
        BossesRise.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
    }

    public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload
    {
        public static final CustomPacketPayload.Type<SavedDataSyncMessage> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"saved_data_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> {
            buffer.writeInt(message.dataType);
            if (message.data != null) {
                buffer.writeNbt((Tag)message.data.save(new CompoundTag(), (HolderLookup.Provider)buffer.registryAccess()));
            }
        }, buffer -> {
            int dataType = buffer.readInt();
            CompoundTag nbt = buffer.readNbt();
            SavedData data = null;
            if (nbt != null) {
                SavedData savedData = data = dataType == 0 ? new MapVariables() : new WorldVariables();
                if (data instanceof MapVariables) {
                    MapVariables mapVariables = (MapVariables)data;
                    mapVariables.read(nbt, (HolderLookup.Provider)buffer.registryAccess());
                } else if (data instanceof WorldVariables) {
                    WorldVariables worldVariables = (WorldVariables)data;
                    worldVariables.read(nbt, (HolderLookup.Provider)buffer.registryAccess());
                }
            }
            return new SavedDataSyncMessage(dataType, data);
        });

        public CustomPacketPayload.Type<SavedDataSyncMessage> type() {
            return TYPE;
        }

        public static void handleData(SavedDataSyncMessage message, IPayloadContext context) {
            if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
                context.enqueueWork(() -> ClientHandler.handle(message, context)).exceptionally(e -> {
                    context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                    return null;
                });
            }
        }

        private static interface ClientHandler {
            public static void handle(SavedDataSyncMessage message, IPayloadContext context) {
                if (message.dataType == 0) {
                    MapVariables.clientSide.read(message.data.save(new CompoundTag(), (HolderLookup.Provider)context.player().registryAccess()), (HolderLookup.Provider)context.player().registryAccess());
                } else {
                    WorldVariables.clientSide.read(message.data.save(new CompoundTag(), (HolderLookup.Provider)context.player().registryAccess()), (HolderLookup.Provider)context.player().registryAccess());
                }
            }
        }
    }

    public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload
    {
        public static final CustomPacketPayload.Type<PlayerVariablesSyncMessage> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath((String)"block_factorys_bosses", (String)"player_variables_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec.of((buffer, message) -> buffer.writeNbt((Tag)message.data().serializeNBT((HolderLookup.Provider)buffer.registryAccess())), buffer -> {
            PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
            message.data.deserializeNBT((HolderLookup.Provider)buffer.registryAccess(), buffer.readNbt());
            return message;
        });

        public CustomPacketPayload.Type<PlayerVariablesSyncMessage> type() {
            return TYPE;
        }

        public static void handleData(PlayerVariablesSyncMessage message, IPayloadContext context) {
            if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
                context.enqueueWork(() -> ClientHandler.handle(message, context)).exceptionally(e -> {
                    context.connection().disconnect((Component)Component.literal((String)e.getMessage()));
                    return null;
                });
            }
        }

        private static interface ClientHandler {
            public static void handle(PlayerVariablesSyncMessage message, IPayloadContext context) {
                ((PlayerVariables) net.neoforged.neoforge.attachment.AttachmentHolder.getData(context.player(), BossesRiseDataAttachments.PLAYER_VARIABLES)).deserializeNBT((HolderLookup.Provider)context.player().registryAccess(), message.data.serializeNBT((HolderLookup.Provider)context.player().registryAccess()));
            }
        }
    }

    public static class MapVariables
    extends SavedData {
        public static final String DATA_NAME = "block_factorys_bosses_mapvars";
        public double debugX = 0.0;
        public double debugY = 0.0;
        public double debugZ = 0.0;
        static MapVariables clientSide = new MapVariables();

        public static MapVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
            MapVariables data = new MapVariables();
            data.read(tag, lookupProvider);
            return data;
        }

        public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
            this.debugX = nbt.getDouble("debugX");
            this.debugY = nbt.getDouble("debugY");
            this.debugZ = nbt.getDouble("debugZ");
        }

        public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
            nbt.putDouble("debugX", this.debugX);
            nbt.putDouble("debugY", this.debugY);
            nbt.putDouble("debugZ", this.debugZ);
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof Level && !world.isClientSide()) {
                PacketDistributor.sendToAllPlayers((CustomPacketPayload)new SavedDataSyncMessage(0, this), (CustomPacketPayload[])new CustomPacketPayload[0]);
            }
        }

        public static MapVariables get(LevelAccessor world) {
            if (world instanceof ServerLevelAccessor) {
                ServerLevelAccessor serverLevelAcc = (ServerLevelAccessor)world;
                return (MapVariables)serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<>(MapVariables::new, MapVariables::load, null), DATA_NAME);
            }
            return clientSide;
        }
    }

    public static class WorldVariables
    extends SavedData {
        public static final String DATA_NAME = "block_factorys_bosses_worldvars";
        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
            WorldVariables data = new WorldVariables();
            data.read(tag, lookupProvider);
            return data;
        }

        public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
        }

        public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
            return nbt;
        }

        public void syncData(LevelAccessor world) {
            this.setDirty();
            if (world instanceof ServerLevel) {
                ServerLevel level = (ServerLevel)world;
                PacketDistributor.sendToPlayersInDimension((ServerLevel)level, (CustomPacketPayload)new SavedDataSyncMessage(1, this), (CustomPacketPayload[])new CustomPacketPayload[0]);
            }
        }

        public static WorldVariables get(LevelAccessor world) {
            if (world instanceof ServerLevel) {
                ServerLevel level = (ServerLevel)world;
                return (WorldVariables)level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(WorldVariables::new, WorldVariables::load, null), DATA_NAME);
            }
            return clientSide;
        }
    }

    @EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                ((PlayerVariables) net.neoforged.neoforge.attachment.AttachmentHolder.getData(player2, BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables((Entity)event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                ((PlayerVariables) net.neoforged.neoforge.attachment.AttachmentHolder.getData(player2, BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables((Entity)event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                ((PlayerVariables) net.neoforged.neoforge.attachment.AttachmentHolder.getData(player2, BossesRiseDataAttachments.PLAYER_VARIABLES)).syncPlayerVariables((Entity)event.getEntity());
            }
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            PlayerVariables original = (PlayerVariables) net.neoforged.neoforge.attachment.AttachmentHolder.getData(event.getOriginal(), BossesRiseDataAttachments.PLAYER_VARIABLES);
            PlayerVariables clone = new PlayerVariables();
            clone.random = original.random;
            clone.boss_no_hit = original.boss_no_hit;
            net.neoforged.neoforge.attachment.AttachmentHolder.setData(event.getEntity(), BossesRiseDataAttachments.PLAYER_VARIABLES, (Object)clone);
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                MapVariables mapdata = MapVariables.get((LevelAccessor)event.getEntity().level());
                WorldVariables worlddata = WorldVariables.get((LevelAccessor)event.getEntity().level());
                if (mapdata != null) {
                    PacketDistributor.sendToPlayer((ServerPlayer)player2, (CustomPacketPayload)new SavedDataSyncMessage(0, mapdata), (CustomPacketPayload[])new CustomPacketPayload[0]);
                }
                if (worlddata != null) {
                    PacketDistributor.sendToPlayer((ServerPlayer)player2, (CustomPacketPayload)new SavedDataSyncMessage(1, worlddata), (CustomPacketPayload[])new CustomPacketPayload[0]);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                WorldVariables worlddata = WorldVariables.get((LevelAccessor)event.getEntity().level());
                if (worlddata != null) {
                    PacketDistributor.sendToPlayer((ServerPlayer)player2, (CustomPacketPayload)new SavedDataSyncMessage(1, worlddata), (CustomPacketPayload[])new CustomPacketPayload[0]);
                }
            }
        }
    }
}

