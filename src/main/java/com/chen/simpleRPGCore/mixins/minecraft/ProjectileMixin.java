package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.mixinsAPI.minecraft.IProjectileMixinExtension;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Projectile.class)
public class ProjectileMixin implements IProjectileMixinExtension {

    @Unique
    boolean src$isBypassesCooldownHit = false;

    public Projectile src$self() {
        return (Projectile) (Object) this;
    }

    @Override
    public boolean src$isBypassesCooldownHit() {
        return src$isBypassesCooldownHit;
    }

    @Override
    public void src$setBypassesCooldownHit(boolean isBypassesCooldownHit) {
        src$isBypassesCooldownHit = isBypassesCooldownHit;
    }
}
