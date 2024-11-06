package com.chen.simpleRPGCore.mixinsAPI.minecraft;

import net.minecraft.world.entity.projectile.Projectile;

public interface IProjectileMixinExtension {
    Projectile src$self();

    boolean src$isBypassesCooldownHit();

    void src$setBypassesCooldownHit(boolean isBypassesCooldownHit);
}
