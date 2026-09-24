/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  java.lang.MatchException
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.CameraType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Post
 *  net.neoforged.neoforge.client.event.ClientTickEvent$Pre
 *  net.neoforged.neoforge.client.event.MovementInputUpdateEvent
 *  net.neoforged.neoforge.client.event.RenderGuiEvent$Pre
 *  net.neoforged.neoforge.client.event.RenderPlayerEvent$Pre
 *  net.neoforged.neoforge.client.event.ViewportEvent$ComputeCameraAngles
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$PlayerRespawnEvent
 *  net.neoforged.neoforge.event.level.LevelEvent$Load
 *  net.neoforged.neoforge.event.level.LevelEvent$Unload
 *  net.neoforged.neoforge.event.server.ServerStoppingEvent
 *  net.neoforged.neoforge.event.tick.EntityTickEvent$Post
 */
package net.unusual.block_factorys_bosses.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.client.renderer.DragonBonesFirstPersonRenderer;
import net.unusual.block_factorys_bosses.client.renderer.KnightFirstPersonRenderer;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import net.unusual.block_factorys_bosses.geckolib.boss.knight.UnderworldKnightRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(value={Dist.CLIENT})
public class ClientEvents {
    public static final Map<UnderworldKnightEntity, SubParticleEmitter> PARTICULARS = new HashMap<UnderworldKnightEntity, SubParticleEmitter>();
    private static final ResourceLocation DASH_GREEN = BossesRise.prefix("textures/gui/roll/dash_green.png");
    private static final ResourceLocation DASH_YELLOW = BossesRise.prefix("textures/gui/roll/dash_yellow.png");
    private static final ResourceLocation DASH_ORANGE = BossesRise.prefix("textures/gui/roll/dash_orange.png");
    private static final ResourceLocation DASH_RED = BossesRise.prefix("textures/gui/roll/dash_red.png");
    private static final ResourceLocation DASH_GREY = BossesRise.prefix("textures/gui/roll/dash_grey.png");
    private static final int TEXTURE_SIZE = 18;

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void renderGUI(RenderGuiEvent.Pre event) {
        if (!((Boolean)ClientConfiguration.ROLL_GUI.get()).booleanValue()) {
            return;
        }
        if (!((Boolean)ServerConfiguration.CAN_USE_ROLL.get()).booleanValue()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.player == null || minecraft.player.isSpectator()) {
            return;
        }
        GuiGraphics guiGraphics = event.getGuiGraphics();
        guiGraphics.drawManaged(() -> ClientEvents.renderGUIManaged(guiGraphics));
    }

