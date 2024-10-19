package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.SimpleRPGConfig;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatRules.class)
public class CombatRulesMixin {
    @Inject(method = "getDamageAfterAbsorb", at = @At("HEAD"), cancellable = true)
    private static void getDamageAfterAbsorb(LivingEntity pEntity, float pDamage, DamageSource pDamageSource, float pArmorValue, float pArmorToughness, CallbackInfoReturnable<Float> cir) {
        if (SimpleRPGConfig.commonConfig.armorAbsorbFunction != null && !SimpleRPGConfig.commonConfig.useVanillaArmorAbsorbFunction) {
            DamageSourceExtraData extraData = ((IDamageSourceExtension) pDamageSource).src$getExtraData();
            cir.setReturnValue(SimpleRPGConfig.commonConfig.armorAbsorbFunction.accept(pEntity, pDamage, pDamageSource, pArmorValue, pArmorToughness, extraData));
        }
    }
}
