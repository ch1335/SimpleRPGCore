package com.chen.simpleRPGCore.common.specialEffects;

import com.chen.simpleRPGCore.API.objects.SpecialMobEffects;
import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class TestEffect extends SpecialEffect {
    public TestEffect(Entity source) {
        super(source, SpecialMobEffects.TEST_EFFECT.get());
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        if (getSource() instanceof LivingEntity living && living.isRemoved()) {
            SimpleRPGCore.LOGGER.info("aaaa");
        }

        super.tick(livingEntity);
        livingEntity.hurt(livingEntity.damageSources().fall(), 1);
    }
}
