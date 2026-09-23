/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.FastColor$ARGB32
 *  net.minecraft.util.Mth
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record ColorGradient(List<ColorNode> nodes) {
    private static final Pattern COLOR_STRING_PATTERN = Pattern.compile("^#([0-9a-f]{2})?([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$", 2);

    public int getColor(float progress) {
        ColorNode firstNode;
        if ((progress = Mth.clamp((float)progress, (float)0.0f, (float)1.0f)) <= (firstNode = (ColorNode)this.nodes.getFirst()).progress()) {
            return firstNode.color();
        }
        for (int index = this.nodes.size() - 2; index >= 0; --index) {
            ColorNode previousNode = this.nodes.get(index);
            if (!(progress >= previousNode.progress())) continue;
            ColorNode nextNode = this.nodes.get(index + 1);
            float x = (progress - previousNode.progress()) / nextNode.progress();
            return FastColor.ARGB32.lerp((float)x, (int)previousNode.color(), (int)nextNode.color());
        }
        return ((ColorNode)this.nodes.getLast()).color();
    }

    public static int parseColor(JsonElement jsonElement) {
        JsonPrimitive jsonPrimitive;
        if (jsonElement == null) {
            throw new IllegalArgumentException("Received null color.");
        }
        if (jsonElement instanceof JsonPrimitive && (jsonPrimitive = (JsonPrimitive)jsonElement).isString()) {
            String colorString = jsonPrimitive.getAsString();
            Matcher matcher = COLOR_STRING_PATTERN.matcher(colorString);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid color string: " + colorString);
            }
            String alphaString = matcher.group(1);
            int alpha = alphaString == null || alphaString.isEmpty() ? 255 : Integer.parseInt(alphaString, 16);
            int red = Integer.parseInt(matcher.group(2), 16);
            int green = Integer.parseInt(matcher.group(3), 16);
            int blue = Integer.parseInt(matcher.group(4), 16);
            return FastColor.ARGB32.color((int)alpha, (int)red, (int)green, (int)blue);
        }
        if (jsonElement instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray)jsonElement;
            if (jsonArray.size() != 3 && jsonArray.size() != 4) {
                throw new IllegalArgumentException("Color array must have exactly 3 or 4 elements.");
            }
            int red = (int)(jsonArray.get(0).getAsFloat() * 255.0f);
            int green = (int)(jsonArray.get(1).getAsFloat() * 255.0f);
            int blue = (int)(jsonArray.get(2).getAsFloat() * 255.0f);
            int alpha = jsonArray.size() < 3 ? 255 : (int)(jsonArray.get(3).getAsFloat() * 255.0f);
            return FastColor.ARGB32.color((int)alpha, (int)red, (int)green, (int)blue);
        }
        throw new IllegalArgumentException("Color must be a string, or an array, but received " + String.valueOf(jsonElement));
    }

    public static ColorGradient deserialize(JsonElement jsonElement) {
        if (jsonElement instanceof JsonArray) {
            JsonArray gradientArray = (JsonArray)jsonElement;
            int colorCount = gradientArray.size();
            ArrayList<ColorNode> nodes = new ArrayList<ColorNode>(colorCount);
            for (int index = 0; index < gradientArray.size(); ++index) {
                JsonElement colorJson = gradientArray.get(index);
                nodes.add(new ColorNode((float)index / (float)colorCount, ColorGradient.parseColor(colorJson)));
            }
            return new ColorGradient(nodes);
        }
        if (jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject)jsonElement;
            if (jsonObject.isEmpty()) {
                throw new IllegalArgumentException("Color gradient has no colors.");
            }
            ArrayList<ColorNode> parsedNodes = new ArrayList<ColorNode>(jsonObject.size());
            for (Map.Entry entry : jsonObject.entrySet()) {
                ColorNode parsedNode = ColorNode.deserialize((String)entry.getKey(), (JsonElement)entry.getValue());
                parsedNodes.add(parsedNode);
            }
            parsedNodes.sort((a, b) -> Float.compare(a.progress, b.progress));
            return new ColorGradient(parsedNodes);
        }
        throw new IllegalArgumentException("Color gradient must be an object or array of colors, but received " + String.valueOf(jsonElement));
    }

    public record ColorNode(float progress, int color) {
        @Override
        public String toString() {
            return "ColorNode(progress = %.3f, color = #%08x)".formatted(Float.valueOf(this.progress), this.color);
        }

        public static ColorNode deserialize(String progress, JsonElement color) {
            return new ColorNode(Float.parseFloat(progress), ColorGradient.parseColor(color));
        }
    }
}

