/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.math.Axis
 *  java.lang.MatchException
 *  javax.annotation.Nullable
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent
 *  net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent$OverlayType
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeCameraAngles
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3d
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  software.bernie.geckolib.cache.object.GeoBone
 */
package net.unusual.block_factorys_bosses.client.camera.renderer;

import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.camera.CinematicCameraTypes;
import net.unusual.block_factorys_bosses.client.camera.internal.CameraHandler;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import software.bernie.geckolib.cache.object.GeoBone;

@EventBusSubscriber(value={Dist.CLIENT})
public class CameraRenderer
implements CameraHandler {
    private static CameraInject camera;
    public static int viewBobCancel;
    @Nullable
    private static CameraType previousCameraType;
    private static float yaw;
    private static float pitch;
    private static Vec3 pos;
    private static float yawO;
    private static float pitchO;
    private static Vec3 posO;

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (event.getOverlayType() != RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
            return;
        }
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        ClientLevel level = Minecraft.getInstance().level;
        camera = (CameraInject)event.getCamera();
        if (level == null) {
            return;
        }
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
            return;
        }
        float partialTick = (float)event.getPartialTick();
        Vec3 interpolatedPos = new Vec3(Mth.lerp((double)partialTick, (double)CameraRenderer.posO.x, (double)CameraRenderer.pos.x), Mth.lerp((double)partialTick, (double)CameraRenderer.posO.y, (double)CameraRenderer.pos.y), Mth.lerp((double)partialTick, (double)CameraRenderer.posO.z, (double)CameraRenderer.pos.z));
        if (!interpolatedPos.equals((Object)Vec3.ZERO)) {
            camera.setCinematic(true);
            camera.setCinematicPosition(interpolatedPos);
        } else {
            camera.setCinematic(false);
            viewBobCancel = 3;
        }
        event.setYaw(Mth.lerp((float)partialTick, (float)yawO, (float)yaw));
        event.setPitch(Mth.lerp((float)partialTick, (float)pitchO, (float)pitch));
        event.setRoll(0.0f);
    }

    @SubscribeEvent
    public static void onTickPre(ClientTickEvent.Post event) {
        if (!BossesRiseClientCinematicCamera.isHandlerActive(CinematicCameraTypes.CAMERA)) {
            return;
        }
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        CameraRenderer.updateTickState();
    }

    public static void updateTickState() {
        posO = pos;
        yawO = yaw;
        pitchO = pitch;
        GeoBone cameraBone = BossesRiseClientCinematicCamera.getCameraBone();
        if (cameraBone == null) {
            pos = Vec3.ZERO;
            posO = Vec3.ZERO;
            CameraRenderer.calculateAngles();
        } else {
            CameraRenderer.updateCameraBoneState(cameraBone);
        }
    }

    private static void calculateAngles() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        Vec3 cameraPos = player.getEyePosition();
        Vec3 trackedPos = null;
        CinematicEntity trackedObject = BossesRiseClientCinematicCamera.getTrackedObject();
        if (trackedObject instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)trackedObject;
            trackedPos = blockEntity.getBlockPos().getCenter();
        }
        if (trackedObject instanceof Entity) {
            Entity entity = (Entity)trackedObject;
            trackedPos = entity.getEyePosition();
        }
        if (trackedPos == null) {
            return;
        }
        double dx = cameraPos.x - trackedPos.x;
        double dy = cameraPos.y - trackedPos.y;
        double dz = cameraPos.z - trackedPos.z;
        yaw = (float)Math.atan2(dx, dz) * -57.295776f + 180.0f;
        pitch = (float)Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 57.295776f;
    }

    private static Quaternionf rotateBlock(Direction facing) {
        return switch (facing) {
            default -> throw new MatchException(null, null);
            case Direction.SOUTH -> Axis.YP.rotationDegrees(180.0f);
            case Direction.WEST -> Axis.YP.rotationDegrees(90.0f);
            case Direction.NORTH -> Axis.YP.rotationDegrees(0.0f);
            case Direction.EAST -> Axis.YP.rotationDegrees(270.0f);
            case Direction.UP -> Axis.XP.rotationDegrees(90.0f);
            case Direction.DOWN -> Axis.XN.rotationDegrees(90.0f);
        };
    }

    private static void updateCameraBoneState(GeoBone cameraBone) {
        Vector3f bonePos;
        CinematicEntity cinematicEntity = BossesRiseClientCinematicCamera.getTrackedObject();
        if (cinematicEntity instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)cinematicEntity;
            BlockPos blockPos = blockEntity.getBlockPos();
            Direction facing = (Direction)blockEntity.getBlockState().getValue((Property)BlockStateProperties.HORIZONTAL_FACING);
            Quaternionf blockRotation = CameraRenderer.rotateBlock(facing);
            bonePos = CameraRenderer.toVec3f(cameraBone.getLocalPosition()).rotate((Quaternionfc)blockRotation).add((float)blockPos.getX() + 0.5f, (float)blockPos.getY(), (float)blockPos.getZ() + 0.5f);
            Vector3f lookDir = cameraBone.getWorldSpaceMatrix().transformDirection(new Vector3f(0.0f, 0.0f, 1.0f)).rotate((Quaternionfc)blockRotation);
            if (blockEntity instanceof HugeDoorBlockEntity) {
                bonePos = bonePos.add((Vector3fc)HugeDoorBlock.getHorizontalCenterOffset(blockEntity.getBlockState()));
            }
            yaw = (float)Mth.atan2((double)lookDir.x(), (double)(-lookDir.z())) * 57.295776f;
            pitch = (float)Mth.atan2((double)lookDir.y(), (double)Mth.sqrt((float)(lookDir.x() * lookDir.x() + lookDir.z() * lookDir.z()))) * 57.295776f;
        } else {
            bonePos = CameraRenderer.toVec3f(cameraBone.getWorldPosition());
            Monster boss = (Monster)BossesRiseClientCinematicCamera.getTrackedObject();
            CinematicRenderer render = (CinematicRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer((Entity)boss);
            Pair<Float, Float> cinematicYawAndPitch = render.getCinematicYawAndPitch(boss, cameraBone, Minecraft.getInstance().player);
            yaw = ((Float)cinematicYawAndPitch.getFirst()).floatValue();
            pitch = ((Float)cinematicYawAndPitch.getSecond()).floatValue();
        }
        if (bonePos.equals(0.0f, 0.0f, 0.0f)) {
            return;
        }
        pos = new Vec3((double)bonePos.x, (double)bonePos.y, (double)bonePos.z);
    }

    private static Vector3f toVec3f(Vector3d vec) {
        return new Vector3f((float)vec.x(), (float)vec.y(), (float)vec.z());
    }

    @Override
    public void init() {
        Minecraft mc = Minecraft.getInstance();
        previousCameraType = mc.options.getCameraType();
    }

    @Override
    public void cleanup() {
        if (previousCameraType != null) {
            Minecraft.getInstance().options.setCameraType(previousCameraType);
            previousCameraType = null;
        }
        camera.setCinematic(false);
        pos = Vec3.ZERO;
        posO = Vec3.ZERO;
        yawO = 0.0f;
        yaw = 0.0f;
        pitchO = 0.0f;
        pitch = 0.0f;
    }

    static {
        viewBobCancel = 0;
        previousCameraType = null;
        pos = Vec3.ZERO;
        posO = Vec3.ZERO;
    }
}

