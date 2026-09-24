/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 */
package net.unusual.block_factorys_bosses.geckolib;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

public interface CommonPoseStack {
    public void bosses_rise_java$push();

    public void bosses_rise_java$pop();

    public void bosses_rise_java$translate(double var1, double var3, double var5);

    public void bosses_rise_java$translate(float var1, float var2, float var3);

    public void bosses_rise_java$scale(float var1, float var2, float var3);

    public void bosses_rise_java$mulPose(Matrix4f var1);

    public void bosses_rise_java$mulPose(Quaternionf var1);

    public Matrix4f bosses_rise_java$last();
}

