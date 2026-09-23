/*
 * Decompiled with CFR 0.152.
 */
package net.unusual.block_factorys_bosses.random;

import java.util.List;

public class WeightedPool<T> {
    private final List<PoolOption<T>> options;
    private final int totalWeight;

    public WeightedPool(List<PoolOption<T>> options) {
        if (options.isEmpty()) {
            throw new IllegalArgumentException("WeightedPool must have at least one option");
        }
        this.options = options;
        this.totalWeight = options.stream().mapToInt(PoolOption::weight).sum();
    }

    public T selectRandom() {
        int randomWeight = (int)(Math.random() * (double)this.totalWeight);
        int weightSum = 0;
        for (PoolOption<T> option : this.options) {
            if (randomWeight >= (weightSum += option.weight())) continue;
            return option.entry();
        }
        return null;
    }

    @SafeVarargs
    public static <T> WeightedPool<T> of(PoolOption<T> ... entries) {
        return new WeightedPool<T>(List.of(entries));
    }

    public static <T> PoolOption<T> option(int weight, T entry) {
        return new PoolOption<T>(weight, entry);
    }

    public record PoolOption<T>(int weight, T entry) {
        public PoolOption {
            if (weight < 1) {
                throw new IllegalArgumentException("PoolOption must have a weight of at least 1");
            }
        }
    }
}

