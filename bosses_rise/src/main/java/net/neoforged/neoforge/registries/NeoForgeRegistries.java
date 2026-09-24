package net.neoforged.neoforge.registries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.attachment.AttachmentType;

public class NeoForgeRegistries {
    public static class Keys {
        public static final ResourceKey<Registry<AttachmentType<?>>> ATTACHMENT_TYPES = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("neoforge", "attachment_types"));
    }
}
