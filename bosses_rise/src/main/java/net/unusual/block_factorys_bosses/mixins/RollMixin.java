/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.unusual.block_factorys_bosses.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Entity.class})
public class RollMixin {
    @Inject(method={"setTicksFrozen"}, at={@At(value="HEAD")}, cancellable=true)
    private void freshNeverFrozen(int ticksFrozen, CallbackInfo ci) {
        Player player;
        Entity entity = (Entity)(Object)this;
        if (entity instanceof Player && RollAttachment.fromPlayer(player = (Player)entity).isInvulnerable()) {
            ci.cancel();
        }
    }
}

