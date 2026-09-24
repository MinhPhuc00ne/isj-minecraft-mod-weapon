/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.common.util.INBTSerializable
 *  net.neoforged.neoforge.event.entity.ProjectileImpactEvent
 *  net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent
 *  net.neoforged.neoforge.event.entity.living.MobEffectEvent$Applicable
 *  net.neoforged.neoforge.event.entity.living.MobEffectEvent$Applicable$Result
 *  net.neoforged.neoforge.event.tick.PlayerTickEvent$Post
 *  org.jetbrains.annotations.UnknownNullability
 *  org.joml.Vector2d
 */
package net.unusual.block_factorys_bosses.attachment.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.configuration.ServerConfiguration;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Vector2d;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RollAttachment
implements INBTSerializable<CompoundTag> {
    private static final int DEFAULT_ROLL_DURATION = 14;
    private static final int ROLL_I_FRAMES = 7;
    public static final int ROLL_DELAY = 5;
    public static final double ROLL_DISTANCE = 6.0;
    protected static final ResourceLocation PATH = BossesRise.prefix("animations/player/rolls.animation.json");
    protected static final PlayerAnimationHandler.PlayerAnimation FORWARD_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_forward");
    protected static final PlayerAnimationHandler.PlayerAnimation BACKWARD_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_back");
    protected static final PlayerAnimationHandler.PlayerAnimation LEFT_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_left");
    protected static final PlayerAnimationHandler.PlayerAnimation RIGHT_ROLL = new PlayerAnimationHandler.PlayerAnimation(PATH, "roll_right");
    protected RollType direction = RollType.FORWARD;
    protected int rollDuration = 14;
    protected int roll = 0;
    protected List<Float> cooldowns = new ArrayList<Float>();

    public static RollAttachment fromPlayer(Player player) {
        return net.neoforged.neoforge.attachment.AttachmentHolder.getData(player, BossesRiseDataAttachments.ROLL_ATTACHMENT);
    }

    public int rollCount() {
        return this.cooldowns.size();
    }

    public float getCooldown(int i) {
        return this.cooldowns.get(i).floatValue();
    }

    public void startCooldown(int i) {
        this.cooldowns.set(i, Float.valueOf(1.0f));
        Collections.sort(this.cooldowns);
    }

    public int getRoll() {
        return this.roll;
    }

    public boolean isRolling() {
        return this.roll > 0;
    }

    public boolean isInvulnerable() {
        return this.roll > 7;
    }

    public int getRollDuration() {
        return this.rollDuration;
    }

    public RollType getDirection() {
        return this.direction;
    }

    public boolean startRoll(Player player, float leftImpulse, float forwardImpulse) {
        if (this.roll > -5) {
            return false;
        }
        if (!player.onGround()) {
            return false;
        }
        if (!player.isSilent()) {
            player.playSound((SoundEvent)BossesRiseSounds.ROLL.value(), 1.0f, 1.0f);
        }
        player.setPose(Pose.CROUCHING);
        this.roll = this.rollDuration = 14;
        float speed = player.getSpeed();
        float left = speed * leftImpulse;
        float forward = speed * forwardImpulse;
        float sin = Mth.sin((float)(player.getYRot() * ((float)Math.PI / 180)));
        float cos = Mth.cos((float)(player.getYRot() * ((float)Math.PI / 180)));
        Vector2d move = new Vector2d((double)(left * cos - forward * sin), (double)(forward * cos + left * sin));
        if (move.lengthSquared() <= (double)0.001f) {
            move = new Vector2d((double)(-sin), (double)cos);
            this.direction = RollType.FORWARD;
        } else {
            this.direction = Math.abs(forwardImpulse) >= Math.abs(leftImpulse) ? (forwardImpulse > 0.0f ? RollType.FORWARD : RollType.BACKWARD) : (leftImpulse > 0.0f ? RollType.LEFT : RollType.RIGHT);
        }
        this.move(player, move);
        PlayerAnimationHandler.PlayerAnimation animation = switch (this.direction.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> FORWARD_ROLL;
            case 1 -> BACKWARD_ROLL;
            case 2 -> LEFT_ROLL;
            case 3 -> RIGHT_ROLL;
        };
        PlayerAnimationHandler.fromPlayer(player).startAnimation(player, animation, this.rollDuration);
        return true;
    }

    public void tick(Player player) {
        block13: {
            block12: {
                int diff;
                int stat;
                if (this.isRolling()) {
                    player.setShiftKeyDown(false);
                    if (player.onGround()) {
                        double radians = Math.toRadians(180.0 - (Math.atan2(player.getDeltaMovement().x(), player.getDeltaMovement().z()) * -57.29577951308232 + 180.0));
                        this.move(player, new Vector2d(Math.sin(radians) * 0.4, Math.cos(radians) * 0.4));
                    }
                }
                if ((stat = (Integer)ServerConfiguration.DEFAULT_ROLL_COUNT.get() + (int)player.getAttributeValue(BossesRiseAttributes.ROLL_COUNT)) < 0) {
                    stat = 0;
                }
                if ((diff = stat - this.rollCount()) > 0) {
                    for (int i = 0; i < diff; ++i) {
                        this.cooldowns.add(Float.valueOf(0.0f));
                    }
                    Collections.sort(this.cooldowns);
                } else if (diff < 0) {
                    for (int i = 0; i > diff; --i) {
                        this.cooldowns.removeLast();
                    }
                    Collections.sort(this.cooldowns);
                }
                if (this.roll <= -5) break block12;
                boolean wasRolling = this.isRolling();
                --this.roll;
                if (!wasRolling || this.isRolling()) break block13;
                Vec3 delta = player.getDeltaMovement();
                Vector2d horizontal = new Vector2d(delta.x, delta.z).normalize().mul(player.getAttributeValue(Attributes.MOVEMENT_SPEED));
                player.setDeltaMovement(horizontal.x, delta.y, horizontal.y);
                player.hasImpulse = true;
                if (player.getPose() == Pose.CROUCHING) {
                    player.setPose(Pose.STANDING);
                }
                break block13;
            }
            int rollCount = this.rollCount();
            if (rollCount < 1) {
                return;
            }
            for (int i = 0; i < rollCount; ++i) {
                if (!(this.cooldowns.get(i).floatValue() > 0.0f)) continue;
                float cooldownInTicks = ((Integer)ServerConfiguration.ROLL_COOLDOWN.get()).intValue();
                if (cooldownInTicks < 0.0f) {
                    this.cooldowns.set(i, Float.valueOf(0.0f));
                    break;
                }
                this.cooldowns.set(i, Float.valueOf(this.cooldowns.get(i).floatValue() - 1.0f / cooldownInTicks));
                break;
            }
        }
    }

    private void move(Player player, Vector2d move) {
        Vec3 delta = player.getDeltaMovement();
        Vector2d horizontal = new Vector2d(delta.x + move.x, delta.z + move.y).normalize().mul(player.getAttributeValue(Attributes.MOVEMENT_SPEED) * (6.0 * (double)(10.0f / (float)this.rollDuration)));
        player.setDeltaMovement(horizontal.x, delta.y, horizontal.y);
        player.hasImpulse = true;
        float rot = player.yHeadRot;
        player.setYRot(player.getYHeadRot());
        player.yRotO = rot;
        player.setYBodyRot(player.getYHeadRot());
        player.yBodyRotO = rot;
    }

    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("direction", this.direction.ordinal());
        tag.putInt("duration", this.rollDuration);
        tag.putInt("roll", this.roll);
        tag.putInt("roll_count", this.rollCount());
        for (int i = 0; i < this.rollCount(); ++i) {
            tag.putFloat("cooldown_" + i, this.cooldowns.get(i).floatValue());
        }
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.direction = RollType.values()[tag.getInt("direction")];
        this.rollDuration = tag.getInt("duration");
        this.roll = tag.getInt("roll");
        this.cooldowns = new ArrayList<Float>();
        int count = tag.getInt("roll_count");
        for (int i = 0; i < count; ++i) {
            this.cooldowns.add(Float.valueOf(tag.getFloat("cooldown_" + i)));
        }
    }

    @SubscribeEvent
    private static void tickEvent(PlayerTickEvent.Post event) {
        RollAttachment.fromPlayer(event.getEntity()).tick(event.getEntity());
    }

    @SubscribeEvent
    private static void preDamage(LivingIncomingDamageEvent event) {
        Player player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player && RollAttachment.fromPlayer(player = (Player)livingEntity).isInvulnerable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void preImpact(ProjectileImpactEvent event) {
        Player player;
        Entity entity = event.getEntity();
        if (entity instanceof Player && RollAttachment.fromPlayer(player = (Player)entity).isInvulnerable()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void preEffect(MobEffectEvent.Applicable event) {
        Player player;
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player && RollAttachment.fromPlayer(player = (Player)livingEntity).isInvulnerable()) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    public static enum RollType implements StringRepresentable
    {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT;


        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}

