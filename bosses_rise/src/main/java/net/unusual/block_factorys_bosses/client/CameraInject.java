/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.phys.Vec3
 */
package net.unusual.block_factorys_bosses.client;

import net.minecraft.world.phys.Vec3;

public interface CameraInject {
    public boolean isCinematic();

    public void setCinematic(boolean var1);

    public void setCinematicPosition(Vec3 var1);
}

