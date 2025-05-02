package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.Shields.OverHealShield;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ShieldTypes {
    public static final DeferredRegister<Shield.ShieldType<?>> SHIELD_TYPE_DEFERRED_REGISTER = DeferredRegister.create(SRCRegistries.SHIELD_TYPE, SimpleRPGCore.MODID);

    public static final DeferredHolder<Shield.ShieldType<?>, Shield.ShieldType<OverHealShield>> OVER_HEAL_SHIELD = SHIELD_TYPE_DEFERRED_REGISTER.register("over_heal", () -> new Shield.ShieldType<>(OverHealShield::new));


}
