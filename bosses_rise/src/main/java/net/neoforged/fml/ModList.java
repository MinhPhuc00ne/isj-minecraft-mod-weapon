package net.neoforged.fml;

import java.util.Optional;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class ModList {
    private static final ModList INSTANCE = new ModList();

    public static ModList get() {
        return INSTANCE;
    }

    public Optional<ModContainer> getModContainerById(String modId) {
        return Optional.of(new ModContainer());
    }

    public static class ModContainer {
        public void registerConfig(ModConfig.Type type, IConfigSpec spec) {
        }
    }
}
