/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.common.custom.CustomPacketPayload
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.util.INBTSerializable
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Post
 *  net.neoforged.neoforge.network.PacketDistributor
 *  org.jetbrains.annotations.UnknownNullability
 */
package net.unusual.block_factorys_bosses.attachment.entity;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.network.PlayerAnimationMessage;
import org.jetbrains.annotations.UnknownNullability;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlayerAnimationHandler
implements INBTSerializable<CompoundTag> {
    protected final List<TimedPlayerAnimation> queuedAnimations = new ArrayList<TimedPlayerAnimation>();
    @Nullable
    protected PlayerAnimation currentAnimation = null;
    protected int duration = 0;
    protected int tick = 0;

    public static PlayerAnimationHandler fromPlayer(Player player) {
        return net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.PLAYER_ANIMATION);
    }

    public int getDuration() {
        return this.duration;
    }

    public int getTick() {
        return this.tick;
    }

    public boolean canLerp() {
        return this.queuedAnimations.isEmpty() && (this.currentAnimation == null || !this.currentAnimation.loop());
    }

    @Nullable
    public PlayerAnimation getCurrentAnimation() {
        if (this.tick <= 0) {
            return null;
        }
        return this.currentAnimation;
    }

    public void startAnimation(Player player, QueuedPlayerAnimation animation) {
        this.queuedAnimations.clear();
        this.queuedAnimations.addAll(animation.queue());
        if (this.queuedAnimations.isEmpty()) {
            this.currentAnimation = null;
            return;
        }
        TimedPlayerAnimation timedAnimation = (TimedPlayerAnimation)this.queuedAnimations.removeFirst();
        this.currentAnimation = timedAnimation.playerAnimation();
        this.tick = this.duration = timedAnimation.duration();
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)serverPlayer, (CustomPacketPayload)new PlayerAnimationMessage(animation, serverPlayer.getId()), (CustomPacketPayload[])new CustomPacketPayload[0]);
        }
    }

    public void startAnimation(Player player, PlayerAnimation animation, int duration) {
        this.queuedAnimations.clear();
        this.currentAnimation = animation;
        this.tick = this.duration = duration;
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            PacketDistributor.sendToPlayersTrackingEntityAndSelf((Entity)serverPlayer, (CustomPacketPayload)new PlayerAnimationMessage(new TimedPlayerAnimation(animation, duration), serverPlayer.getId()), (CustomPacketPayload[])new CustomPacketPayload[0]);
        }
    }

    public void tick() {
        if (this.tick > 0 && --this.tick <= 0) {
            if (this.currentAnimation != null && this.currentAnimation.loop) {
                this.tick = this.duration;
            } else if (!this.queuedAnimations.isEmpty()) {
                TimedPlayerAnimation timedAnimation = (TimedPlayerAnimation)this.queuedAnimations.removeFirst();
                this.currentAnimation = timedAnimation.playerAnimation();
                this.tick = this.duration = timedAnimation.duration();
            }
        }
    }

    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("duration", this.duration);
        tag.putInt("tick", this.tick);
        if (this.currentAnimation != null) {
            tag.putString("path", this.currentAnimation.path().toString());
            tag.putString("name", this.currentAnimation.name());
            tag.putFloat("lerp", this.currentAnimation.lerp());
            tag.putBoolean("loop", this.currentAnimation.loop());
        }
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.duration = tag.getInt("duration");
        this.tick = tag.getInt("tick");
        if (!tag.contains("path")) {
            return;
        }
        ResourceLocation path = ResourceLocation.tryParse((String)tag.getString("path"));
        if (path == null) {
            return;
        }
        this.currentAnimation = new PlayerAnimation(path, tag.getString("name"), tag.getFloat("lerp"), tag.getBoolean("loop"));
    }

    @SubscribeEvent
    private static void tickEvent(PlayerTickEvent.Post event) {
        PlayerAnimationHandler.fromPlayer(event.getEntity()).tick();
    }

    public record PlayerAnimation(ResourceLocation path, String name, float lerp, boolean loop) {
        public static final Codec<PlayerAnimation> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group(ResourceLocation.CODEC.fieldOf("path").forGetter(PlayerAnimation::path), Codec.STRING.fieldOf("name").forGetter(PlayerAnimation::name), Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("lerp").forGetter(PlayerAnimation::lerp), Codec.BOOL.fieldOf("loop").forGetter(PlayerAnimation::loop)).apply(recordCodecBuilder, PlayerAnimation::new));

        public PlayerAnimation(ResourceLocation path, String name, float lerp) {
            this(path, name, lerp, false);
        }

        public PlayerAnimation(ResourceLocation path, String name, boolean loop) {
            this(path, name, 1.0f, loop);
        }

        public PlayerAnimation(ResourceLocation path, String name) {
            this(path, name, 1.0f, false);
        }
    }

    public record QueuedPlayerAnimation(List<TimedPlayerAnimation> queue) {
        public static final Codec<QueuedPlayerAnimation> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group(TimedPlayerAnimation.CODEC.listOf().fieldOf("queue").forGetter(QueuedPlayerAnimation::queue)).apply(recordCodecBuilder, QueuedPlayerAnimation::new));
    }

    public record TimedPlayerAnimation(PlayerAnimation playerAnimation, int duration) {
        public static final Codec<TimedPlayerAnimation> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group(PlayerAnimation.CODEC.fieldOf("player_animation").forGetter(TimedPlayerAnimation::playerAnimation), Codec.INT.fieldOf("duration").forGetter(TimedPlayerAnimation::duration)).apply(recordCodecBuilder, TimedPlayerAnimation::new));
    }
}

