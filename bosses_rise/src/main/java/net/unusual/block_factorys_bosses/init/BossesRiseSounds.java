/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package net.unusual.block_factorys_bosses.init;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create((ResourceKey)Registries.SOUND_EVENT, (String)"block_factorys_bosses");
    public static final List<ResourceLocation> CANCELABLE_SOUNDS = new ArrayList<ResourceLocation>();
    public static final Holder<SoundEvent> MUSIC_SKOR = BossesRiseSounds.registerMusic("skor");
    public static final Holder<SoundEvent> YETI_INTRO = BossesRiseSounds.registerMusic("yeti_intro");
    public static final Holder<SoundEvent> YETI_FOOTSTEP = BossesRiseSounds.registerEntitySound("yeti.footstep");
    public static final Holder<SoundEvent> YETI_DEATH = BossesRiseSounds.registerEntitySound("yeti.death");
    public static final Holder<SoundEvent> YETI_GROUNDSMASH = BossesRiseSounds.registerEntitySound("yeti.groundsmash");
    public static final Holder<SoundEvent> YETI_HANDSWIPE = BossesRiseSounds.registerEntitySound("yeti.handswipe");
    public static final Holder<SoundEvent> YETI_HANDSWIPE2 = BossesRiseSounds.registerEntitySound("yeti.handswipe2");
    public static final Holder<SoundEvent> YETI_HIT = BossesRiseSounds.registerEntitySound("yeti.hit");
    public static final Holder<SoundEvent> YETI_PUNCH = BossesRiseSounds.registerEntitySound("yeti.punch");
    public static final Holder<SoundEvent> YETI_ENRAGE = BossesRiseSounds.registerEntitySound("yeti.enrage");
    public static final Holder<SoundEvent> YETI_SPAWN = BossesRiseSounds.registerEntitySound("yeti.spawn");
    public static final Holder<SoundEvent> YETI_JUMP = BossesRiseSounds.registerEntitySound("yeti.jump");
    public static final Holder<SoundEvent> YETI_LAND = BossesRiseSounds.registerEntitySound("yeti.land");
    public static final Holder<SoundEvent> YETI_RANGED_CONTROL = BossesRiseSounds.registerEntitySound("yeti.ranged_control");
    public static final Holder<SoundEvent> YETI_RANGED_CONTROL_ICE_BLOCK = BossesRiseSounds.registerEntitySound("yeti.ranged_control_ice_block");
    public static final Holder<SoundEvent> YETI_ICE_BARRAGE = BossesRiseSounds.registerEntitySound("yeti.ice_barrage");
    public static final Holder<SoundEvent> ICE_BARRAGE_WHIRLPOOL = BossesRiseSounds.registerEntitySound("yeti.ice_barrage_whirlpool");
    public static final Holder<SoundEvent> ICE_GAUNTLET_PUNCH = BossesRiseSounds.registerItemSound("ice_gauntlet.punch");
    public static final Holder<SoundEvent> ICE_GAUNTLET_BLAST_1 = BossesRiseSounds.registerItemSound("ice_gauntlet.blast_1");
    public static final Holder<SoundEvent> ICE_GAUNTLET_BLAST_2 = BossesRiseSounds.registerItemSound("ice_gauntlet.blast_2");
    public static final Holder<SoundEvent> THROW_ICICLE = BossesRiseSounds.registerItemSound("ice_gauntlet.throw_icicle");
    public static final Holder<SoundEvent> ICICLE_PICKUP = BossesRiseSounds.registerItemSound("ice_gauntlet.icicle_pickup");
    public static final Holder<SoundEvent> ICICLE_SPAWN = BossesRiseSounds.registerEntitySound("ice_gauntlet.icicle_spawning");
    public static final Holder<SoundEvent> ICICLE_HURT = BossesRiseSounds.registerEntitySound("ice_gauntlet.icicle_hurt");
    public static final Holder<SoundEvent> ICICLE_BREAK = BossesRiseSounds.registerEntitySound("ice_gauntlet.icicle_break");
    public static final Holder<SoundEvent> MUSIC_ASHLORD = BossesRiseSounds.registerMusic("ashlord");
    public static final Holder<SoundEvent> DRAGON_FOOTSTEP = BossesRiseSounds.registerEntitySound("dragon.footstep");
    public static final Holder<SoundEvent> DRAGON_BITE = BossesRiseSounds.registerEntitySound("dragon.bite");
    public static final Holder<SoundEvent> DRAGON_CLAW = BossesRiseSounds.registerEntitySound("dragon.claw");
    public static final Holder<SoundEvent> DRAGON_TAILSWIPE = BossesRiseSounds.registerEntitySound("dragon.tailswipe");
    public static final Holder<SoundEvent> DRAGON_WING = BossesRiseSounds.registerEntitySound("dragon.wing");
    public static final Holder<SoundEvent> DRAGON_HIT = BossesRiseSounds.registerEntitySound("dragon.hit");
    public static final Holder<SoundEvent> DRAGON_SPAWN = BossesRiseSounds.registerEntitySound("dragon.spawn");
    public static final Holder<SoundEvent> DRAGON_FIRELAYER = BossesRiseSounds.registerEntitySound("dragon.firelayer");
    public static final Holder<SoundEvent> DRAGON_TRANSITION = BossesRiseSounds.registerEntitySound("dragon.phase_transition");
    public static final Holder<SoundEvent> DRAGON_ROAR = BossesRiseSounds.registerEntitySound("dragon.roar");
    public static final Holder<SoundEvent> DRAGON_FIREBALLS = BossesRiseSounds.registerEntitySound("dragon.fireballs");
    public static final Holder<SoundEvent> DRAGON_DEATH = BossesRiseSounds.registerEntitySound("dragon.death");
    public static final Holder<SoundEvent> FIRE_SMELTED_ITEM = BossesRiseSounds.registerEntitySound("dragon.fire_smelted_item");
    public static final Holder<SoundEvent> MUSIC_HELVAR = BossesRiseSounds.registerMusic("helvar");
    public static final Holder<SoundEvent> KNIGHT_FOOTSTEP = BossesRiseSounds.registerEntitySound("knight.footstep");
    public static final Holder<SoundEvent> KNIGHT_INTRO = BossesRiseSounds.registerEntitySound("knight.intro");
    public static final Holder<SoundEvent> KNIGHT_HURT = BossesRiseSounds.registerEntitySound("knight.hurt");
    public static final Holder<SoundEvent> KNIGHT_BLOCK = BossesRiseSounds.registerEntitySound("knight.block");
    public static final Holder<SoundEvent> KNIGHT_ATTACK_VOICELINE = BossesRiseSounds.registerEntitySound("knight.attack_voiceline");
    public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_1 = BossesRiseSounds.registerEntitySound("knight.jumpspin_1", true);
    public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_2 = BossesRiseSounds.registerEntitySound("knight.jumpspin_2", true);
    public static final Holder<SoundEvent> KNIGHT_JUMPSPIN_3 = BossesRiseSounds.registerEntitySound("knight.jumpspin_3", true);
    public static final Holder<SoundEvent> KNIGHT_INTRODUCTION_ATTACK = BossesRiseSounds.registerEntitySound("knight.introduction_attack", true);
    public static final Holder<SoundEvent> KNIGHT_VERTICAL_SLASH = BossesRiseSounds.registerEntitySound("knight.vertical_slash", true);
    public static final Holder<SoundEvent> KNIGHT_VERTICAL_SLASH_TRANS = BossesRiseSounds.registerEntitySound("knight.vertical_slash_transition", true);
    public static final Holder<SoundEvent> KNIGHT_HORIZONTAL_SLASH = BossesRiseSounds.registerEntitySound("knight.horizontal_slash", true);
    public static final Holder<SoundEvent> KNIGHT_HORIZONTAL_SLASH_TRANS = BossesRiseSounds.registerEntitySound("knight.horizontal_slash_transition", true);
    public static final Holder<SoundEvent> KNIGHT_DODGE = BossesRiseSounds.registerEntitySound("knight.dodge");
    public static final Holder<SoundEvent> KNIGHT_KNOCKED_DOWN = BossesRiseSounds.registerEntitySound("knight.knocked_down");
    public static final Holder<SoundEvent> KNIGHT_COMBO_1 = BossesRiseSounds.registerEntitySound("knight.combo1", true);
    public static final Holder<SoundEvent> KNIGHT_COMBO_2 = BossesRiseSounds.registerEntitySound("knight.combo2", true);
    public static final Holder<SoundEvent> KNIGHT_STACK_REMOVE = BossesRiseSounds.registerEntitySound("knight.stack_remove", true);
    public static final Holder<SoundEvent> KNIGHT_THRUST = BossesRiseSounds.registerEntitySound("knight.thrust", true);
    public static final Holder<SoundEvent> KNIGHT_LIGHT_ATTACK_1 = BossesRiseSounds.registerEntitySound("knight.light_attack_1", true);
    public static final Holder<SoundEvent> KNIGHT_LIGHT_ATTACK_2 = BossesRiseSounds.registerEntitySound("knight.light_attack_2", true);
    public static final Holder<SoundEvent> KNIGHT_HEAVY_ATTACK = BossesRiseSounds.registerEntitySound("knight.heavy_attack", true);
    public static final Holder<SoundEvent> KNIGHT_LIGHT_HEAVY_ATTACK = BossesRiseSounds.registerEntitySound("knight.light_heavy_attack", true);
    public static final Holder<SoundEvent> KNIGHT_RESURRECT = BossesRiseSounds.registerEntitySound("knight.resurrection");
    public static final Holder<SoundEvent> KNIGHT_REVENGE_ATTACK = BossesRiseSounds.registerEntitySound("knight.revenge_attack", true);
    public static final Holder<SoundEvent> KNIGHT_KNOCK_OFF = BossesRiseSounds.registerEntitySound("knight.knock_off");
    public static final Holder<SoundEvent> KNIGHT_DEATH = BossesRiseSounds.registerEntitySound("knight.death");
    public static final Holder<SoundEvent> KNIGHT_FAKE_DEATH = BossesRiseSounds.registerEntitySound("knight.fake_death");
    public static final Holder<SoundEvent> WISP_EXPLODE = BossesRiseSounds.registerEntitySound("knight.wisp_explode");
    public static final Holder<SoundEvent> WISP_EXPLODE_LAST = BossesRiseSounds.registerEntitySound("knight.wisp_explode_last");
    public static final Holder<SoundEvent> RIFT_OPEN = BossesRiseSounds.registerEntitySound("barrage_rift.rift_open");
    public static final Holder<SoundEvent> RIFT_IDLE = BossesRiseSounds.registerEntitySound("barrage_rift.rift_idle");
    public static final Holder<SoundEvent> RIFT_CLOSE = BossesRiseSounds.registerEntitySound("barrage_rift.closing");
    public static final Holder<SoundEvent> RIFT_SLASH = BossesRiseSounds.registerEntitySound("barrage_rift.slash_air");
    public static final Holder<SoundEvent> RIFT_JUMP = BossesRiseSounds.registerEntitySound("barrage_rift.jump");
    public static final Holder<SoundEvent> RIFT_FIREBALL = BossesRiseSounds.registerEntitySound("barrage_rift.rift_fireball");
    public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_OPEN = BossesRiseSounds.registerBlockSound("underworld_arena_door.open");
    public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_KEY_INSERTION = BossesRiseSounds.registerBlockSound("underworld_arena_door.key_insertion");
    public static final Holder<SoundEvent> UNDERWORLD_ARENA_DOOR_CLOSE = BossesRiseSounds.registerBlockSound("underworld_arena_door.close");
    public static final Holder<SoundEvent> MUSIC_SIROK = BossesRiseSounds.registerMusic("sirok");
    public static final Holder<SoundEvent> SAND_COLUMN_ERUPTION = BossesRiseSounds.registerEntitySound("sand_column.eruption");
    public static final Holder<SoundEvent> SANDWORM_AMBIENT = BossesRiseSounds.registerEntitySound("sandworm.ambient");
    public static final Holder<SoundEvent> SANDWORM_BITE = BossesRiseSounds.registerEntitySound("sandworm.bite");
    public static final Holder<SoundEvent> SANDWORM_BODY_IMPACT = BossesRiseSounds.registerEntitySound("sandworm.impact");
    public static final Holder<SoundEvent> SANDWORM_CHARGE = BossesRiseSounds.registerEntitySound("sandworm.charge");
    public static final Holder<SoundEvent> SANDWORM_DEATH = BossesRiseSounds.registerEntitySound("sandworm.death");
    public static final Holder<SoundEvent> SANDWORM_DIVE = BossesRiseSounds.registerEntitySound("sandworm.dive");
    public static final Holder<SoundEvent> SANDWORM_DIVE_LONG = BossesRiseSounds.registerEntitySound("sandworm.dive_long");
    public static final Holder<SoundEvent> SANDWORM_DIVE_SHORT = BossesRiseSounds.registerEntitySound("sandworm.dive_short");
    public static final Holder<SoundEvent> SANDWORM_EMERGE = BossesRiseSounds.registerEntitySound("sandworm.emerge");
    public static final Holder<SoundEvent> SANDWORM_HURT = BossesRiseSounds.registerEntitySound("sandworm.hurt");
    public static final Holder<SoundEvent> SANDWORM_POISON_SPIT = BossesRiseSounds.registerEntitySound("sandworm.poison_spit");
    public static final Holder<SoundEvent> SANDWORM_SCREECH = BossesRiseSounds.registerEntitySound("sandworm.screech");
    public static final Holder<SoundEvent> SANDWORM_TAIL_IMPACT = BossesRiseSounds.registerEntitySound("sandworm.tail_impact");
    public static final Holder<SoundEvent> SANDWORM_GAUNTLET_POISON_BARRAGE_RELEASE = BossesRiseSounds.registerItemSound("sandworm_gauntlet.poison_barrage_release");
    public static final Holder<SoundEvent> MUSIC_NERAKYSS = BossesRiseSounds.registerMusic("nerakyss");
    public static final Holder<SoundEvent> KRAKEN_INTRO = BossesRiseSounds.registerEntitySound("kraken.intro");
    public static final Holder<SoundEvent> KRAKEN_DEATH = BossesRiseSounds.registerEntitySound("kraken.death");
    public static final Holder<SoundEvent> KRAKEN_DEATH_FINAL = BossesRiseSounds.registerEntitySound("kraken.death_final");
    public static final Holder<SoundEvent> KRAKEN_GRAB_AND_THROW = BossesRiseSounds.registerEntitySound("kraken.grab_and_throw");
    public static final Holder<SoundEvent> KRAKEN_GROWLING = BossesRiseSounds.registerEntitySound("kraken.growling");
    public static final Holder<SoundEvent> KRAKEN_IDLE = BossesRiseSounds.registerEntitySound("kraken.idle");
    public static final Holder<SoundEvent> KRAKEN_LIGHT_SLAM = BossesRiseSounds.registerEntitySound("kraken.light_slam");
    public static final Holder<SoundEvent> KRAKEN_STRONG_SLAM = BossesRiseSounds.registerEntitySound("kraken.strong_slam");
    public static final Holder<SoundEvent> KRAKEN_PHASE_TRANS = BossesRiseSounds.registerEntitySound("kraken.phase_transition");
    public static final Holder<SoundEvent> KRAKEN_WARNING = BossesRiseSounds.registerEntitySound("kraken.warning");
    public static final Holder<SoundEvent> KRAKEN_TENTACLE_DEATH = BossesRiseSounds.registerEntitySound("kraken.tentacle_death");
    public static final Holder<SoundEvent> KRAKEN_WATER_MOVEMENT = BossesRiseSounds.registerEntitySound("kraken.water_movement");
    public static final Holder<SoundEvent> CRATE_LAND = BossesRiseSounds.registerEntitySound("crate.land");
    public static final Holder<SoundEvent> CANNON_FIRE = BossesRiseSounds.registerEntitySound("cannon.fire");
    public static final Holder<SoundEvent> CANNON_DISMOUNT = BossesRiseSounds.registerEntitySound("cannon.get_out");
    public static final Holder<SoundEvent> CANNON_IMPACT = BossesRiseSounds.registerEntitySound("cannon.impact");
    public static final Holder<SoundEvent> CANNON_RELOAD = BossesRiseSounds.registerEntitySound("cannon.reload");
    public static final Holder<SoundEvent> CANNON_SEAT = BossesRiseSounds.registerEntitySound("cannon.seat");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_BACK = BossesRiseSounds.registerItemSound("undying_tentacle.throw_back");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_FORTH = BossesRiseSounds.registerItemSound("undying_tentacle.throw_forth");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_THROW_GRAB = BossesRiseSounds.registerItemSound("undying_tentacle.throw_grab");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_CHARGING = BossesRiseSounds.registerItemSound("undying_tentacle.charging");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_DISAPPEARING = BossesRiseSounds.registerItemSound("undying_tentacle.disappearing");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_SMASH_GROUND = BossesRiseSounds.registerItemSound("undying_tentacle.smash_ground");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_SPAWN_TENTACLES = BossesRiseSounds.registerItemSound("undying_tentacle.spawn_tentacles");
    public static final Holder<SoundEvent> UNDYING_TENTACLE_TENTACLE_STRIKE_THE_GROUND = BossesRiseSounds.registerItemSound("undying_tentacle.tentacle_strike_the_ground");
    public static final Holder<SoundEvent> SILENCE = BossesRiseSounds.registerEntitySound("silence");
    public static final Holder<SoundEvent> LARGE_SWORD_SWING = BossesRiseSounds.registerItemSound("large_sword_swing");
    public static final Holder<SoundEvent> KNIGHT_SWORD_SWING = BossesRiseSounds.registerItemSound("knight_sword_swing");
    public static final Holder<SoundEvent> DAGGER_STAB = BossesRiseSounds.registerItemSound("dagger_stab");
    public static final Holder<SoundEvent> WARRIOR_SWORD_SWING = BossesRiseSounds.registerItemSound("warrior_sword_swing");
    public static final Holder<SoundEvent> ROLL = BossesRiseSounds.registerEntitySound("roll");

    private static Holder<SoundEvent> registerMusic(String name) {
        String fullName = BossesRiseSounds.prefix("music", name);
        return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent((ResourceLocation)BossesRise.prefix(fullName)));
    }

    private static Holder<SoundEvent> registerItemSound(String name) {
        String fullName = BossesRiseSounds.prefix("item", name);
        return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent((ResourceLocation)BossesRise.prefix(fullName)));
    }

    private static Holder<SoundEvent> registerBlockSound(String name) {
        String fullName = BossesRiseSounds.prefix("block", name);
        return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent((ResourceLocation)BossesRise.prefix(fullName)));
    }

    private static Holder<SoundEvent> registerEntitySound(String name) {
        return BossesRiseSounds.registerEntitySound(name, false);
    }

    private static Holder<SoundEvent> registerEntitySound(String name, boolean cancelable) {
        String fullName = BossesRiseSounds.prefix("entity", name);
        if (cancelable) {
            CANCELABLE_SOUNDS.add(BossesRise.prefix(fullName));
        }
        return SOUND_EVENTS.register(fullName, () -> SoundEvent.createVariableRangeEvent((ResourceLocation)BossesRise.prefix(fullName)));
    }

    private static String prefix(String soundType, String soundName) {
        return soundType + "." + soundName;
    }
}

