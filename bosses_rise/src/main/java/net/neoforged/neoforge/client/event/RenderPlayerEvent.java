package net.neoforged.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;

public class RenderPlayerEvent {
    private final Player entity;
    private final PlayerRenderer renderer;
    private final float partialTick;
    private final PoseStack poseStack;

    public RenderPlayerEvent(Player entity, PlayerRenderer renderer, float partialTick, PoseStack poseStack) {
        this.entity = entity;
        this.renderer = renderer;
        this.partialTick = partialTick;
        this.poseStack = poseStack;
    }

    public Player getEntity() { return entity; }
    public PlayerRenderer getRenderer() { return renderer; }
    public float getPartialTick() { return partialTick; }
    public PoseStack getPoseStack() { return poseStack; }

    public static class Pre extends RenderPlayerEvent {
        public Pre(Player entity, PlayerRenderer renderer, float partialTick, PoseStack poseStack) {
            super(entity, renderer, partialTick, poseStack);
        }
    }

    public static class Post extends RenderPlayerEvent {
        public Post(Player entity, PlayerRenderer renderer, float partialTick, PoseStack poseStack) {
            super(entity, renderer, partialTick, poseStack);
        }
    }
}
