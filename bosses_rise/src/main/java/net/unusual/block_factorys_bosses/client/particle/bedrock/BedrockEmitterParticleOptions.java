/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  io.netty.buffer.ByteBuf
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.network.codec.ByteBufCodecs
 *  net.minecraft.network.codec.StreamCodec
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEmitter;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record BedrockEmitterParticleOptions(ParticleType<BedrockEmitterParticleOptions> type, @Nullable BedrockParticleEmitter parent, boolean isEmitter) implements ParticleOptions
{
    public static MapCodec<BedrockEmitterParticleOptions> codec(ParticleType<BedrockEmitterParticleOptions> type) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.BOOL.optionalFieldOf("is_emitter", true).forGetter(BedrockEmitterParticleOptions::isEmitter)).apply(instance, isEmitter -> new BedrockEmitterParticleOptions(type, null, isEmitter)));
    }

    public static StreamCodec<? super ByteBuf, BedrockEmitterParticleOptions> streamCodec(ParticleType<BedrockEmitterParticleOptions> type) {
        return StreamCodec.composite((StreamCodec)ByteBufCodecs.BOOL, BedrockEmitterParticleOptions::isEmitter, isEmitter -> new BedrockEmitterParticleOptions(type, null, (boolean)isEmitter));
    }

    public ParticleType<?> getType() {
        return this.type;
    }
}

