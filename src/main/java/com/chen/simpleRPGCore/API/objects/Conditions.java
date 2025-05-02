package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.conditions.ManaSystemEnableCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class Conditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_DEFERRED_REGISTER = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, SimpleRPGCore.MODID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ManaSystemEnableCondition>> MANA_SYSTEM_ENABLE_CONDITION = CONDITION_DEFERRED_REGISTER.register("mana_system_enable", () -> ManaSystemEnableCondition.CODEC);

}
