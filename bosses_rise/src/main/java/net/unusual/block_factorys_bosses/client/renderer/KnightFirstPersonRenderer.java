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
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.neoforged.api.distmarker.Dist
 *  net.neoforged.bus.api.EventPriority
 *  net.neoforged.bus.api.SubscribeEvent
 *  net.neoforged.fml.common.EventBusSubscriber
 *  net.neoforged.neoforge.client.event.RenderArmEvent
 */
package net.unusual.block_factorys_bosses.client.renderer;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelknight_arm;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@EventBusSubscriber(value={Dist.CLIENT})
public class KnightFirstPersonRenderer {
    private static boolean rendererActive = false;

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        rendererActive = mc.player != null && BossesRiseItems.KNIGHT_CHESTPLATE.get() == mc.player.getItemBySlot(EquipmentSlot.CHEST).getItem();
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void onRenderPlayerHand(RenderArmEvent event) {
        if (!rendererActive) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        MultiBufferSource buffer = event.getMultiBufferSource();
        EntityRenderer entityRenderer = mc.getEntityRenderDispatcher().getRenderer((Entity)player);
        if (!(entityRenderer instanceof PlayerRenderer)) {
            return;
        }
        PlayerRenderer pr = (PlayerRenderer)entityRenderer;
        Modelknight_arm<LocalPlayer> model = new Modelknight_arm<LocalPlayer>(Minecraft.getInstance().getEntityModels().bakeLayer(Modelknight_arm.LAYER_LOCATION));
        ((PlayerModel)pr.getModel()).copyPropertiesTo(model);
        ResourceLocation armor_location = BossesRise.prefix("textures/entities/knight_arm.png");
        model.setupAnim(player, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? model.l_arm : model.r_arm;
        armPart.zRot = 0.125f;
        armPart.render(event.getPoseStack(), buffer.getBuffer(RenderType.entityCutoutNoCull((ResourceLocation)armor_location)), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
        event.setCanceled(true);
    }
}

