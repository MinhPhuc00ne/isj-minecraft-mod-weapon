/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nonnull
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.state.ActiveDataState;
import net.unusual.block_factorys_bosses.state.ActiveState;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateData;

public interface DataState<E extends Entity, D extends StateData>
extends State<E> {
    @Nonnull
    public D createData();

    public Codec<D> getDataCodec();

    default public void onStart(ActiveDataState<E, D> state) {
    }

    @Nonnull
    public State.Result onTick(ActiveDataState<E, D> var1);

    default public void onEnd(ActiveDataState<E, D> state) {
    }

    @Override
    default public void onStart(ActiveState<E> state) {
        this.onStart((ActiveDataState)state);
    }

    @Override
    @Nonnull
    default public State.Result onTick(ActiveState<E> state) {
        return this.onTick((ActiveDataState)state);
    }

    @Override
    default public void onEnd(ActiveState<E> state) {
        this.onEnd((ActiveDataState)state);
    }
}

