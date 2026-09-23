/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener$PreparationBarrier
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.util.profiling.ProfilerFiller
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BedrockParticleEffectCache {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String DEFINITIONS_DIRECTORY = "bedrock_particles";
    private static Map<ResourceLocation, BedrockParticleEffect> BEDROCK_PARTICLE_EFFECTS = Collections.emptyMap();

    public static Map<ResourceLocation, BedrockParticleEffect> getBedrockParticleEffects() {
        return BEDROCK_PARTICLE_EFFECTS;
    }

    public static void registerReloadListener() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager instanceof ReloadableResourceManager) {
            ReloadableResourceManager resourceManager2 = (ReloadableResourceManager)resourceManager;
            resourceManager2.registerReloadListener(BedrockParticleEffectCache::reload);
        }
    }

    public static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        Map<ResourceLocation, BedrockParticleEffect> particleEffects = new Object2ObjectOpenHashMap<>();
        return BedrockParticleEffectCache.loadParticleEffects(backgroundExecutor, resourceManager, particleEffects::put)
            .thenCompose(stage::wait)
            .thenAcceptAsync(empty -> {
                BEDROCK_PARTICLE_EFFECTS = particleEffects;
            }, gameExecutor);
    }

    private static CompletableFuture<Void> loadParticleEffects(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BedrockParticleEffect> elementConsumer) {
        return BedrockParticleEffectCache.loadResources(backgroundExecutor, resourceManager, resourceLocation -> {
            try {
                Resource effectResource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation).orElseThrow();
                try (BufferedReader reader = effectResource.openAsReader()) {
                    return BedrockParticleEffect.deserialize(reader);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't load Bedrock particle effect " + String.valueOf(resourceLocation), e);
            }
        }, elementConsumer);
    }

    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(() -> resourceManager.listResources(DEFINITIONS_DIRECTORY, location -> location.getNamespace().equals("block_factorys_bosses") && location.getPath().endsWith(".particle.json")), executor).thenAcceptAsync(resources -> {
            for (ResourceLocation resource : resources.keySet()) {
                try {
                    T particleEffect = loader.apply(resource);
                    map.accept(resource, particleEffect);
                }
                catch (Exception e) {
                    LOGGER.error("Exception thrown while loading Bedrock particle effect {}", (Object)resource, (Object)e);
                }
            }
        }, executor);
    }
}

