package com.minhphuc.weapons.content.infinitygauntlet;

import com.mojang.datafixers.util.Pair;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.function.Supplier;

public class ServerboundSpaceTeleportPacket {
    private final int destinationId;

    public ServerboundSpaceTeleportPacket(int destinationId) {
        this.destinationId = destinationId;
    }

    public ServerboundSpaceTeleportPacket(FriendlyByteBuf buf) {
        this.destinationId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.destinationId);
    }

    public void handle(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        context.queue(() -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (player == null) return;

            ServerLevel currentLevel = player.serverLevel();

            if (destinationId >= 0 && destinationId <= 2) {
                // Dimension Teleportation (0 = Overworld, 1 = Nether, 2 = End)
                ResourceKey<Level> targetDimensionKey = switch (destinationId) {
                    case 1 -> Level.NETHER;
                    case 2 -> Level.END;
                    default -> Level.OVERWORLD;
                };

                ServerLevel targetLevel = currentLevel.getServer().getLevel(targetDimensionKey);
                if (targetLevel != null) {
                    BlockPos spawnPos = targetLevel.getSharedSpawnPos();
                    double destX = spawnPos.getX() + 0.5D;
                    double destY = spawnPos.getY() + 1.0D;
                    double destZ = spawnPos.getZ() + 0.5D;

                    if (targetDimensionKey == Level.END) {
                        spawnPos = ServerLevel.END_SPAWN_POINT;
                        destX = spawnPos.getX() + 0.5D;
                        destY = spawnPos.getY() + 1.0D;
                        destZ = spawnPos.getZ() + 0.5D;
                    }

                    // Effects at origin
                    currentLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.5F, 1.0F);
                    currentLevel.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1.0D, player.getZ(), 50, 0.5D, 1.0D, 0.5D, 0.1D);

                    player.teleportTo(targetLevel, destX, destY, destZ, player.getYRot(), player.getXRot());
                    player.resetFallDistance();

                    // Effects at destination
                    targetLevel.playSound(null, destX, destY, destZ,
                            SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.4F);
                    targetLevel.sendParticles(ParticleTypes.SONIC_BOOM, destX, destY + 1.0D, destZ, 1, 0, 0, 0, 0);
                    targetLevel.sendParticles(ParticleTypes.FLASH, destX, destY + 1.0D, destZ, 1, 0, 0, 0, 0);

                    String destName = switch (destinationId) {
                        case 1 -> "Cõi Địa Ngục (Nether)";
                        case 2 -> "Thế Giới Kết Thúc (The End)";
                        default -> "Thế Giới Thường (Overworld)";
                    };

                    player.displayClientMessage(
                        Component.literal("§9§l[ĐÁ KHÔNG GIAN] §fĐã xé rách chiều không gian mở cổng đến §e" + destName + "! 🌌"),
                        true
                    );
                }
            } else if (destinationId == 3 || destinationId == 4) {
                // Structure Teleportation (3 = Village, 4 = Ancient City)
                try {
                    var registry = currentLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
                    HolderSet<Structure> holderSet = null;

                    if (destinationId == 3) {
                        holderSet = registry.getOrCreateTag(StructureTags.VILLAGE);
                    } else {
                        var ancientCityHolder = registry.getHolder(BuiltinStructures.ANCIENT_CITY);
                        if (ancientCityHolder.isPresent()) {
                            holderSet = HolderSet.direct(ancientCityHolder.get());
                        }
                    }

                    if (holderSet != null) {
                        Pair<BlockPos, Holder<Structure>> result = currentLevel.getChunkSource().getGenerator().findNearestMapStructure(
                            currentLevel, holderSet, player.blockPosition(), 100, false
                        );

                        if (result != null) {
                            BlockPos targetPos = result.getFirst();
                            int topY = currentLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, targetPos.getX(), targetPos.getZ());
                            if (destinationId == 4) {
                                topY = targetPos.getY(); // Ancient city is underground
                            }

                            currentLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.5F, 1.0F);

                            player.teleportTo(targetPos.getX() + 0.5D, topY + 1.0D, targetPos.getZ() + 0.5D);
                            player.resetFallDistance();

                            currentLevel.playSound(null, targetPos.getX(), topY + 1.0D, targetPos.getZ(),
                                    SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.4F);
                            currentLevel.sendParticles(ParticleTypes.SONIC_BOOM, targetPos.getX(), topY + 1.0D, targetPos.getZ(), 1, 0, 0, 0, 0);

                            String structName = (destinationId == 3) ? "Làng Dân Làng (Village)" : "Thành Phố Cổ Warden (Ancient City)";
                            player.displayClientMessage(
                                Component.literal("§9§l[ĐÁ KHÔNG GIAN] §fĐã tìm thấy & dịch chuyển đến §e" + structName + "! 🌌"),
                                true
                            );
                        } else {
                            player.displayClientMessage(
                                Component.literal("§c[ĐÁ KHÔNG GIAN] Không tìm thấy cấu trúc trong phạm vi 100 chunks!"),
                                true
                            );
                        }
                    }
                } catch (Exception e) {
                    player.displayClientMessage(
                        Component.literal("§c[ĐÁ KHÔNG GIAN] Không thể định vị địa danh!"),
                        true
                    );
                }
            }
        });
    }
}
