package com.chen.simpleRPGCore.common;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceMixin;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class EventHandler {
    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class Game {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
            DamageContainer container = event.getContainer();
            Entity source = container.getSource().getEntity();
            LivingEntity livingEntity = event.getEntity();
            DamageSourceExtraData extraData = ((IDamageSourceMixin) container.getSource()).src$getExtraData();


            if (extraData.isMeleeDamageToEntity(livingEntity)) {
                container.setPostAttackInvulnerabilityTicks(0);
            }

            if (SRCEventFactory.modifyDamageBeforeCritical(container, livingEntity)) event.setCanceled(true);

            float criticalChance = (float) extraData.getAttributeOriginalHolder(SRCAttributes.CRITICAL_CHANCE).getNew(0);

            if (livingEntity.getRandom().nextFloat() <= criticalChance && SRCEventFactory.modPreCritical(container, livingEntity)) {
                float criticalDamage = (float) extraData.getAttributeOriginalHolder(SRCAttributes.CRITICAL_DAMAGE).getNew(1);
                extraData.addCriticalDamageEntity(livingEntity);
                container.setNewDamage(container.getNewDamage() * criticalDamage);
                if (source != null) {
                    source.level().playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, source.getSoundSource(), 1.0F, 1.0F);
                    if (source instanceof LivingEntity livingEntity1 && livingEntity1.level() instanceof ServerLevel level) {
                        level.getChunkSource().broadcastAndSend(livingEntity1, new ClientboundAnimatePacket(livingEntity, 4));
                    }
                }
            }

            if(SRCEventFactory.modifyDamageAfterCritical(container, livingEntity)) event.setCanceled(true);

        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void restDamage(LivingIncomingDamageEvent event) {
            ((IDamageSourceMixin) event.getContainer().getSource()).src$getExtraData().restToOriginal();
        }
    }


    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void modifyAttribute(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SRCAttributes.CRITICAL_CHANCE);
            event.add(EntityType.PLAYER, SRCAttributes.CRITICAL_DAMAGE);
            event.add(EntityType.PLAYER, SRCAttributes.LIFE_STEAL);
            event.add(EntityType.PLAYER, SRCAttributes.ARMOR_PENETRATION);
        }
    }
}
