/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.Mth
 *  software.bernie.geckolib.loading.math.value.Constant
 */
package net.unusual.block_factorys_bosses.client.particle.bedrock.curve;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Mth;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.BezierCurve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.Curve;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record BezierChainCurve(List<BezierChainSegment> segments) implements Curve
{
    public BezierChainCurve {
        if (segments.isEmpty()) {
            throw new IllegalArgumentException("Curve with type bezier_chain must have at least 1 segment.");
        }
    }

    @Override
    public double interpolate(float progress) {
        BezierChainSegment firstSegment;
        if ((progress = Mth.clamp((float)progress, (float)0.0f, (float)1.0f)) < (firstSegment = (BezierChainSegment)this.segments.getFirst()).start()) {
            return firstSegment.curve().interpolate(0.0f);
        }
        for (BezierChainSegment segment : this.segments) {
            if (!(progress < segment.end())) continue;
            return segment.curve().interpolate(Mth.map((float)progress, (float)segment.start(), (float)segment.end(), (float)0.0f, (float)1.0f));
        }
        return ((BezierChainSegment)this.segments.getLast()).curve().interpolate(1.0f);
    }

    public static BezierChainCurve deserialize(JsonObject curveObject) {
        List<BezierChainSegment> segments = BezierChainSegment.deserialize(curveObject);
        return new BezierChainCurve(segments);
    }

    public record BezierChainSegment(float start, float end, BezierCurve curve) {
        public static List<BezierChainSegment> deserialize(JsonObject curveObject) {
            List<ParsedBezierChainNode> nodes = ParsedBezierChainNode.parseNodes(curveObject);
            ArrayList<BezierChainSegment> segments = new ArrayList<BezierChainSegment>(nodes.size() - 1);
            for (int nodeIndex = 0; nodeIndex < nodes.size() - 1; ++nodeIndex) {
                ParsedBezierChainNode startNode = nodes.get(nodeIndex);
                ParsedBezierChainNode endNode = nodes.get(nodeIndex + 1);
                segments.add(new BezierChainSegment(startNode.progress(), endNode.progress(), new BezierCurve(List.of(new Constant((double)startNode.rightValue()), new Constant((double)startNode.rightSlope()), new Constant((double)endNode.leftSlope()), new Constant((double)endNode.leftValue())))));
            }
            return segments;
        }
    }

    private record ParsedBezierChainNode(float progress, float leftValue, float leftSlope, float rightSlope, float rightValue) {
        private static float getFloatOrFallback(JsonObject json, String primaryKey, String fallbackKey) {
            return json.has(primaryKey) ? json.getAsJsonPrimitive(primaryKey).getAsFloat() : json.getAsJsonPrimitive(fallbackKey).getAsFloat();
        }

        private static ParsedBezierChainNode parseNode(String key, JsonObject nodeJson) {
            float progress = Float.parseFloat(key);
            float leftSlope = ParsedBezierChainNode.getFloatOrFallback(nodeJson, "left_slope", "slope");
            float rightSlope = ParsedBezierChainNode.getFloatOrFallback(nodeJson, "right_slope", "slope");
            float leftValue = ParsedBezierChainNode.getFloatOrFallback(nodeJson, "left_value", "value");
            float rightValue = ParsedBezierChainNode.getFloatOrFallback(nodeJson, "right_value", "value");
            return new ParsedBezierChainNode(progress, leftValue, leftValue + leftSlope / 3.0f, rightValue - rightSlope / 3.0f * 2.0f, rightValue);
        }

        public static List<ParsedBezierChainNode> parseNodes(JsonObject curveObject) {
            JsonObject nodesInput = curveObject.getAsJsonObject("nodes");
            if (nodesInput.size() < 2) {
                throw new IllegalArgumentException("Curve with type bezier_chain must have at least 2 nodes, but has " + nodesInput.size() + ".");
            }
            ArrayList<ParsedBezierChainNode> parsedNodes = new ArrayList<ParsedBezierChainNode>(nodesInput.size());
            for (Map.Entry entry : nodesInput.entrySet()) {
                ParsedBezierChainNode parsedNode = ParsedBezierChainNode.parseNode((String)entry.getKey(), ((JsonElement)entry.getValue()).getAsJsonObject());
                parsedNodes.add(parsedNode);
            }
            parsedNodes.sort((a, b) -> Float.compare(a.progress, b.progress));
            return parsedNodes;
        }
    }
}

