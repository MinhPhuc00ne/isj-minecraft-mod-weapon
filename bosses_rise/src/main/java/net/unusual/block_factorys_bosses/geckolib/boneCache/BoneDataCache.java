/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.Nullable
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimatableManager
 *  software.bernie.geckolib.constant.dataticket.DataTicket
 */
package net.unusual.block_factorys_bosses.geckolib.boneCache;

import java.util.HashMap;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.geckolib.boneCache.BoneData;
import net.unusual.block_factorys_bosses.geckolib.util.AnimatableId;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.constant.dataticket.DataTicket;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BoneDataCache {
    public static final DataTicket<BoneDataCache> DATA_TICKET = new DataTicket(BossesRise.prefix("bone_cache").toString(), BoneDataCache.class);
    private final HashMap<String, BoneData> boneDataByName = new HashMap();
    private long lastClear = -1L;

    public boolean hasBoneData() {
        return !this.boneDataByName.isEmpty();
    }

    @Nullable
    public BoneData getBoneData(String boneName) {
        BoneData boneData = this.boneDataByName.get(boneName);
        if (boneData != null) {
            boneData.markAccessed();
        }
        return boneData;
    }

    public BoneData getOrCreateBoneData(String boneName) {
        BoneData boneData = this.boneDataByName.computeIfAbsent(boneName, key -> new BoneData());
        boneData.markAccessed();
        return boneData;
    }

    @Nullable
    public BoneData getInitializedBoneData(String boneName) {
        BoneData boneData = this.getOrCreateBoneData(boneName);
        if (boneData.isInitialized()) {
            return boneData;
        }
        return null;
    }

    @ApiStatus.Internal
    public void maybeClearUnwantedBoneData() {
        if (this.lastClear > this.lastClear - 2000L) {
            return;
        }
        this.clearUnwantedBoneData();
    }

    @ApiStatus.Internal
    public void clearUnwantedBoneData() {
        this.boneDataByName.values().removeIf(BoneData::isUnwanted);
        this.lastClear = System.currentTimeMillis();
    }

    public static <T extends GeoAnimatable> BoneDataCache getOrCreateBoneDataCache(long animatableId, T animatable) {
        AnimatableManager animatableCache = animatable.getAnimatableInstanceCache().getManagerForId(animatableId);
        BoneDataCache boneDataCache = (BoneDataCache)animatableCache.getData(DATA_TICKET);
        if (boneDataCache == null) {
            boneDataCache = new BoneDataCache();
            animatableCache.setData(DATA_TICKET, boneDataCache);
        }
        return boneDataCache;
    }

    public static <T extends Entity & GeoAnimatable> BoneDataCache getOrCreateBoneDataCache(T entity) {
        return BoneDataCache.getOrCreateBoneDataCache(AnimatableId.ofEntity(entity), entity);
    }

    public static <T extends BlockEntity & GeoAnimatable> BoneDataCache getOrCreateBoneDataCache(T blockEntity) {
        return BoneDataCache.getOrCreateBoneDataCache(AnimatableId.ofBlockEntity(blockEntity), blockEntity);
    }

    @Nullable
    public static <T extends GeoAnimatable> BoneDataCache getBoneDataCache(long animatableId, T animatable) {
        return (BoneDataCache)animatable.getAnimatableInstanceCache().getDataPoint(animatableId, DATA_TICKET);
    }

    @Nullable
    public static <T extends Entity & GeoAnimatable> BoneDataCache getBoneDataCache(T entity) {
        return BoneDataCache.getBoneDataCache(AnimatableId.ofEntity(entity), entity);
    }

    @Nullable
    public static <T extends BlockEntity & GeoAnimatable> BoneDataCache getBoneDataCache(T blockEntity) {
        return BoneDataCache.getBoneDataCache(AnimatableId.ofBlockEntity(blockEntity), blockEntity);
    }

    @Nullable
    public static <T extends GeoAnimatable> BoneData getInitializedBoneData(long animatableId, T animatable, String boneName) {
        BoneDataCache boneDataCache = BoneDataCache.getBoneDataCache(animatableId, animatable);
        if (boneDataCache != null) {
            return boneDataCache.getInitializedBoneData(boneName);
        }
        return null;
    }

    @Nullable
    public static <T extends Entity & GeoAnimatable> BoneData getInitializedBoneData(T entity, String boneName) {
        return BoneDataCache.getInitializedBoneData(AnimatableId.ofEntity(entity), entity, boneName);
    }

    @Nullable
    public static <T extends BlockEntity & GeoAnimatable> BoneData getInitializedBoneData(T blockEntity, String boneName) {
        return BoneDataCache.getInitializedBoneData(AnimatableId.ofBlockEntity(blockEntity), blockEntity, boneName);
    }
}

