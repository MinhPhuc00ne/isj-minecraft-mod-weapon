/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 *  software.bernie.geckolib.GeckoLibConstants
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimationProcessor
 *  software.bernie.geckolib.cache.GeckoLibCache
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoEntityRenderer
 *  software.bernie.geckolib.util.ClientUtil
 */
package net.unusual.block_factorys_bosses.geckolib.boss.knight;

import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.MatrixAnimationProcessorTwo;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import net.unusual.block_factorys_bosses.geckolib.util.ParticleLocator;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.ClientUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnderworldKnightRenderer
extends GeoEntityRenderer<UnderworldKnightEntity>
implements CinematicRenderer<UnderworldKnightEntity> {
    public static final Map<String, ParticleLocator> BONER = new HashMap<String, ParticleLocator>();
    public static final ResourceLocation PHASE_1_MODEL = BossesRise.prefix("knight_boss");
    private static final ResourceLocation PHASE_2_TEXTURE = BossesRise.prefix("textures/entity/knight_boss_phase2.png");

    public UnderworldKnightRenderer(EntityRendererProvider.Context context) {
        super(context, (GeoModel)new UnderworldKnightModel(PHASE_1_MODEL));
        this.addRenderLayer(new EmissiveRenderLayer(this));
    }

    @Nullable
    public RenderType getRenderType(UnderworldKnightEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        boolean invisible = animatable.isInvisible();
        if (invisible && !animatable.isInvisibleTo(ClientUtil.getClientPlayer())) {
            return RenderType.itemEntityTranslucentCull((ResourceLocation)texture);
        }
        if (!invisible) {
            return RenderType.entityTranslucent((ResourceLocation)texture);
        }
        return Minecraft.getInstance().shouldEntityAppearGlowing((Entity)animatable) ? RenderType.outline((ResourceLocation)texture) : null;
    }

    public boolean shouldRender(UnderworldKnightEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return livingEntity.isCinematic() || super.shouldRender(livingEntity, camera, camX, camY, camZ);
    }

    @Override
    public Pair<Float, Float> getCinematicYawAndPitch(UnderworldKnightEntity animatable, GeoBone cameraBone, LocalPlayer player) {
        Vector3d rot = cameraBone.getRotationVector().mul(-57.29577951308232);
        rot.y += (double)animatable.getYRot();
        return new Pair((Object)Float.valueOf((float)rot.y()), (Object)Float.valueOf((float)rot.x()));
    }

    private static class UnderworldKnightModel
    extends CustomEntityGeoModel<UnderworldKnightEntity> {
        private final MatrixAnimationProcessorTwo<UnderworldKnightEntity> animationProcessor = new MatrixAnimationProcessorTwo(this);
        private BakedGeoModel currentKnightModel = null;

        public UnderworldKnightModel(ResourceLocation location) {
            super(location);
        }

        public BakedGeoModel getBakedModel(ResourceLocation location) {
            BakedGeoModel model = (BakedGeoModel)GeckoLibCache.getBakedModels().get(location);
            if (model == null) {
                if (!location.getPath().contains("geo/")) {
                    throw GeckoLibConstants.exception((ResourceLocation)location, (String)"Invalid model resource path provided - GeckoLib models must be placed in assets/<modid>/geo/");
                }
                throw GeckoLibConstants.exception((ResourceLocation)location, (String)"Unable to find model");
            }
            if (model != this.currentKnightModel) {
                this.animationProcessor.setActiveModel(model);
                this.currentKnightModel = model;
            }
            return this.currentKnightModel;
        }

        public AnimationProcessor<UnderworldKnightEntity> getAnimationProcessor() {
            return this.animationProcessor;
        }

        public ResourceLocation getTextureResource(UnderworldKnightEntity knight) {
            return knight.isTransformed() ? PHASE_2_TEXTURE : super.getTextureResource(knight);
        }
    }
}

