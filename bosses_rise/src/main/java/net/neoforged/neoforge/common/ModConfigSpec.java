package net.neoforged.neoforge.common;

import java.util.function.Supplier;
import net.neoforged.fml.config.IConfigSpec;

public class ModConfigSpec implements IConfigSpec {
    public static class ConfigValue<T> implements Supplier<T> {
        private final T value;
        public ConfigValue(T value) { this.value = value; }
        @Override
        public T get() { return value; }
        public void set(T val) {}
    }

    public static class Builder {
        public Builder push(String path) { return this; }
        public Builder pop() { return this; }
        public Builder comment(String comment) { return this; }
        public Builder translation(String translation) { return this; }
        public <T> ConfigValue<T> define(String path, T defaultValue) { return new ConfigValue<>(defaultValue); }
        public <T extends Enum<T>> ConfigValue<T> defineEnum(String path, T defaultValue) { return new ConfigValue<>(defaultValue); }
        public <T extends Comparable<? super T>> ConfigValue<T> defineInRange(String path, T defaultValue, T min, T max) { return new ConfigValue<>(defaultValue); }
        public ModConfigSpec build() { return new ModConfigSpec(); }
    }
}
