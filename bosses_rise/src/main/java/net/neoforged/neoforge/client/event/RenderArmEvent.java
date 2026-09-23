package net.neoforged.neoforge.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;

public class RenderArmEvent {
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;
    private final HumanoidArm arm;
    private boolean canceled;

    public RenderArmEvent(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, HumanoidArm arm) {
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
        this.arm = arm;
    }

    public PoseStack getPoseStack() { return poseStack; }
    public MultiBufferSource getMultiBufferSource() { return multiBufferSource; }
    public int getPackedLight() { return packedLight; }
    public HumanoidArm getArm() { return arm; }
    public boolean isCanceled() { return canceled; }
    public void setCanceled(boolean canceled) { this.canceled = canceled; }
}
