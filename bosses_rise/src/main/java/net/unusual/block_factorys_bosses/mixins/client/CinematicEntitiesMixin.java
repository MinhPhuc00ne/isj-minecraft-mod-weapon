/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  software.bernie.geckolib.cache.object.GeoBone
 *  software.bernie.geckolib.renderer.GeoRenderer
 */
package net.unusual.block_factorys_bosses.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;

@Mixin(value={Entity.class, BlockEntity.class})
public class CinematicEntitiesMixin
implements CinematicEntity {
    private GeoBone cameraBone;

    @Override
    public void setCameraBone(String name) {
        if (name == null) {
            return;
        }
        GeoRenderer renderer = null;
        Object self = this;
        if (self instanceof Entity) {
            Entity entity = (Entity)self;
            renderer = (GeoRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
        } else if (self instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)self;
            renderer = (GeoRenderer)Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(blockEntity);
        }
        if (renderer == null) {
            return;
        }
        this.cameraBone = (GeoBone) renderer.getGeoModel().getBone(name).orElse(null);
        if (this.cameraBone != null && self instanceof Entity) {
            this.cameraBone.setTrackingMatrices(true);
        }
    }

    @Override
    @Nullable
    public GeoBone getCameraBone() {
        return this.cameraBone;
    }
}

