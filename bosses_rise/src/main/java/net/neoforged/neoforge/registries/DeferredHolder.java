package net.neoforged.neoforge.registries;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DeferredHolder<R, T extends R> implements Supplier<T>, Holder<R> {
    private final ResourceLocation id;
    private final Supplier<T> supplier;
    private T value;

    public DeferredHolder(ResourceLocation id, Supplier<T> supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public ResourceLocation getId() { return id; }

    public DeferredHolder<R, T> getDelegate() { return this; }

    @Override
    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

    @Override
    public R value() { return get(); }

    @Override
    public boolean isBound() { return true; }

    @Override
    public boolean is(ResourceLocation id) { return this.id.equals(id); }

    @Override
    public boolean is(ResourceKey<R> key) { return false; }

    @Override
    public boolean is(Predicate<ResourceKey<R>> predicate) { return false; }

    @Override
    public boolean is(TagKey<R> tag) { return false; }

    @Override
    public boolean is(Holder<R> holder) { return this.equals(holder); }

    @Override
    public Stream<TagKey<R>> tags() { return Stream.empty(); }

    @Override
    public Either<ResourceKey<R>, R> unwrap() { return Either.right(get()); }

    @Override
    public Optional<ResourceKey<R>> unwrapKey() { return Optional.empty(); }

    @Override
    public Kind kind() { return Kind.DIRECT; }

    @Override
    public boolean canSerializeIn(HolderOwner<R> owner) { return true; }

    public ResourceKey<R> getKey() { return null; }
    public Holder<R> asHolder() { return this; }
}
