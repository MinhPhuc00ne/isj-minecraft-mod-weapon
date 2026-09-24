/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 */
package net.unusual.block_factorys_bosses.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.state.ActiveDataState;
import net.unusual.block_factorys_bosses.state.ActiveState;
import net.unusual.block_factorys_bosses.state.DataState;
import net.unusual.block_factorys_bosses.state.State;
import net.unusual.block_factorys_bosses.state.StateData;
import net.unusual.block_factorys_bosses.state.StateRef;
import net.unusual.block_factorys_bosses.state.StateRegistry;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
public class StateController<E extends Entity> {
    private static final String TAG_STATE_CONTROLLER = "state_controller";
    private static final String TAG_STATE_STACK = "state_stack";
    private static final String TAG_NAME = "name";
    private static final String TAG_TIMER = "timer";
    private static final String TAG_DATA = "data";
    private final E entity;
    private final Function<StateController<E>, StateRef<E>> defaultStateSupplier;
    private final StateRegistry<E> registry;
    private final Deque<ActiveState<E>> stateStack = new ArrayDeque<ActiveState<E>>();

    public StateController(E entity, StateRegistry<E> stateRegistry, Function<StateController<E>, StateRef<E>> defaultStateSupplier) {
        this.entity = entity;
        this.registry = stateRegistry;
        this.defaultStateSupplier = defaultStateSupplier;
    }

    public E getEntity() {
        return this.entity;
    }

    public StateRegistry<E> getRegistry() {
        return this.registry;
    }

    @Nullable
    public ActiveState<E> getActiveState() {
        return this.stateStack.peekFirst();
    }

    @Nullable
    public StateRef<E> getActiveStateRef() {
        ActiveState<E> activeState = this.getActiveState();
        return activeState == null ? null : activeState.getStateRef();
    }

    public Deque<ActiveState<E>> getStateStack() {
        return this.stateStack;
    }

    public boolean hasActiveState(Predicate<? super ActiveState<E>> predicate) {
        return this.stateStack.stream().anyMatch(predicate);
    }

    public boolean hasActiveStateRef(StateRef<E> stateRef) {
        return this.hasActiveStateRef(it -> it == stateRef);
    }

    public boolean hasActiveStateRef(Predicate<? super StateRef<E>> predicate) {
        return this.stateStack.stream().map(ActiveState::getStateRef).anyMatch(predicate);
    }

    @Nullable
    public ActiveState<E> findActiveState(Predicate<? super ActiveState<E>> predicate) {
        return this.stateStack.stream().filter(predicate).findFirst().orElse(null);
    }

    @Nullable
    public ActiveState<E> findActiveStateByRef(StateRef<E> stateRef) {
        return this.stateStack.stream().filter(state -> state.getStateRef() == stateRef).findFirst().orElse(null);
    }

    @Nullable
    public ActiveState<E> findActiveStateByRef(Predicate<? super StateRef<E>> predicate) {
        return this.stateStack.stream().filter(state -> predicate.test(state.getStateRef())).findFirst().orElse(null);
    }

    public boolean isStateActive(@Nullable StateRef<E> state) {
        ActiveState<E> activeState = this.getActiveState();
        if (activeState == null) {
            return state == null;
        }
        return activeState.getStateRef() == state;
    }

    @Nullable
    public State<E> getState() {
        ActiveState<E> activeState = this.getActiveState();
        if (activeState == null) {
            return null;
        }
        return activeState.getState();
    }

    private ActiveState<E> createInstance(StateRef<E> stateRef) {
        State<E> state = Objects.requireNonNull(this.getRegistry().getState(stateRef));
        if (state instanceof DataState) {
            DataState dataState = (DataState)state;
            return new ActiveDataState(this, stateRef, dataState, dataState.createData());
        }
        return new ActiveState<E>(this, stateRef, state);
    }

    public ActiveState<E> push(StateRef<E> state) {
        ActiveState<E> activeState = this.createInstance(state);
        this.stateStack.addFirst(activeState);
        return activeState;
    }

    public ActiveState<E> pushBefore(ActiveState<E> before, StateRef<E> state) {
        ActiveState<E> next;
        ActiveState<E> activeState = null;
        ArrayList tempStack = new ArrayList(this.stateStack.size());
        while ((next = this.stateStack.pollFirst()) != null) {
            tempStack.add(next);
            if (next != before) continue;
            activeState = this.push(state);
            break;
        }
        while (!tempStack.isEmpty()) {
            this.stateStack.addFirst((ActiveState)tempStack.removeLast());
        }
        if (activeState == null) {
            throw new IndexOutOfBoundsException("StateController doesn't have the given state " + String.valueOf(before));
        }
        return activeState;
    }

