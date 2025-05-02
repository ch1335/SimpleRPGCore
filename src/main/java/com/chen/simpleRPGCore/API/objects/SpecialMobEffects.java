package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffectType;
import com.chen.simpleRPGCore.common.specialEffects.TestEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SpecialMobEffects {
    public static final DeferredRegister<SpecialEffectType<?>> SPECIAL_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(SRCRegistries.SPECIAL_EFFECT, SimpleRPGCore.MODID);

    public static final DeferredHolder<SpecialEffectType<?>, SpecialEffectType<TestEffect>> TEST_EFFECT = SPECIAL_EFFECT_DEFERRED_REGISTER.register("test", () -> new SpecialEffectType<>(TestEffect::new));
}
