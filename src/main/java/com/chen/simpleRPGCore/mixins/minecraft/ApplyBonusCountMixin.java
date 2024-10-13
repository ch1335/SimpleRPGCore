package com.chen.simpleRPGCore.mixins.minecraft;


import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyArg(method = "run", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$Formula;calculateNewCount(Lnet/minecraft/util/RandomSource;II)I"))
    private int addMiningFortuneAttributeBonus(int fortuneLevel, @Local(argsOnly = true) LootContext context) {
        LivingEntity living = (LivingEntity) context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (this.enchantment.getKey() == Enchantments.FORTUNE && living != null && living.getAttributes().hasAttribute(SRCAttributes.MINING_FORTUNE)) {
            return (int) (fortuneLevel + living.getAttributeValue(SRCAttributes.MINING_FORTUNE));
        }
        return fortuneLevel;
    }
}
