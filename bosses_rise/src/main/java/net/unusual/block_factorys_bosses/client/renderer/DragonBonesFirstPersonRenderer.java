/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.player.PlayerRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.item.Item
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.RenderArmEvent
 */
package net.unusual.block_factorys_bosses.client.renderer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.model.Modelchestplate;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber(value={Dist.CLIENT})
public class DragonBonesFirstPersonRenderer {
    private static boolean rendererActive = false;
    private static List<Item> disallowedItems = new ArrayList<Item>();

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        Item heldItem = mc.player.getMainHandItem().getItem();
        Item offHandItem = mc.player.getOffhandItem().getItem();
        if (disallowedItems.contains(heldItem) || disallowedItems.contains(offHandItem)) {
            rendererActive = false;
            return;
        }
        rendererActive = mc.player != null && BossesRiseItems.DRAGON_BONES_CHESTPLATE.get() == mc.player.getItemBySlot(EquipmentSlot.CHEST).getItem();
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void onRenderPlayerHand(RenderArmEvent event) {
        if (!rendererActive) {
            return;
        }
        if (disallowedItems.isEmpty()) {
            disallowedItems = List.of((Item)BossesRiseItems.SANDWORM_GAUNTLET.get(), (Item)BossesRiseItems.ICE_GAUNTLET.get());
        }
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        MultiBufferSource buffer = event.getMultiBufferSource();
        EntityRenderer entityRenderer = mc.getEntityRenderDispatcher().getRenderer((Entity)player);
        if (!(entityRenderer instanceof PlayerRenderer)) {
            return;
        }
        PlayerRenderer pr = (PlayerRenderer)entityRenderer;
        Modelchestplate<LocalPlayer> model = new Modelchestplate<LocalPlayer>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelchestplate.LAYER_LOCATION));
        ((PlayerModel)pr.getModel()).copyPropertiesTo(model);
        int index = Mth.floor((float)((float)(Math.round(AnimationTickHolder.getRenderTime()) % 40) * 0.4f));
        ResourceLocation armor_location = BossesRise.prefix("textures/entities/dragon_chestplate/dragon_chestplate_" + index + ".png");
        model.setupAnim(player, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? model.leftArm : model.rightArm;
        armPart.zRot = 0.125f;
        armPart.render(event.getPoseStack(), buffer.getBuffer(RenderType.entityCutoutNoCull((ResourceLocation)armor_location)), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
    }
}

