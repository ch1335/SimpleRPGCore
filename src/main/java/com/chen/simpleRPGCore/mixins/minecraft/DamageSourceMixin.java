package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IProjectileMixinExtension;
import net.minecraft.core.Holder;
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


@Mixin(DamageSource.class)
public class DamageSourceMixin implements IDamageSourceExtension {
    @Unique
    private DamageSourceExtraData src$extraData;

    @Inject(method = "<init>(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
    private void init(Holder<DamageType> pType, Entity pDirectEntity, Entity pCausingEntity, Vec3 pDamageSourcePosition, CallbackInfo ci) {
        DamageSourceExtraData.OriginalState.Builder builder = new DamageSourceExtraData.OriginalState.Builder();
        if (pDirectEntity instanceof Projectile projectile && ((IProjectileMixinExtension) projectile).src$isBypassesCooldownHit()) {
            builder.setBypassesCooldown(true);
        }
        src$extraData = new DamageSourceExtraData(pCausingEntity, builder.build());
    }

    @Unique
    public DamageSourceExtraData src$getExtraData() {
        return src$extraData;
    }
}
