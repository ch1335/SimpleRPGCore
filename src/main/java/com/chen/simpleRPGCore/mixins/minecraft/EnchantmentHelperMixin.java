package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getEnchantmentLevel",at = @At("RETURN"), cancellable = true)
    private static void getEnchantmentLevel(Holder<Enchantment> pEnchantment, LivingEntity pEntity, CallbackInfoReturnable<Integer> cir){
        if (pEnchantment == Enchantments.LOOTING && pEntity.getAttributes().hasAttribute(SRCAttributes.MOB_LOOTING)) {
            cir.setReturnValue((int) (cir.getReturnValue() + pEntity.getAttributeValue(SRCAttributes.MOB_LOOTING)));
        }
    }
}
