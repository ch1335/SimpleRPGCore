package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IProjectileMixinExtension;
import net.minecraft.core.Holder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(DamageSource.class)
public class DamageSourceMixin implements IDamageSourceExtension {
    @Unique
    private DamageSourceExtraData src$extraData;

    @Inject(method = "<init>(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
    private void init(Holder<DamageType> pType, Entity pDirectEntity, Entity pCausingEntity, Vec3 pDamageSourcePosition, CallbackInfo ci) {
        src$extraData = new DamageSourceExtraData(pCausingEntity);
        if (pDirectEntity instanceof Projectile projectile && ((IProjectileMixinExtension) projectile).src$isBypassesCooldownHit()) {
            src$extraData.addOriginalAdditionTags(DamageTypeTags.BYPASSES_COOLDOWN);
        }
    }

    @Unique
    public DamageSourceExtraData src$getExtraData() {
        return src$extraData;
    }

    @Inject(method = "is(Lnet/minecraft/tags/TagKey;)Z", at = @At("RETURN"), cancellable = true)
    private void is(TagKey<DamageType> damageTypeKey, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && src$extraData.getAdditionalTags().contains(damageTypeKey)) {
            cir.setReturnValue(true);
        }
    }
}
