/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.GsonHelper
 *  software.bernie.geckolib.animation.Animation$Keyframes
 *  software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData
 *  software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData
 *  software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData
 *  software.bernie.geckolib.loading.json.raw.Bone
 *  software.bernie.geckolib.loading.json.raw.Cube
 *  software.bernie.geckolib.loading.json.raw.FaceUV
 *  software.bernie.geckolib.loading.json.raw.LocatorClass
 *  software.bernie.geckolib.loading.json.raw.LocatorValue
 *  software.bernie.geckolib.loading.json.raw.MinecraftGeometry
 *  software.bernie.geckolib.loading.json.raw.Model
 *  software.bernie.geckolib.loading.json.raw.ModelProperties
 *  software.bernie.geckolib.loading.json.raw.PolyMesh
 *  software.bernie.geckolib.loading.json.raw.PolysUnion
 *  software.bernie.geckolib.loading.json.raw.TextureMesh
 *  software.bernie.geckolib.loading.json.raw.UVFaces
 *  software.bernie.geckolib.loading.json.raw.UVUnion
 *  software.bernie.geckolib.loading.json.typeadapter.BakedAnimationsAdapter
 *  software.bernie.geckolib.loading.object.BakedAnimations
 */
package net.unusual.block_factorys_bosses.geckolib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.GsonHelper;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.loading.json.raw.Cube;
import software.bernie.geckolib.loading.json.raw.FaceUV;
import software.bernie.geckolib.loading.json.raw.LocatorClass;
import software.bernie.geckolib.loading.json.raw.LocatorValue;
import software.bernie.geckolib.loading.json.raw.MinecraftGeometry;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.raw.ModelProperties;
import software.bernie.geckolib.loading.json.raw.PolyMesh;
import software.bernie.geckolib.loading.json.raw.PolysUnion;
import software.bernie.geckolib.loading.json.raw.TextureMesh;
import software.bernie.geckolib.loading.json.raw.UVFaces;
import software.bernie.geckolib.loading.json.raw.UVUnion;
import software.bernie.geckolib.loading.json.typeadapter.BakedAnimationsAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomKeyFramesAdapter
implements JsonDeserializer<Animation.Keyframes> {
    public static final Gson GEO_GSON = new GsonBuilder().setLenient().registerTypeAdapter(Bone.class, (Object)Bone.deserializer()).registerTypeAdapter(Cube.class, (Object)Cube.deserializer()).registerTypeAdapter(FaceUV.class, (Object)FaceUV.deserializer()).registerTypeAdapter(LocatorClass.class, (Object)LocatorClass.deserializer()).registerTypeAdapter(LocatorValue.class, (Object)LocatorValue.deserializer()).registerTypeAdapter(MinecraftGeometry.class, (Object)MinecraftGeometry.deserializer()).registerTypeAdapter(Model.class, (Object)Model.deserializer()).registerTypeAdapter(ModelProperties.class, (Object)ModelProperties.deserializer()).registerTypeAdapter(PolyMesh.class, (Object)PolyMesh.deserializer()).registerTypeAdapter(PolysUnion.class, (Object)PolysUnion.deserializer()).registerTypeAdapter(TextureMesh.class, (Object)TextureMesh.deserializer()).registerTypeAdapter(UVFaces.class, (Object)UVFaces.deserializer()).registerTypeAdapter(UVUnion.class, (Object)UVUnion.deserializer()).registerTypeAdapter(Animation.Keyframes.class, (Object)new CustomKeyFramesAdapter()).registerTypeAdapter(BakedAnimations.class, (Object)new BakedAnimationsAdapter()).create();

    public Animation.Keyframes deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        SoundKeyframeData[] sounds = CustomKeyFramesAdapter.buildSoundFrameData(obj);
        ParticleKeyframeData[] particles = CustomKeyFramesAdapter.buildParticleFrameData(obj);
        CustomInstructionKeyframeData[] customInstructions = CustomKeyFramesAdapter.buildCustomFrameData(obj);
        return new Animation.Keyframes(sounds, particles, customInstructions);
    }

    private static SoundKeyframeData[] buildSoundFrameData(JsonObject rootObj) {
        JsonObject soundsObj = GsonHelper.getAsJsonObject((JsonObject)rootObj, (String)"sound_effects", (JsonObject)new JsonObject());
        SoundKeyframeData[] sounds = new SoundKeyframeData[soundsObj.size()];
        int index = 0;
        for (Map.Entry entry : soundsObj.entrySet()) {
            sounds[index] = new SoundKeyframeData(Double.valueOf(Double.parseDouble((String)entry.getKey()) * 20.0), GsonHelper.getAsString((JsonObject)((JsonElement)entry.getValue()).getAsJsonObject(), (String)"effect"));
            ++index;
        }
        return sounds;
    }

    private static ParticleKeyframeData[] buildParticleFrameData(JsonObject rootObj) {
        JsonObject particlesObj = GsonHelper.getAsJsonObject((JsonObject)rootObj, (String)"particle_effects", (JsonObject)new JsonObject());
        ArrayList<ParticleKeyframeData> list = new ArrayList<ParticleKeyframeData>();
        for (Map.Entry entry : particlesObj.entrySet()) {
            double startTick = Double.parseDouble((String)entry.getKey()) * 20.0;
            Object object = entry.getValue();
            if (object instanceof JsonObject) {
                JsonObject object2 = (JsonObject)object;
                String effect = GsonHelper.getAsString((JsonObject)object2, (String)"effect", (String)"");
                String locator = GsonHelper.getAsString((JsonObject)object2, (String)"locator", (String)"");
                String script = GsonHelper.getAsString((JsonObject)object2, (String)"pre_effect_script", (String)"");
                list.add(new ParticleKeyframeData(startTick, effect, locator, script));
                continue;
            }
            object = entry.getValue();
            if (!(object instanceof JsonArray)) continue;
            JsonArray array = (JsonArray)object;
            for (JsonElement element : array) {
                if (!(element instanceof JsonObject)) continue;
                JsonObject object3 = (JsonObject)element;
                String effect = GsonHelper.getAsString((JsonObject)object3, (String)"effect", (String)"");
                String locator = GsonHelper.getAsString((JsonObject)object3, (String)"locator", (String)"");
                String script = GsonHelper.getAsString((JsonObject)object3, (String)"pre_effect_script", (String)"");
                list.add(new ParticleKeyframeData(startTick, effect, locator, script));
            }
        }
        return list.toArray(new ParticleKeyframeData[0]);
    }

    private static CustomInstructionKeyframeData[] buildCustomFrameData(JsonObject rootObj) {
        JsonObject customInstructionsObj = GsonHelper.getAsJsonObject((JsonObject)rootObj, (String)"timeline", (JsonObject)new JsonObject());
        CustomInstructionKeyframeData[] customInstructions = new CustomInstructionKeyframeData[customInstructionsObj.size()];
        int index = 0;
        for (Map.Entry entry : customInstructionsObj.entrySet()) {
            String instructions = "";
            Object v = entry.getValue();
            if (v instanceof JsonArray) {
                JsonArray array = (JsonArray)v;
                instructions = ((ObjectArrayList)GEO_GSON.fromJson((JsonElement)array, ObjectArrayList.class)).toString();
            } else {
                v = entry.getValue();
                if (v instanceof JsonPrimitive) {
                    JsonPrimitive primitive = (JsonPrimitive)v;
                    instructions = primitive.getAsString();
                }
            }
            customInstructions[index] = new CustomInstructionKeyframeData(Double.parseDouble((String)entry.getKey()) * 20.0, instructions);
            ++index;
        }
        return customInstructions;
    }
}