    public static void renderGUIManaged(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }
        RenderSystem.enableBlend();
        RollAttachment attachment = RollAttachment.fromPlayer((Player)minecraft.player);
        guiGraphics.guiWidth();
        guiGraphics.pose().pushPose();
        int count = attachment.rollCount();
        float ready = 0.0f;
        for (int i = 0; i < count; ++i) {
            ready += 1.0f - attachment.getCooldown(i);
        }
        ResourceLocation location = DASH_GREY;
        int color = 0x353535;
        if (ready - 1.0f >= (float)(count - 1) * 0.75f) {
            location = DASH_GREEN;
            color = 7654656;
        } else if (ready - 1.0f >= (float)(count - 1) * 0.5f) {
            location = DASH_YELLOW;
            color = 15250688;
        } else if (ready - 1.0f >= (float)(count - 1) * 0.25f) {
            location = DASH_ORANGE;
            color = 15224064;
        } else if (ready >= 1.0f) {
            location = DASH_RED;
            color = 14221315;
        }
        int configX = (Integer)ClientConfiguration.ROLL_GUI_OFFSET_X.get();
        int configY = (Integer)ClientConfiguration.ROLL_GUI_OFFSET_Y.get();
        int x = switch ((ClientConfiguration.GUIAnchors)((Object)ClientConfiguration.ROLL_GUI_OFFSET_ANCHOR.get())) {
            default -> throw new MatchException(null, null);
            case ClientConfiguration.GUIAnchors.HOTBAR_RIGHT -> guiGraphics.guiWidth() / 2 + 92;
            case ClientConfiguration.GUIAnchors.HOTBAR_LEFT -> guiGraphics.guiWidth() / 2 - 110 - Minecraft.getInstance().font.width(String.valueOf((int)ready));
            case ClientConfiguration.GUIAnchors.TOP_LEFT, ClientConfiguration.GUIAnchors.BOTTOM_LEFT -> 1;
            case ClientConfiguration.GUIAnchors.TOP_RIGHT, ClientConfiguration.GUIAnchors.BOTTOM_RIGHT -> guiGraphics.guiWidth() - 18 - Minecraft.getInstance().font.width(String.valueOf((int)ready)) - 1;
            case ClientConfiguration.GUIAnchors.SCREEN_CENTER -> guiGraphics.guiWidth() / 2 - 9;
        };
        int y = switch ((ClientConfiguration.GUIAnchors)((Object)ClientConfiguration.ROLL_GUI_OFFSET_ANCHOR.get())) {
            default -> throw new MatchException(null, null);
            case ClientConfiguration.GUIAnchors.HOTBAR_RIGHT, ClientConfiguration.GUIAnchors.HOTBAR_LEFT, ClientConfiguration.GUIAnchors.BOTTOM_LEFT, ClientConfiguration.GUIAnchors.BOTTOM_RIGHT -> guiGraphics.guiHeight() - 1;
            case ClientConfiguration.GUIAnchors.TOP_LEFT, ClientConfiguration.GUIAnchors.TOP_RIGHT -> 19;
            case ClientConfiguration.GUIAnchors.SCREEN_CENTER -> guiGraphics.guiHeight() / 2 - 9 + 18;
        };
        guiGraphics.blit(location, x + configX, y - 18 + configY, 0.0f, 0.0f, 18, 18, 18, 18);
        Font font = Minecraft.getInstance().font;
        String string = String.valueOf((int)ready);
        Objects.requireNonNull(Minecraft.getInstance().font);
        guiGraphics.drawString(font, string, x + 18 + configX + 1, y - 9 + configY, 0, false);
        Font font2 = Minecraft.getInstance().font;
        String string2 = String.valueOf((int)ready);
        Objects.requireNonNull(Minecraft.getInstance().font);
        guiGraphics.drawString(font2, string2, x + 18 + configX - 1, y - 9 + configY, 0, false);
        Font font3 = Minecraft.getInstance().font;
        String string3 = String.valueOf((int)ready);
        Objects.requireNonNull(Minecraft.getInstance().font);
        guiGraphics.drawString(font3, string3, x + 18 + configX, y - 9 + configY + 1, 0, false);
        Font font4 = Minecraft.getInstance().font;
        String string4 = String.valueOf((int)ready);
        Objects.requireNonNull(Minecraft.getInstance().font);
        guiGraphics.drawString(font4, string4, x + 18 + configX, y - 9 + configY - 1, 0, false);
        Font font5 = Minecraft.getInstance().font;
        String string5 = String.valueOf((int)ready);
        Objects.requireNonNull(Minecraft.getInstance().font);
        guiGraphics.drawString(font5, string5, x + 18 + configX, y - 9 + configY, color, false);
        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    }

    @SubscribeEvent
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        RollAttachment attachment;
        if (!((Boolean)ClientConfiguration.FIRST_PERSON_ROLL.get()).booleanValue()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level != null && minecraft.options.getCameraType() == CameraType.FIRST_PERSON && minecraft.player != null && (attachment = RollAttachment.fromPlayer((Player)minecraft.player)).isRolling()) {
            float rollProgress = (float)(((double)attachment.getRoll() - event.getPartialTick()) / (double)attachment.getRollDuration());
            switch (attachment.getDirection()) {
                case FORWARD: {
                    event.setPitch(event.getPitch() - rollProgress * 360.0f);
                    break;
                }
                case BACKWARD: {
                    event.setPitch(event.getPitch() + rollProgress * 360.0f);
                    break;
                }
                case LEFT: {
                    event.setRoll(event.getRoll() + rollProgress * 360.0f);
                    break;
                }
                case RIGHT: {
                    event.setRoll(event.getRoll() - rollProgress * 360.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void serverQuit(ServerStoppingEvent event) {
        BossesRiseClientCinematicCamera.stopCinematicCamera();
    }

    @SubscribeEvent
    public static void playerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        BossesRiseClientCinematicCamera.stopCinematicCamera();
    }

    @SubscribeEvent
    public static void entityDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (!(livingEntity instanceof CinematicEntity)) {
            return;
        }
        CinematicEntity entity = (CinematicEntity)livingEntity;
        if (entity != BossesRiseClientCinematicCamera.getTrackedObject()) {
            return;
        }
        BossesRiseClientCinematicCamera.stopCinematicCamera();
    }

    @SubscribeEvent
    public static void playerDeath(PlayerEvent.PlayerRespawnEvent event) {
        BossesRiseClientCinematicCamera.stopCinematicCamera();
    }

    @SubscribeEvent
    public static void updateMovementInput(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        if (player instanceof LocalPlayer) {
            LocalPlayer player2 = (LocalPlayer)player;
            player2.level().getEntitiesOfClass(YetiEntity.class, player2.getBoundingBox().inflate(16.0), e -> true).forEach(yeti -> {
                if ((Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) > 10 && (Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) <= 109) {
                    event.getInput().leftImpulse *= 0.0f;
                    event.getInput().forwardImpulse *= 0.0f;
                }
            });
            if (player2.isUsingItem() && player2.getUseItem().is(BossesRiseItems.ICE_GAUNTLET)) {
                event.getInput().leftImpulse *= 2.0f;
                event.getInput().forwardImpulse *= 2.0f;
            }
        }
    }

    @SubscribeEvent
    public static void onTickPre(ClientTickEvent.Pre event) {
        List<UnderworldKnightEntity> toRemove = PARTICULARS.keySet().stream().filter(Entity::isRemoved).toList();
        for (UnderworldKnightEntity knight : toRemove) {
            PARTICULARS.remove(knight);
        }
        if (BossesRiseClientCinematicCamera.isCameraActive()) {
            BlockEntity blockEntity;
            Entity entity;
            CinematicEntity tracked = BossesRiseClientCinematicCamera.getTrackedObject();
            if (tracked instanceof Entity && (entity = (Entity)tracked).isRemoved()) {
                BossesRiseClientCinematicCamera.stopCinematicCamera();
            }
            if (tracked instanceof BlockEntity && (blockEntity = (BlockEntity)tracked).isRemoved()) {
                BossesRiseClientCinematicCamera.stopCinematicCamera();
            }
        }
        ClientEvents.onTick(true);
    }

    @SubscribeEvent
    public static void onTickPost(ClientTickEvent.Post event) {
        ClientEvents.onTick(false);
    }

    public static void onTick(boolean isPreEvent) {
        if (!ClientEvents.isGameActive()) {
            return;
        }
        DragonBonesFirstPersonRenderer.clientTick();
        KnightFirstPersonRenderer.clientTick();
        AnimationTickHolder.tick();
    }

    @SubscribeEvent
    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor world = event.getLevel();
        if (world.isClientSide() && world instanceof ClientLevel) {
            AnimationTickHolder.reset();
        }
    }

    @SubscribeEvent
    public static void onUnloadWorld(LevelEvent.Unload event) {
        if (!event.getLevel().isClientSide()) {
            return;
        }
        AnimationTickHolder.reset();
    }

    @SubscribeEvent
    public static void onRenderPlayerEvent(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player instanceof LocalPlayer localPlayer && localPlayer.getControlledVehicle() instanceof CannonEntity cannon) {
            if (cannon.bonePos != null) {
                PoseStack stack = event.getPoseStack();
                float partialTick = event.getPartialTick();
                Vec3 diff = localPlayer.getPosition(partialTick).subtract(cannon.getPosition(partialTick)).add(localPlayer.getVehicleAttachmentPoint((Entity)cannon).xRot(cannon.getViewXRot(partialTick) * ((float)Math.PI / 180)).yRot((-cannon.getViewYRot(partialTick) - 180.0f) * ((float)Math.PI / 180)));
                stack.translate(-diff.x, -diff.y, -diff.z);
                stack.translate(cannon.bonePos.x, cannon.bonePos.y, cannon.bonePos.z);
                stack.mulPose(Axis.YN.rotationDegrees(cannon.getYRot()));
                stack.mulPose(Axis.XN.rotationDegrees(-cannon.getViewXRot(event.getPartialTick())));
                stack.mulPose(Axis.YP.rotationDegrees(cannon.getYRot()));
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof UnderworldKnightEntity) {
            SubParticleEmitter emitter;
            ParticleLocator locator;
            UnderworldKnightEntity knight = (UnderworldKnightEntity)entity;
            if (knight.level().isClientSide && !PARTICULARS.containsKey(knight) && knight.isTransformed() && (locator = UnderworldKnightRenderer.BONER.get("head")) != null && (emitter = SubParticleEmitter.attachNewParticle(knight, (ParticleOptions)BossesRiseParticleTypes.PHASE_TRANSITION_10.get(), locator)) != null) {
                PARTICULARS.put(knight, emitter);
            }
        }
    }

    protected static boolean isGameActive() {
        return Minecraft.getInstance().level != null && Minecraft.getInstance().player != null;
    }
}

