package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void MeleeAttack(Entity pTarget, CallbackInfo ci, @Local(ordinal = 0) LocalRef<DamageSource> damageSourceLocalRef) {
        IDamageSourceExtension iDamageSource = (IDamageSourceExtension) damageSourceLocalRef.get();
        iDamageSource.src$getExtraData().addMeleeDamageEntity(pTarget);
        damageSourceLocalRef.set((DamageSource) iDamageSource);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public void SweepingAttack(Entity pTarget, CallbackInfo ci, @Local(ordinal = 0) LocalRef<DamageSource> damageSourceLocalRef, @Local(ordinal = 0) LivingEntity livingEntity) {
        IDamageSourceExtension iDamageSource = (IDamageSourceExtension) damageSourceLocalRef.get();
        iDamageSource.src$getExtraData().addMeleeDamageEntity(livingEntity);
        iDamageSource.src$getExtraData().addSweepingDamageEntity(livingEntity);
        damageSourceLocalRef.set((DamageSource) iDamageSource);
    }
}
