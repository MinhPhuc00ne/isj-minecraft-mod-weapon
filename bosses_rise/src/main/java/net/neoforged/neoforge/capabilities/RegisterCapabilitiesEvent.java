package net.neoforged.neoforge.capabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RegisterCapabilitiesEvent {
    public <T extends BlockEntity> void registerBlockEntity(Object capability, BlockEntityType<T> type, Object provider) {
    }
}
