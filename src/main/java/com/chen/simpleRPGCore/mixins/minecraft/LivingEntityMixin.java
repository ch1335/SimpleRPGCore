package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceMixin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Stack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract float getHealth();

    @Shadow @Nullable protected Stack<DamageContainer> damageContainers;

    @Inject(method = "actuallyHurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private void actuallyHurt(DamageSource damageSource, float pDamageAmount, CallbackInfo ci){
        float actuallyHealthLost = Math.min(this.getHealth(),this.damageContainers.peek().getNewDamage());

        LivingEntity livingEntity = (LivingEntity)(Object) this;
        DamageSourceExtraData extraData = ((IDamageSourceMixin) damageSource).src$getExtraData();

        float life_steal = (float) extraData.getAttributeOriginalHolder(SRCAttributes.LIFE_STEAL).getNew(0);

        if (damageSource.getEntity() instanceof LivingEntity living && extraData.isMeleeDamageToEntity(livingEntity)) {
            living.heal(actuallyHealthLost * life_steal);
        }
    }
}
