/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  software.bernie.geckolib.loading.math.MathParser
 *  software.bernie.geckolib.loading.math.MathValue
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface Curve {
    public double interpolate(float var1);

    public static List<MathValue> parseNodes(JsonObject curveObject) {
        JsonArray nodesInput = curveObject.getAsJsonArray("nodes");
        ArrayList<MathValue> points = new ArrayList<MathValue>(nodesInput.size());
        for (JsonElement nodeInput : nodesInput) {
            MathValue point = MathParser.parseJson((JsonElement)nodeInput);
            points.add(point);
        }
        return points;
    }
}

