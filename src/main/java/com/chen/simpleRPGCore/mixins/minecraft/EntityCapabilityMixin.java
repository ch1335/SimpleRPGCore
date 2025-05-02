package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityCapability.class)
public class EntityCapabilityMixin {
    @Inject(method = "getCapability", at = @At("RETURN"))
    private void get(Entity entity, Object context, CallbackInfoReturnable<Object> cir) {
        Object o = cir.getReturnValue();
        if (o != null && o.getClass() == PlayerExtraData.class && entity instanceof Player player && ((PlayerExtraData) o).player != entity) {
            ((PlayerExtraData) o).player = player;
        }
    }
}
