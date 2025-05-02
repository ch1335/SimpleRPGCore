package com.chen.simpleRPGCore.common.conditions;

import com.chen.simpleRPGCore.SimpleRPGConfig;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public class ManaSystemEnableCondition implements ICondition {

    public static MapCodec<ManaSystemEnableCondition> CODEC = MapCodec.of(Encoder.empty(), Decoder.unit(new ManaSystemEnableCondition()));

    @Override
    public boolean test(@NotNull IContext context) {
        return SimpleRPGConfig.commonConfig.enableManaSystem;
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
