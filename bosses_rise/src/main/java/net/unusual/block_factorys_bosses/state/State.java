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
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.state.ActiveState;
import net.unusual.block_factorys_bosses.state.LambdaDataState;
import net.unusual.block_factorys_bosses.state.LambdaState;
import net.unusual.block_factorys_bosses.state.StateData;
import net.unusual.block_factorys_bosses.state.StateRef;

public interface State<E extends Entity> {
    default public void onStart(ActiveState<E> state) {
    }

    @Nonnull
    public Result onTick(ActiveState<E> var1);

    default public void onEnd(ActiveState<E> state) {
    }

    public static <E extends Entity> StateRef<E> named(String name) {
        return new StateRef(name);
    }

    public static <E extends Entity> LambdaState.LambdaStateBuilder<E> builder() {
        return new LambdaState.LambdaStateBuilder();
    }

    public static <E extends Entity, D extends StateData> LambdaDataState.LambdaDataStateBuilder<E, D> withData(Codec<D> dataCodec, Supplier<D> defaultDataSupplier) {
        return new LambdaDataState.LambdaDataStateBuilder().withData(dataCodec, defaultDataSupplier);
    }

    public static interface StateBuilder<E extends Entity> {
        public State<E> build();
    }

    public static enum Result {
        CONTINUE,
        END;

    }
}

