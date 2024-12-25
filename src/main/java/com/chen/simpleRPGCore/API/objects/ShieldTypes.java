package com.chen.simpleRPGCore.API.objects;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.ShieldSystem.UnitShield;
import net.minecraft.resources.ResourceLocation;

public class ShieldTypes {
    public static final Shield.ShieldType<UnitShield> OVER_HEAL_SHIELD = Shield.ShieldType.getOrCreate(ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "over_heal"));
}
