package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientboundDamageEventPacket.class, priority = 1335)
public class ClientboundDamageEventPacketMixin {

    @Shadow
    @Final
    private int entityId;
    @Unique
    private DamageSource src$source;

    @Unique
    private boolean src$isCritical = false;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("RETURN"))
    private void init(Entity p_270474_, DamageSource p_270781_, CallbackInfo ci) {
        src$source = p_270781_;
    }

    @Inject(method = "write", at = @At("RETURN"))
    private void write(RegistryFriendlyByteBuf buffer, CallbackInfo ci) {
        if (src$source != null) {
            IDamageSourceExtension extension = (IDamageSourceExtension) src$source;
            DamageSourceExtraData extraData = extension.src$getExtraData();
            if (extraData.isCriticalDamageToEntity(entityId)) {
                src$isCritical = true;
            }
        }
        buffer.writeBoolean(src$isCritical);
    }

    @Inject(method = "<init>(Lnet/minecraft/network/RegistryFriendlyByteBuf;)V", at = @At("RETURN"))
    private void receive(RegistryFriendlyByteBuf p_321729_, CallbackInfo ci) {
        src$isCritical = p_321729_.readBoolean();
    }

    @Inject(method = "getSource", at = @At("RETURN"))
    private void getSource(Level level, CallbackInfoReturnable<DamageSource> cir) {
        DamageSource damageSource = cir.getReturnValue();
        IDamageSourceExtension extension = (IDamageSourceExtension) damageSource;
        DamageSourceExtraData extraData = extension.src$getExtraData();
        if (src$isCritical) {
            extraData.addCriticalDamageEntity(entityId);
        }
    }
}
