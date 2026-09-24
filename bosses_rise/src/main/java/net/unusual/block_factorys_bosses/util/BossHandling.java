/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.LerpingBossEvent
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.Music
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.warden.Warden
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent$BossEventProgress
 *  net.neoforged.neoforge.client.event.SelectMusicEvent
 *  net.neoforged.neoforge.event.entity.living.LivingDeathEvent
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$StartTracking
 *  net.neoforged.neoforge.event.entity.player.PlayerEvent$StopTracking
 *  net.neoforged.neoforge.event.tick.ServerTickEvent$Post
 */
package net.unusual.block_factorys_bosses.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossHandling {
    public static final BossBar[] INFO = new BossBar[]{new BossBar("warden", 288, 76, 53, 182), new BossBar("ender_dragon", 288, 76, 53, 182), new BossBar("wither", 288, 76, 53, 182), new BossBar("yeti", 288, 76, 53, 182), new BossBar("dragon", 288, 76, 53, 182), new BossBar("worm", 288, 76, 53, 182), new BossBar("knight", 288, 76, 53, 182), new BossBar(BossHandling.bossBarTexture("knight", "complex_resurrected"), BossHandling.bossBarTexture("knight", "simple"), BossHandling.bossBarTexture("knight", "bar_resurrected"), 288, 76, 53, 182), new BossBar(BossHandling.bossBarTexture("kraken", "complex_phase_1"), BossHandling.bossBarTexture("kraken", "simple_phase_1"), BossHandling.bossBarTexture("kraken", "bar"), 288, 76, 53, 182), new BossBar(BossHandling.bossBarTexture("kraken", "complex_phase_2"), BossHandling.bossBarTexture("kraken", "simple_phase_2"), BossHandling.bossBarTexture("kraken", "bar"), 288, 76, 53, 182)};
    public static final Map<UUID, TrackedBoss> TRACKED_BOSSES = new HashMap<UUID, TrackedBoss>();
    public static final int VANILLA_BOSS = 2;
    public static final int WARDEN = 0;
    public static final int ENDER_DRAGON = 1;
    public static final int WITHER = 2;
    public static final int YETI = 3;
    public static final int DRAGON = 4;
    public static final int SANDWORM = 5;
    public static final int KNIGHT = 6;
    public static final int KNIGHT_TRANSFORMED = 7;
    public static final int KRAKEN_PHASE_1 = 8;
    public static final int KRAKEN_PHASE_2 = 9;

    public static ResourceLocation bossBarTexture(String boss, String textureVariant) {
        return BossesRise.prefix("textures/gui/bossbar/" + boss + "/" + textureVariant + ".png");
    }

    public static void updateBossType(ServerBossEvent bossEvent, int newBossType) {
        TrackedBoss trackedBoss = TRACKED_BOSSES.get(bossEvent.getId());
        if (trackedBoss != null) {
            TrackedBoss augmented = new TrackedBoss(newBossType, trackedBoss.playingMusic());
            TRACKED_BOSSES.put(bossEvent.getId(), augmented);
            bossEvent.getPlayers().forEach(player -> net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new UpdateBossBarTypeMessage(bossEvent.getId(), augmented)));
        }
    }

    public record TrackedBoss(int type, boolean playingMusic) {
        public static final Codec<TrackedBoss> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group(Codec.INT.fieldOf("type").forGetter(TrackedBoss::type), Codec.BOOL.fieldOf("playing_music").forGetter(TrackedBoss::playingMusic)).apply(recordCodecBuilder, TrackedBoss::new));

        public TrackedBoss setMusic(boolean music) {
            return new TrackedBoss(this.type, music);
        }
    }

    public record BossBar(ResourceLocation complexBase, ResourceLocation simpleBase, ResourceLocation healthBar, int texWidth, int texHeight, int barStart, int barLength) {
        public BossBar(String boss, int texWidth, int texHeight, int barStart, int barLength) {
            this(BossHandling.bossBarTexture(boss, "complex"), BossHandling.bossBarTexture(boss, "simple"), BossHandling.bossBarTexture(boss, "bar"), texWidth, texHeight, barStart, barLength);
        }
    }

    public static enum HealthBarStyle implements StringRepresentable
    {
        DEFAULT,
        OFF,
        VANILLA,
        SIMPLE,
        FANCY;


        public HealthBarStyle resolve() {
            if (this == DEFAULT) {
                return ((HealthBarDefaultStyle)((Object)ClientConfiguration.DEFAULT_BOSSBAR.get())).getStyle();
            }
            return this;
        }

        public String getSerializedName() {
            return this.toString().toLowerCase(Locale.ROOT);
        }
    }

    public static enum HealthBarDefaultStyle implements StringRepresentable
    {
        OFF(HealthBarStyle.OFF),
        VANILLA(HealthBarStyle.VANILLA),
        SIMPLE(HealthBarStyle.SIMPLE),
        FANCY(HealthBarStyle.FANCY);

        private final HealthBarStyle style;

        private HealthBarDefaultStyle(HealthBarStyle style) {
            this.style = style;
        }

        public HealthBarStyle getStyle() {
            return this.style;
        }

        public String getSerializedName() {
            return this.toString().toLowerCase(Locale.ROOT);
        }
    }

    @EventBusSubscriber
    public static class CommonHandler {
        public static Map<UUID, ServerBossEvent> WARDENS = new HashMap<UUID, ServerBossEvent>();

        public static ServerBossEvent getOrCreate(Warden warden) {
            ServerBossEvent bossEvent = WARDENS.get(warden.getUUID());
            if (bossEvent == null) {
                bossEvent = new ServerBossEvent(warden.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);
                TRACKED_BOSSES.put(bossEvent.getId(), new TrackedBoss(0, false));
                WARDENS.put(warden.getUUID(), bossEvent);
            }
            return bossEvent;
        }

        @SubscribeEvent
        public static void serverTick(ServerTickEvent.Post event) {
            if (!WARDENS.isEmpty()) {
                ArrayList<UUID> toRemove = new ArrayList<UUID>();
                for (UUID id : WARDENS.keySet()) {
                    boolean present = false;
                    for (ServerLevel level : event.getServer().getAllLevels()) {
                        Entity warden = level.getEntity(id);
                        if (warden == null || warden.isRemoved()) continue;
                        present = true;
                        break;
                    }
                    if (present) continue;
                    toRemove.add(id);
                }
                toRemove.forEach(WARDENS::remove);
            }
        }

        @SubscribeEvent
        public static void onStart(PlayerEvent.StartTracking event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                ServerPlayer player2 = (ServerPlayer)player;
                Entity target = event.getTarget();
                if (target instanceof Warden) {
                    Warden warden = (Warden)target;
                    CommonHandler.getOrCreate(warden).addPlayer(player2);
                }
            }
        }

        @SubscribeEvent
        public static void onStop(PlayerEvent.StopTracking event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                Warden warden;
                ServerBossEvent bossEvent;
                ServerPlayer player2 = (ServerPlayer)player;
                Entity target = event.getTarget();
                if (target instanceof Warden && (bossEvent = WARDENS.get((warden = (Warden)target).getUUID())) != null) {
                    bossEvent.removePlayer(player2);
                }
            }
        }

        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event) {
            Warden warden;
            ServerBossEvent bossEvent;
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity instanceof Warden && (bossEvent = WARDENS.get((warden = (Warden)livingEntity).getUUID())) != null) {
                bossEvent.setProgress(0.0f);
            }
        }
    }

    @EventBusSubscriber(value={Dist.CLIENT})
    public static class ClientHandler {
        public static final BossMusic HELVAR_MUSIC = new BossMusic(BossesRiseSounds.MUSIC_HELVAR);
        public static final BossMusic ASHLORD_MUSIC = new BossMusic(BossesRiseSounds.MUSIC_ASHLORD);
        public static final BossMusic SKOR_MUSIC = new BossMusic(BossesRiseSounds.MUSIC_SKOR);
        public static final BossMusic SIROK_MUSIC = new BossMusic(BossesRiseSounds.MUSIC_SIROK);
        public static final BossMusic NERAKYSS_MUSIC = new BossMusic(BossesRiseSounds.MUSIC_NERAKYSS);

        @SubscribeEvent(priority=EventPriority.LOWEST)
        public static void renderLayer(CustomizeGuiOverlayEvent.BossEventProgress event) {
            LerpingBossEvent bossEvent = event.getBossEvent();
            TrackedBoss boss = TRACKED_BOSSES.getOrDefault(event.getBossEvent().getId(), null);
            if (boss != null) {
                HealthBarStyle style;
                int type = boss.type();
                switch (type) {
                    case 0: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.WARDEN_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 1: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.ENDER_DRAGON_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 2: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.WITHER_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 3: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.YETI_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 4: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.DRAGON_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 5: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.SANDWORM_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 6: 
                    case 7: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.KNIGHT_BOSSBAR.get())).resolve();
                        break;
                    }
                    case 8: 
                    case 9: {
                        style = ((HealthBarStyle)((Object)ClientConfiguration.KRAKEN_BOSSBAR.get())).resolve();
                        break;
                    }
                    default: {
                        style = HealthBarStyle.VANILLA;
                    }
                }
                if (style == HealthBarStyle.VANILLA) {
                    return;
                }
                if (style == HealthBarStyle.OFF) {
                    event.setCanceled(true);
                    event.setIncrement(0);
                    return;
                }
                event.setCanceled(true);
                event.setIncrement(46);
                BossBar barInfo = INFO[type];
                GuiGraphics guiGraphics = event.getGuiGraphics();
                RenderSystem.enableBlend();
                guiGraphics.pose().pushPose();
                int progressScaled = (int)((float)barInfo.barStart() + bossEvent.getProgress() * (float)barInfo.barLength());
                if (style == HealthBarStyle.FANCY) {
                    guiGraphics.blit(barInfo.complexBase(), (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2, event.getY() - 14, 0.0f, 0.0f, barInfo.texWidth(), barInfo.texHeight(), barInfo.texWidth(), barInfo.texHeight());
                } else {
                    guiGraphics.blit(barInfo.simpleBase(), (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2, event.getY() - 14, 0.0f, 0.0f, barInfo.texWidth(), barInfo.texHeight(), barInfo.texWidth(), barInfo.texHeight());
                }
                guiGraphics.blit(barInfo.healthBar(), (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2, event.getY() - 14, 0.0f, 0.0f, progressScaled, barInfo.texHeight(), barInfo.texWidth(), barInfo.texHeight());
                guiGraphics.pose().popPose();
                RenderSystem.disableBlend();
            }
        }

        @SubscribeEvent
        public static void onMusicCheck(SelectMusicEvent event) {
            for (Map.Entry entry : Minecraft.getInstance().gui.getBossOverlay().events.entrySet()) {
                LerpingBossEvent lerpingBossEvent = (LerpingBossEvent)entry.getValue();
                TrackedBoss boss = TRACKED_BOSSES.getOrDefault(lerpingBossEvent.getId(), null);
                if (boss == null || !boss.playingMusic) continue;
                switch (boss.type) {
                    case 6: {
                        event.overrideMusic((Music)HELVAR_MUSIC);
                        return;
                    }
                    case 4: {
                        event.overrideMusic((Music)ASHLORD_MUSIC);
                        return;
                    }
                    case 3: {
                        event.overrideMusic((Music)SKOR_MUSIC);
                        return;
                    }
                    case 5: {
                        event.overrideMusic((Music)SIROK_MUSIC);
                        return;
                    }
                    case 8: 
                    case 9: {
                        event.overrideMusic((Music)NERAKYSS_MUSIC);
                        return;
                    }
                }
            }
            if (ClientHandler.isBossMusic(event.getPlayingMusic())) {
                event.setMusic(null);
            }
        }

        public static boolean isBossMusic(@Nullable SoundInstance instance) {
            if (instance == null) {
                return false;
            }
            ResourceLocation location = instance.getLocation();
            return BossMusic.contains(location);
        }

        public static class BossMusic
        extends Music {
            public static final List<Holder<SoundEvent>> BOSS_TRACKS = new ArrayList<Holder<SoundEvent>>();
            public static final List<ResourceLocation> SUPPLIED_BOSS_TRACKS = new ArrayList<ResourceLocation>();

            public BossMusic(Holder<SoundEvent> event) {
                super(event, 0, 0, true);
                BOSS_TRACKS.add(event);
            }

            public static boolean contains(ResourceLocation location) {
                if (SUPPLIED_BOSS_TRACKS.isEmpty()) {
                    for (Holder<SoundEvent> holder : BOSS_TRACKS) {
                        if (holder.isBound()) {
                            SUPPLIED_BOSS_TRACKS.add(((SoundEvent)holder.value()).getLocation());
                            continue;
                        }
                        SUPPLIED_BOSS_TRACKS.clear();
                        return false;
                    }
                }
                return SUPPLIED_BOSS_TRACKS.contains(location);
            }
        }
    }
}