    public ActiveState<E> replaceActive(StateRef<E> state) {
        this.endActive();
        return this.push(state);
    }

    public ActiveState<E> replaceAll(StateRef<E> state) {
        this.endAll();
        return this.push(state);
    }

    @Nullable
    public ActiveState<E> endActive() {
        ActiveState<E> activeState = this.stateStack.removeFirst();
        if (activeState == null) {
            return null;
        }
        activeState.getState().onEnd(activeState);
        return activeState;
    }

    public void endDownTo(ActiveState<E> activeState) {
        if (!this.stateStack.contains(activeState)) {
            return;
        }
        while (this.endActive() != activeState) {
        }
    }

    public void endAll() {
        while (!this.stateStack.isEmpty()) {
            this.endActive();
        }
    }

    public void tick() {
        State.Result result;
        ActiveState<E> activeState = this.stateStack.peekFirst();
        if (activeState == null) {
            StateRef<E> newState = this.defaultStateSupplier.apply(this);
            if (newState == null) {
                return;
            }
            activeState = this.push(newState);
        }
        if ((result = activeState.tick()) == State.Result.END) {
            if (this.stateStack.getFirst() == activeState) {
                this.endActive();
            }
            assert (!this.stateStack.contains(activeState));
        }
    }

    public void addAdditionalSaveData(CompoundTag entityCompound) {
        CompoundTag stateControllerCompound = new CompoundTag();
        ListTag stackList = new ListTag();
        for (ActiveState<E> activeState : this.stateStack) {
            CompoundTag activeStateCompound = new CompoundTag();
            activeStateCompound.putString(TAG_NAME, activeState.getStateRef().getName());
            activeStateCompound.putInt(TAG_TIMER, activeState.getTimer());
            if (activeState instanceof ActiveDataState) {
                ActiveDataState activeDataState = (ActiveDataState)activeState;
                Object stateData = activeDataState.getData();
                Codec dataCodec = activeDataState.getState().getDataCodec();
                Tag dataCompound = (Tag)dataCodec.encodeStart((DynamicOps)NbtOps.INSTANCE, stateData).getOrThrow();
                activeStateCompound.put(TAG_DATA, dataCompound);
            }
            stackList.add(activeStateCompound);
        }
        stateControllerCompound.put(TAG_STATE_STACK, (Tag)stackList);
        entityCompound.put(TAG_STATE_CONTROLLER, (Tag)stateControllerCompound);
    }

    public void readAdditionalSaveData(CompoundTag entityCompound) {
        assert (this.stateStack.isEmpty());
        if (!entityCompound.contains(TAG_STATE_CONTROLLER)) {
            return;
        }
        CompoundTag stateControllerCompound = entityCompound.getCompound(TAG_STATE_CONTROLLER);
        ListTag stackList = stateControllerCompound.getList(TAG_STATE_STACK, 10);
        for (Tag tag : stackList) {
            ActiveState<E> activeState;
            if (!(tag instanceof CompoundTag)) continue;
            CompoundTag activeStateCompound = (CompoundTag)tag;
            StateRef<E> stateRef = Objects.requireNonNull(this.getRegistry().getStateRef(activeStateCompound.getString(TAG_NAME)));
            State<E> state = Objects.requireNonNull(this.getRegistry().getState(stateRef));
            if (state instanceof DataState) {
                DataState dataState = (DataState)state;
                CompoundTag stateDataCompound = activeStateCompound.getCompound(TAG_DATA);
                StateData stateData = (StateData)((DataState)state).getDataCodec().decode((DynamicOps)NbtOps.INSTANCE, (Object)stateDataCompound).getOrThrow();
                activeState = new ActiveDataState<E, StateData>(this, stateRef, dataState, stateData);
            } else {
                activeState = new ActiveState<E>(this, stateRef, state);
            }
            if (activeStateCompound.contains(TAG_TIMER)) {
                activeState.setTimer(activeStateCompound.getInt(TAG_TIMER));
            }
            this.stateStack.addFirst(activeState);
        }
    }
}

