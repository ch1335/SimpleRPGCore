package com.chen.simpleRPGCore.utils;

import com.chen.simpleRPGCore.tags.SRCDamageTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.Tags;

public class Util {
    public static int toInt(double value, RandomSource randomSource) {
        int intValue = (int) Math.floor(value);
        return intValue + ((randomSource.nextFloat() < (value - intValue)) ? 1 : 0);
    }

    public static boolean isPhysicalDamage(DamageSource source) {
        return !source.is(DamageTypes.MAGIC) &&
                !source.is(DamageTypes.INDIRECT_MAGIC) &&
                !source.is(DamageTypeTags.IS_FIRE) &&
                !source.is(DamageTypeTags.IS_EXPLOSION) &&
                !source.is(SRCDamageTags.MOD_MAGE_DAMAGE) ||
                source.is(Tags.DamageTypes.IS_PHYSICAL)
                ;
    }

    public static boolean canCriticalByTag(DamageSource source) {
        return source.is(SRCDamageTags.CAN_CRITICAL) ||
                (source.is(DamageTypeTags.IS_PROJECTILE) && source.is(Tags.DamageTypes.IS_PHYSICAL)) ||
                source.is(DamageTypeTags.IS_PLAYER_ATTACK) ||
                source.is(DamageTypes.MOB_ATTACK)
                ;
    }
}
