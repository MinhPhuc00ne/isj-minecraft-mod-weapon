/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.state;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.state.ActiveState;
import net.unusual.block_factorys_bosses.state.DataState;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.state.StateData;
import net.unusual.block_factorys_bosses.state.StateRef;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ActiveDataState<E extends Entity, D extends StateData>
extends ActiveState<E> {
    private final D data;

    public ActiveDataState(StateController<E> controller, StateRef<E> stateRef, DataState<E, D> state, D data) {
        super(controller, stateRef, state);
        this.data = data;
    }

    public D getData() {
        return this.data;
    }

    public DataState<E, D> getState() {
        return (DataState)super.getState();
    }

    @Override
    public String toString() {
        return "ActiveDataState(stateRef = " + this.getStateRef().getName() + ", timer = " + this.getTimer() + ")";
    }
}

