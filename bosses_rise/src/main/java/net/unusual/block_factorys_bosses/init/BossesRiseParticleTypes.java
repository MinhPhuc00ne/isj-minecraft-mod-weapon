/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.MapCodec
 *  javax.annotation.Nonnull
 *  net.minecraft.core.particles.ColorParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.codec.StreamCodec
 *  net.minecraft.resources.ResourceKey
 *  net.neoforged.neoforge.registries.DeferredHolder
 *  net.neoforged.neoforge.registries.DeferredRegister
 */
package net.unusual.block_factorys_bosses.init;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockEmitterParticleOptions;

public class BossesRiseParticleTypes {
    private static SimpleParticleType simple(boolean alwaysShow) {
        return net.fabricmc.fabric.api.particle.v1.FabricParticleTypes.simple(alwaysShow);
    }
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create((ResourceKey)Registries.PARTICLE_TYPE, (String)"block_factorys_bosses");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOW_CLOUD = REGISTRY.register("snow_cloud", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOW_FLAKE = REGISTRY.register("snow_flake", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_DUST = REGISTRY.register("falling_dust", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_FLAME = REGISTRY.register("blazing_flame", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FANCY_SMOKE = REGISTRY.register("fancy_smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_DUST = REGISTRY.register("blazing_dust", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLAZING_CLOUD = REGISTRY.register("blazing_cloud", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_SMOKE = REGISTRY.register("soul_smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGICAL_DOT = REGISTRY.register("magical_dot", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_CLOUD = REGISTRY.register("soul_cloud", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_FALLING_DUST = REGISTRY.register("black_falling_dust", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DUST_CLOUD = REGISTRY.register("dust_cloud", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GROUND_DUST = REGISTRY.register("ground_dust", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SAND_ROCK = REGISTRY.register("sand_rock", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> POISON_SPIT = REGISTRY.register("poison_spit", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INVUL_WISP = REGISTRY.register("invul_wisp", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INVUL_WISP_INACTIVE = REGISTRY.register("invul_wisp_inactive", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, ParticleType<ColorParticleOption>> PIXEL = REGISTRY.register("pixel", () -> BossesRiseParticleTypes.register(false, ColorParticleOption::codec, ColorParticleOption::streamCodec));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT = REGISTRY.register("rift", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT_OPEN = REGISTRY.register("rift_open", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RIFT_CLOSE = REGISTRY.register("rift_close", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL = REGISTRY.register("soul", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_FLIP = REGISTRY.register("soul_flip", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BIG_STAR = REGISTRY.register("big_star", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STAR = REGISTRY.register("star", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WEIRD_SMOKE = REGISTRY.register("weird_smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXPLOSION = REGISTRY.register("explosion", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BIG_EXPLOSION = REGISTRY.register("big_explosion", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLINT = REGISTRY.register("glint", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMOKE = REGISTRY.register("smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_SMOKE = REGISTRY.register("red_smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_SPARK = REGISTRY.register("red_spark", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_1 = REGISTRY.register("ice_whirlpool_1", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_2 = REGISTRY.register("ice_whirlpool_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_3 = REGISTRY.register("ice_whirlpool_3", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_4 = REGISTRY.register("ice_whirlpool_4", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_WHIRLPOOL_5 = REGISTRY.register("ice_whirlpool_5", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_SMOKE_IMPACT_1 = REGISTRY.register("yeti_smoke_impact_1", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_SMOKE_IMPACT_2 = REGISTRY.register("yeti_smoke_impact_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SOLID = REGISTRY.register("yeti_death_solid", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SOFT = REGISTRY.register("yeti_death_soft", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_SHOCKWAVE = REGISTRY.register("yeti_death_shockwave", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_LIGHT = REGISTRY.register("yeti_death_light", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_DEATH_FROST = REGISTRY.register("yeti_death_frost", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YETI_ICE_CHUNK = REGISTRY.register("yeti_ice_chunk", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_AOE = REGISTRY.register("fire_aoe", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_CLOUD_SMOKE = REGISTRY.register("fire_cloud_smoke", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_CLOUD = REGISTRY.register("fire_cloud", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RING = REGISTRY.register("ring", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_EVENT = REGISTRY.register("introduction_attack_event", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_2 = REGISTRY.register("introduction_attack_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_3 = REGISTRY.register("introduction_attack_3", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_INTRODUCTION_ATTACK_DUST = REGISTRY.register("introduction_attack_dust", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOUL_ONCE = REGISTRY.register("introduction_attack_soul_once", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS = REGISTRY.register("introduction_attack_souls", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_2 = REGISTRY.register("introduction_attack_souls_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_3 = REGISTRY.register("introduction_attack_souls_3", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_3_DIED = REGISTRY.register("introduction_attack_souls_3_died", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INTRODUCTION_ATTACK_SOULS_DIED_2 = REGISTRY.register("introduction_attack_souls_died_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_SMOKE_IMPACT_1 = REGISTRY.register("knight_smoke_impact_1", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> KNIGHT_SMOKE_IMPACT_2 = REGISTRY.register("knight_smoke_impact_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_TRAIL = REGISTRY.register("soul_trail", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT = REGISTRY.register("mark_glint", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_2 = REGISTRY.register("mark_glint_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_3 = REGISTRY.register("mark_glint_3", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_EXP = REGISTRY.register("mark_glint_exp", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MARK_GLINT_EXP_2 = REGISTRY.register("mark_glint_exp_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_1 = REGISTRY.register("phase_transition_1", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_2 = REGISTRY.register("phase_transition_2", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_4 = REGISTRY.register("phase_transition_4", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_6 = REGISTRY.register("phase_transition_6", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_6_LOW = REGISTRY.register("phase_transition_6_low", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_7 = REGISTRY.register("phase_transition_7", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_8 = REGISTRY.register("phase_transition_8", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_9 = REGISTRY.register("phase_transition_9", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PHASE_TRANSITION_10 = REGISTRY.register("phase_transition_10", () -> simple(false));
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> AREA_BUBBLING = BossesRiseParticleTypes.registerBedrockParticle("area_bubbling");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> BITE_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("bite_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> BITE_DUST = BossesRiseParticleTypes.registerBedrockParticle("bite_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("crate_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_IMPACT_DUST = BossesRiseParticleTypes.registerBedrockParticle("crate_impact_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> CRATE_THROW_DUST = BossesRiseParticleTypes.registerBedrockParticle("crate_throw_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM = BossesRiseParticleTypes.registerBedrockParticle("emergence_water_foam");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_BIG = BossesRiseParticleTypes.registerBedrockParticle("emergence_water_foam_big");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_GIANT = BossesRiseParticleTypes.registerBedrockParticle("emergence_water_foam_giant");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_FOAM_MEDIUM = BossesRiseParticleTypes.registerBedrockParticle("emergence_water_foam_medium");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> EMERGENCE_WATER_GIANT = BossesRiseParticleTypes.registerBedrockParticle("emergence_water_giant");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> INK_FOG = BossesRiseParticleTypes.registerBedrockParticle("ink_fog");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_HEAD_STARS = BossesRiseParticleTypes.registerBedrockParticle("kraken_head_stars");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_PIRATE_SUMMON = BossesRiseParticleTypes.registerBedrockParticle("kraken_pirate_summon");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> KRAKEN_SPIT = BossesRiseParticleTypes.registerBedrockParticle("kraken_spit");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("mast_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DEBRIS_BIG = BossesRiseParticleTypes.registerBedrockParticle("mast_debris_big");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DUST = BossesRiseParticleTypes.registerBedrockParticle("mast_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MAST_DUST_BIG = BossesRiseParticleTypes.registerBedrockParticle("mast_dust_big");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> MIST_CLOUD = BossesRiseParticleTypes.registerBedrockParticle("mist_cloud");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> STRONG_SLAM_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("strong_slam_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> STRONG_SLAM_DUST = BossesRiseParticleTypes.registerBedrockParticle("strong_slam_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_EMERGENCE_SPLASH = BossesRiseParticleTypes.registerBedrockParticle("tentacle_emergence_splash");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TOWERING_MIST_CLOUD = BossesRiseParticleTypes.registerBedrockParticle("towering_mist_cloud");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_SPLASH_BIG = BossesRiseParticleTypes.registerBedrockParticle("transition_emergence_splash_big");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM = BossesRiseParticleTypes.registerBedrockParticle("transition_emergence_water_foam");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM_LONG = BossesRiseParticleTypes.registerBedrockParticle("transition_emergence_water_foam_long");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_EMERGENCE_WATER_FOAM_TENTACLE_LONG = BossesRiseParticleTypes.registerBedrockParticle("transition_emergence_water_foam_tentacle_long");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_MAST_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("transition_mast_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_MAST_DUST = BossesRiseParticleTypes.registerBedrockParticle("transition_mast_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_TENTACLE_EMERGENCE_SPLASH = BossesRiseParticleTypes.registerBedrockParticle("transition_tentacle_emergence_splash");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_WATER_BUBBLE = BossesRiseParticleTypes.registerBedrockParticle("transition_water_bubble");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TRANSITION_WATER_BUBBLE_FOAM = BossesRiseParticleTypes.registerBedrockParticle("transition_water_bubble_foam");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_BUBBLE_FOAM = BossesRiseParticleTypes.registerBedrockParticle("water_bubble_foam");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_BURST = BossesRiseParticleTypes.registerBedrockParticle("water_burst");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_FOAM = BossesRiseParticleTypes.registerBedrockParticle("water_foam");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WATER_SPLASH = BossesRiseParticleTypes.registerBedrockParticle("water_splash");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WOOD_DEBRIS = BossesRiseParticleTypes.registerBedrockParticle("wood_debris");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> WOOD_DUST = BossesRiseParticleTypes.registerBedrockParticle("wood_dust");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_AOE = BossesRiseParticleTypes.registerBedrockParticle("poison_aoe");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_BUBBLE = BossesRiseParticleTypes.registerBedrockParticle("poison_bubble");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_FOG = BossesRiseParticleTypes.registerBedrockParticle("poison_fog");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> POISON_FOG_PUDDLE = BossesRiseParticleTypes.registerBedrockParticle("poison_fog_puddle");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_BUBBLES_LOOP = BossesRiseParticleTypes.registerBedrockParticle("slam_bubbles_loop");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_DROPS_LOOP = BossesRiseParticleTypes.registerBedrockParticle("slam_drops_loop");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SPLASH_LOOP = BossesRiseParticleTypes.registerBedrockParticle("slam_splash_loop");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_WHIRLPOOL_LOOP = BossesRiseParticleTypes.registerBedrockParticle("slam_whirlpool_loop");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_BUBBLES_RELEASE = BossesRiseParticleTypes.registerBedrockParticle("slam_bubbles_release");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_DROPS_RELEASE = BossesRiseParticleTypes.registerBedrockParticle("slam_drops_release");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SMALL_SPLASH_RELEASE = BossesRiseParticleTypes.registerBedrockParticle("slam_small_splash_release");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_SPLASH_RELEASE = BossesRiseParticleTypes.registerBedrockParticle("slam_splash_release");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> SLAM_WHIRLPOOL_RELEASE = BossesRiseParticleTypes.registerBedrockParticle("slam_whirlpool_release");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SHOCKWAVE_ACTIVATION = BossesRiseParticleTypes.registerBedrockParticle("tentacle_slam_shockwave_activation");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SMALL_WATER_ACTIVATION = BossesRiseParticleTypes.registerBedrockParticle("tentacle_slam_small_water_activation");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_SPLASH_ACTIVATION_MAIN = BossesRiseParticleTypes.registerBedrockParticle("tentacle_slam_splash_activation_main");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_TALL_WATER_ACTIVATION = BossesRiseParticleTypes.registerBedrockParticle("tentacle_slam_tall_water_activation");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> TENTACLE_SLAM_WHITE_WATER_ACTIVATION = BossesRiseParticleTypes.registerBedrockParticle("tentacle_slam_white_water_activation");
    public static final DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> FOG_1 = BossesRiseParticleTypes.registerBedrockParticle("fog_1");
    public static final int PIXEL_COLOR_DEFAULT = 62969;
    public static final int PIXEL_COLOR_INACTIVE = 16740689;

    private static <T extends ParticleOptions> ParticleType<T> register(boolean overrideLimiter, final Function<ParticleType<T>, MapCodec<T>> codecGetter, final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecGetter) {
        return new ParticleType<T>(overrideLimiter){

            @Nonnull
            public MapCodec<T> codec() {
                return (MapCodec)codecGetter.apply(this);
            }

            @Nonnull
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return (StreamCodec)streamCodecGetter.apply(this);
            }
        };
    }

    private static DeferredHolder<ParticleType<?>, ParticleType<BedrockEmitterParticleOptions>> registerBedrockParticle(String name) {
        return REGISTRY.register(name, () -> BossesRiseParticleTypes.register(false, BedrockEmitterParticleOptions::codec, BedrockEmitterParticleOptions::streamCodec));
    }
}

