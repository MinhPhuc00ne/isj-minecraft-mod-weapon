/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 *  software.bernie.geckolib.cache.object.BakedGeoModel
 *  software.bernie.geckolib.cache.texture.AnimatableTexture
 *  software.bernie.geckolib.model.GeoModel
 *  software.bernie.geckolib.renderer.GeoBlockRenderer
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package net.unusual.block_factorys_bosses.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.block.entity.HugeDoorBlockEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBlockGeoModel;
import net.unusual.block_factorys_bosses.init.BossesRiseRenderTypes;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.texture.AnimatableTexture;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeDoorBlockEntityRenderer
extends GeoBlockRenderer<HugeDoorBlockEntity> {
    private final ResourceLocation lockedTextureLocation;
    private final ResourceLocation unlockedTextureLocation;
    private final ResourceLocation unlockingTextureLocation;
    @Nullable
    private final ResourceLocation lockedEmissiveTextureLocation;
    @Nullable
    private final ResourceLocation unlockedEmissiveTextureLocation;
    @Nullable
    private final ResourceLocation unlockingEmissiveTextureLocation;
    private boolean useEmissiveTexture = false;

    public HugeDoorBlockEntityRenderer(BlockEntityRendererProvider.Context context, ResourceLocation blockId, boolean hasEmissiveTextures) {
        super((GeoModel)new CustomBlockGeoModel<HugeDoorBlockEntity>(blockId){

            public RenderType getRenderType(HugeDoorBlockEntity animatable, ResourceLocation texture) {
                return RenderType.entityCutout((ResourceLocation)texture);
            }
        });
        CustomBlockGeoModel model = (CustomBlockGeoModel)this.getGeoModel();
        this.lockedTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_locked"));
        this.unlockedTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_unlocked"));
        this.unlockingTextureLocation = model.buildFormattedTexturePath(blockId.withSuffix("_unlocking"));
        if (!hasEmissiveTextures) {
            this.lockedEmissiveTextureLocation = null;
            this.unlockedEmissiveTextureLocation = null;
            this.unlockingEmissiveTextureLocation = null;
        } else {
            this.lockedEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.lockedTextureLocation);
            this.unlockedEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.unlockedTextureLocation);
            this.unlockingEmissiveTextureLocation = EmissiveRenderLayer.addEmissiveTextureSuffix(this.unlockingTextureLocation);
            this.addRenderLayer(new EmissiveRenderLayer<HugeDoorBlockEntity>((GeoRenderer)this, blockEntity -> {
                if (((HugeDoorBlockEntity)this.animatable).getUnlockingTextureFrame() >= 0) {
                    return this.unlockingEmissiveTextureLocation;
                }
                return (Boolean)((HugeDoorBlockEntity)this.animatable).getBlockState().getValue((Property)HugeDoorBlock.OPEN) != false ? this.unlockedEmissiveTextureLocation : this.lockedEmissiveTextureLocation;
            }){

                @Override
                public RenderType getRenderType(HugeDoorBlockEntity animatable, ResourceLocation texture) {
                    return BossesRiseRenderTypes.entityTranslucentEmissiveCull(texture);
                }

                @Override
                public void render(PoseStack poseStack, HugeDoorBlockEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
                    HugeDoorBlockEntityRenderer.this.useEmissiveTexture = true;
                    super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
                    HugeDoorBlockEntityRenderer.this.useEmissiveTexture = false;
                }
            });
        }
    }

    public ResourceLocation getTextureLocation(HugeDoorBlockEntity animatable) {
        if (this.useEmissiveTexture) {
            assert (this.unlockingEmissiveTextureLocation != null && this.unlockedEmissiveTextureLocation != null && this.lockedEmissiveTextureLocation != null);
            return this.chooseTexture(this.lockedEmissiveTextureLocation, this.unlockedEmissiveTextureLocation, this.unlockingEmissiveTextureLocation);
        }
        return this.chooseTexture(this.lockedTextureLocation, this.unlockedTextureLocation, this.unlockingTextureLocation);
    }

    private ResourceLocation chooseTexture(ResourceLocation locked, ResourceLocation unlocked, ResourceLocation unlocking) {
        if (((HugeDoorBlockEntity)this.animatable).getUnlockingTextureFrame() >= 0) {
            return unlocking;
        }
        return (Boolean)((HugeDoorBlockEntity)this.animatable).getBlockState().getValue((Property)HugeDoorBlock.OPEN) != false ? unlocked : locked;
    }

    public boolean shouldRenderOffScreen(HugeDoorBlockEntity blockEntity) {
        return true;
    }

    public AABB getRenderBoundingBox(HugeDoorBlockEntity blockEntity) {
        if (blockEntity.isPlayingOpeningAnimation()) {
            return AABB.ofSize((Vec3)blockEntity.getBlockPos().getCenter(), (double)this.getViewDistance(), (double)this.getViewDistance(), (double)this.getViewDistance());
        }
        BlockPos rootPos = blockEntity.getBlockPos();
        Direction facing = (Direction)blockEntity.getBlockState().getValue(HugeDoorBlock.FACING);
        Direction directionToOtherHinge = HugeDoorBlock.getDirectionToOtherHinge(facing);
        int width = HugeDoorBlock.getWidth(blockEntity.getBlockState());
        int height = HugeDoorBlock.getHeight(blockEntity.getBlockState());
        int halfWidth = width / 2 + 1;
        BlockPos centerPos = rootPos.relative(directionToOtherHinge, halfWidth);
        return AABB.encapsulatingFullBlocks((BlockPos)centerPos.offset(-halfWidth, 0, -halfWidth), (BlockPos)centerPos.offset(halfWidth, height - 1, halfWidth)).inflate(2.0);
    }

    public int getViewDistance() {
        return 256;
    }

    public void render(HugeDoorBlockEntity animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Vector3f offset = HugeDoorBlock.getHorizontalCenterOffset(animatable.getBlockState());
        poseStack.translate(offset.x(), offset.y(), offset.z());
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }

    public void updateAnimatedTextureFrame(HugeDoorBlockEntity animatable) {
        int unlockingTextureFrame = animatable.getUnlockingTextureFrame();
        if (unlockingTextureFrame < 0) {
            super.updateAnimatedTextureFrame(animatable);
        } else {
            AnimatableTexture.setAndUpdate((ResourceLocation)this.getTextureLocation(animatable), (int)unlockingTextureFrame);
        }
    }
}

