package com.chen.simpleRPGCore.mixins.minecraft;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Inject(method = "tick",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V",shift = At.Shift.AFTER,ordinal = 0))
    private void heal(Player pPlayer, CallbackInfo ci){
        if (pPlayer.getAbsorptionAmount()<1) {
            pPlayer.setAbsorptionAmount(0);
        }
    }
}
