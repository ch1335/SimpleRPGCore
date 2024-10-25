package com.chen.simpleRPGCore.mixins.apothicAttributes;

import dev.shadowsoffire.apothic_attributes.impl.AttributeEvents;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttributeEvents.class)
public class AttributeEventsMixin {
    @Inject(method = "heal",at = @At("HEAD"), cancellable = true)
    private void heal(LivingHealEvent e, CallbackInfo ci){
        ci.cancel();
    }
    @Inject(method = "apothCriticalStrike",at = @At("HEAD"), cancellable = true)
    private void apothCriticalStrike(LivingIncomingDamageEvent e, CallbackInfo ci){
        ci.cancel();
    }
    @Inject(method = "lifeStealOverheal",at = @At("HEAD"), cancellable = true)
    private void lifeStealOverheal(LivingDamageEvent.Post e, CallbackInfo ci){
        ci.cancel();
    }
}
