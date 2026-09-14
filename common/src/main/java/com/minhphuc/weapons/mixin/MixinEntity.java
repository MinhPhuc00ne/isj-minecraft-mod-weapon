package com.minhphuc.weapons.mixin;

import com.minhphuc.weapons.data.IEntityDataSaver;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntityDataSaver {
    @Unique
    private CompoundTag weapons$modData;

    @Override
    public CompoundTag weapons$getModData() {
        if (this.weapons$modData == null) {
            this.weapons$modData = new CompoundTag();
        }
        return this.weapons$modData;
    }

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void weapons$injectSave(CompoundTag compound, CallbackInfoReturnable<CompoundTag> cir) {
        if (this.weapons$modData != null && !this.weapons$modData.isEmpty()) {
            compound.put("WeaponsModData", this.weapons$modData);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void weapons$injectLoad(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("WeaponsModData", 10)) {
            this.weapons$modData = compound.getCompound("WeaponsModData");
        }
    }
}
