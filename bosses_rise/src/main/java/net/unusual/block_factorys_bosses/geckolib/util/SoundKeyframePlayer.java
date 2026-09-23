/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  software.bernie.geckolib.animatable.GeoAnimatable
 *  software.bernie.geckolib.animation.AnimationController$SoundKeyframeHandler
 *  software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.keyframe.event.SoundKeyframeEvent;

public class SoundKeyframePlayer<A extends GeoAnimatable>
implements AnimationController.SoundKeyframeHandler<A> {
    public void handle(SoundKeyframeEvent<A> event) {
        Level level = this.getLevel(event.getAnimatable());
        if (!level.isClientSide()) {
            return;
        }
        String[] data = event.getKeyframeData().getSound().split("\\|");
        if (data.length < 1) {
            return;
        }
        ResourceLocation soundLocation = ResourceLocation.parse((String)data[0]);
        float volume = data.length >= 2 ? Float.parseFloat(data[1]) : 1.5f;
        float pitch = data.length >= 3 ? Float.parseFloat(data[2]) : 1.0f;
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent((ResourceLocation)soundLocation);
        GeoAnimatable geoAnimatable = event.getAnimatable();
        if (geoAnimatable instanceof Entity) {
            Entity entity = (Entity)geoAnimatable;
            level.playLocalSound(entity.blockPosition(), soundEvent, entity.getSoundSource(), volume, pitch, false);
        } else {
            geoAnimatable = event.getAnimatable();
            if (geoAnimatable instanceof BlockEntity) {
                BlockEntity blockEntity = (BlockEntity)geoAnimatable;
                level.playLocalSound(blockEntity.getBlockPos(), soundEvent, SoundSource.BLOCKS, volume, pitch, false);
            } else {
                throw new IllegalArgumentException("Unsupported GeoAnimatable type: " + String.valueOf(event.getAnimatable()));
            }
        }
    }

    protected Level getLevel(A animatable) {
        if (animatable instanceof Entity) {
            Entity entity = (Entity)animatable;
            return entity.level();
        }
        if (animatable instanceof BlockEntity) {
            BlockEntity blockEntity = (BlockEntity)animatable;
            return blockEntity.getLevel();
        }
        throw new IllegalArgumentException("Unsupported GeoAnimatable type: " + String.valueOf(animatable));
    }
}

