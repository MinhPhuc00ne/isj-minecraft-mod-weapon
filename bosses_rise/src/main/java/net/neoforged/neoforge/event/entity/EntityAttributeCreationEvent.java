package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class EntityAttributeCreationEvent {
    public void put(EntityType<? extends LivingEntity> entityType, AttributeSupplier supplier) {
        // In Fabric, FabricDefaultAttributeRegistry.register(entityType, supplier);
    }
}
