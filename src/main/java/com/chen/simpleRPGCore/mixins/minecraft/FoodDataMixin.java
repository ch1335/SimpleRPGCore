package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.API.objects.ShieldTypes;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Unique
    private float src$healBeforeOverHealAmount = 0;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V", ordinal = 0))
    private void healBefore(Player pPlayer, CallbackInfo ci) {
        if (!pPlayer.level().isClientSide) {
            MobExtraData mobExtraData = pPlayer.getCapability(SRCCapabilities.SRC_MOB_DATA);
            if (mobExtraData != null) {
                src$healBeforeOverHealAmount = mobExtraData.getShield(ShieldTypes.OVER_HEAL_SHIELD.get()).getAmount();
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V", shift = At.Shift.AFTER, ordinal = 0))
    private void healAfter(Player pPlayer, CallbackInfo ci) {
        if (!pPlayer.level().isClientSide) {
            MobExtraData mobExtraData = pPlayer.getCapability(SRCCapabilities.SRC_MOB_DATA);
            if (mobExtraData != null) {
                mobExtraData.getShield(ShieldTypes.OVER_HEAL_SHIELD.get()).setAmount(src$healBeforeOverHealAmount);
            }
        }
    }
}
