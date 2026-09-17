package com.minhphuc.weapons.mixin;

import net.minecraft.world.entity.Interaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Interaction.class)
public interface InteractionAccessor {
    @Invoker("setWidth")
    void weapons$setWidth(float width);

    @Invoker("setHeight")
    void weapons$setHeight(float height);

    @Invoker("setResponse")
    void weapons$setResponse(boolean response);
}
