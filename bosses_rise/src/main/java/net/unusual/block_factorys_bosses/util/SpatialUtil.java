/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.util;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.network.PlayerPushMessage;

public interface SpatialUtil {
    public static float rotateDegrees(float rotationDegrees, Rotation rotation) {
        float f = Mth.wrapDegrees((float)rotationDegrees);
        return f + (switch (rotation) {
            default -> throw new MatchException(null, null);
            case Rotation.NONE -> 0.0f;
            case Rotation.CLOCKWISE_90 -> 90.0f;
            case Rotation.CLOCKWISE_180 -> 180.0f;
            case Rotation.COUNTERCLOCKWISE_90 -> 270.0f;
        });
    }

    public static float mirrorDegrees(float rotationDegrees, Mirror mirror) {
        rotationDegrees = Mth.wrapDegrees((float)rotationDegrees);
        return switch (mirror) {
            default -> throw new MatchException(null, null);
            case Mirror.NONE -> rotationDegrees;
            case Mirror.FRONT_BACK -> -rotationDegrees;
            case Mirror.LEFT_RIGHT -> 180.0f - rotationDegrees;
        };
    }

    public static void pushEntity(Entity target, Vec3 force) {
        SpatialUtil.pushEntity(target, force, -1.0);
    }

    public static void pushEntity(Entity target, Vec3 force, double speedLimit) {
        Vec3 newMotion;
        double entityDeltaMovementLength;
        if (speedLimit >= 0.0 && (entityDeltaMovementLength = (newMotion = target.getDeltaMovement().add(force)).dot(force.normalize())) > speedLimit) {
            force = newMotion.normalize().scale(speedLimit);
        }
        target.push(force);
        if (target instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)target;
            net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new PlayerPushMessage(force));
        }
    }
}

