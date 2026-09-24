/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener$PreparationBarrier
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.util.profiling.ProfilerFiller
 *  software.bernie.geckolib.GeckoLibClient
 *  software.bernie.geckolib.GeckoLibConstants
 *  software.bernie.geckolib.loading.FileLoader
 *  software.bernie.geckolib.loading.object.BakedAnimations
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.unusual.block_factorys_bosses.geckolib.util.CustomKeyFramesAdapter;
import software.bernie.geckolib.GeckoLibClient;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.loading.FileLoader;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseAnimationCache {
    public static final String CUSTOM_ANIMATION_PATH = "anim_bosses_rise";
    private static Map<ResourceLocation, BakedAnimations> BOSSES_RISE_ANIMATIONS = Collections.emptyMap();

    public static Map<ResourceLocation, BakedAnimations> getBakedAnimations() {
        return BOSSES_RISE_ANIMATIONS;
    }

    public static void registerReloadListener() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager instanceof ReloadableResourceManager) {
            ReloadableResourceManager resourceManager2 = (ReloadableResourceManager)resourceManager;
            resourceManager2.registerReloadListener(BossesRiseAnimationCache::reload);
        }
    }

    public static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        Object2ObjectOpenHashMap<ResourceLocation, BakedAnimations> animations = new Object2ObjectOpenHashMap<>();
        CompletableFuture<?>[] completableFutureArray = new CompletableFuture[1];
        completableFutureArray[0] = BossesRiseAnimationCache.loadAnimations(backgroundExecutor, resourceManager, animations::put);
        return CompletableFuture.allOf(completableFutureArray).thenCompose(stage::wait).thenAcceptAsync(empty -> {
            BOSSES_RISE_ANIMATIONS = animations;
        }, gameExecutor);
    }

    private static CompletableFuture<Void> loadAnimations(Executor backgroundExecutor, ResourceManager resourceManager, BiConsumer<ResourceLocation, BakedAnimations> elementConsumer) {
        return BossesRiseAnimationCache.loadResources(backgroundExecutor, resourceManager, resource -> {
            try {
                return (BakedAnimations)CustomKeyFramesAdapter.GEO_GSON.fromJson((JsonElement)GsonHelper.getAsJsonObject((JsonObject)FileLoader.loadFile((ResourceLocation)resource, (ResourceManager)resourceManager), (String)"animations"), BakedAnimations.class);
            }
            catch (Exception ex) {
                throw GeckoLibConstants.exception((ResourceLocation)resource, (String)"Error loading animation file", (Throwable)ex);
            }
        }, elementConsumer);
    }

    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(() -> resourceManager.listResources(CUSTOM_ANIMATION_PATH, fileName -> fileName.toString().endsWith(".json")), executor).thenAcceptAsync(resources -> {
            for (ResourceLocation resource : resources.keySet()) {
                if (!resource.getNamespace().toLowerCase(Locale.ROOT).equals("block_factorys_bosses")) continue;
                map.accept(resource, loader.apply(resource));
            }
        }, executor);
    }
}

