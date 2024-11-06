package com.chen.simpleRPGCore.event.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;


public class ModifyProjectileEvent extends Event {
    public final Projectile projectile;

    public final LivingEntity shooter;

    public final ItemStack itemStack;

    public final InteractionHand shootHand;

    public boolean isCrit;

    public final ServerLevel serverLevel;

    public ModifyProjectileEvent(ServerLevel serverLevel, Projectile projectile, LivingEntity shooter, ItemStack itemStack, InteractionHand shootHand, boolean isCrit) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.itemStack = itemStack;
        this.shootHand = shootHand;
        this.isCrit = isCrit;
        this.serverLevel = serverLevel;
    }
}
