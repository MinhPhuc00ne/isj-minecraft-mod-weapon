/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.state;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateRef;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StateRegistry<E extends Entity> {
    private final Map<StateRef<E>, State<E>> states = new HashMap<StateRef<E>, State<E>>();

    public void register(StateRef<E> stateRef, State<E> state) {
        if (this.states.containsKey(stateRef)) {
            throw new IllegalArgumentException("Tried to register a state with a duplicate name: " + stateRef.getName());
        }
        this.states.put(stateRef, state);
    }

    public StateRef<E> register(StateRef<E> stateRef, State.StateBuilder<E> stateBuilder) {
        this.register(stateRef, stateBuilder.build());
        return stateRef;
    }

    @Nullable
    public State<E> getState(StateRef<E> stateRef) {
        return this.states.get(stateRef);
    }

    @Nullable
    public StateRef<E> getStateRef(String stateName) {
        return this.states.keySet().stream().filter(stateRef -> stateRef.getName().equals(stateName)).findFirst().orElse(null);
    }

    public boolean isEmpty() {
        return this.states.isEmpty();
    }
}

