package com.chen.simpleRPGCore.mixins.minecraft;

import net.minecraft.world.effect.RegenerationMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegenerationMobEffect.class)
public class RegenerationMobEffectMixin {
    @Inject(method = "applyEffectTick",at=@At("HEAD"))
    private void applyEffectTick(LivingEntity p_295924_, int p_296417_, CallbackInfoReturnable<Boolean> cir){
        if (p_295924_.getHealth() == p_295924_.getMaxHealth()) {
            p_295924_.heal(1.0F);
        }
    }
}
