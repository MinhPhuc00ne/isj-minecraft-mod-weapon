/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 */
package net.unusual.block_factorys_bosses.entity.control;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NoMoveControl
extends MoveControl {
    public NoMoveControl(Mob mob) {
        super(mob);
    }

    public void tick() {
        this.operation = MoveControl.Operation.WAIT;
    }
}

