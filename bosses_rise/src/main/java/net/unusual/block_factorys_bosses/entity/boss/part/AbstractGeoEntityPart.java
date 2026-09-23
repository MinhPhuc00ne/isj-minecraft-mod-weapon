/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.FieldsAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3f
 */
package net.unusual.block_factorys_bosses.entity.boss.part;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPart;
import net.unusual.block_factorys_bosses.entity.boss.part.AbstractEntityPartParent;
import net.unusual.block_factorys_bosses.geckolib.ServerAnimationPlayer;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoAnimatable;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AbstractGeoEntityPart<ParentT extends Entity, PartT extends AbstractEntityPart<ParentT, PartT>>
extends AbstractEntityPart<ParentT, PartT> {
    private final String anchorBoneName;
    private final String bodyBoneName;

    public AbstractGeoEntityPart(ParentT parent, int partIndex, String anchorBoneName, String bodyBoneName, float width, float height) {
        super(parent, partIndex, width, height);
        this.anchorBoneName = anchorBoneName;
        this.bodyBoneName = bodyBoneName;
    }

    public String getAnchorBoneName() {
        return this.anchorBoneName;
    }

    public String getBodyBoneName() {
        return this.bodyBoneName;
    }

    public static <ParentT extends Entity & GeoAnimatable & AbstractEntityPartParent<ParentT, PartT>, PartT extends AbstractGeoEntityPart<ParentT, PartT>> void positionPartsFromBones(PartT[] parts, ServerAnimationPlayer<ParentT> serverAnimationPlayer, @Nullable Vec3 rootOffset) {
        ServerAnimationPlayer.ServerPoseStack poseStack = new ServerAnimationPlayer.ServerPoseStack();
        serverAnimationPlayer.applyWorldSpaceTransformations(poseStack);
        if (rootOffset != null) {
            poseStack.translate(rootOffset.x(), rootOffset.y(), rootOffset.z());
        }
        for (PartT part : parts) {
            ((AbstractGeoEntityPart)((Object)part)).xo = part.getX();
            ((AbstractGeoEntityPart)((Object)part)).yo = part.getY();
            ((AbstractGeoEntityPart)((Object)part)).zo = part.getZ();
            ((AbstractGeoEntityPart)((Object)part)).xOld = part.getX();
            ((AbstractGeoEntityPart)((Object)part)).yOld = part.getY();
            ((AbstractGeoEntityPart)((Object)part)).zOld = part.getZ();
            Vector3f bonePosition = serverAnimationPlayer.getBonePosition(poseStack, serverAnimationPlayer.getBone(((AbstractGeoEntityPart)((Object)part)).getAnchorBoneName()));
            part.setPos((double)bonePosition.x, (double)(bonePosition.y - part.getBbHeight() * 0.5f), (double)bonePosition.z);
        }
    }
}

