package com.chen.simpleRPGCore.common;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.ILivingEntityMixinExtension;
import com.chen.simpleRPGCore.network.PlayerExtraDataSycPack;
import dev.shadowsoffire.apothic_attributes.payload.CritParticlePayload;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class EventHandler {
    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class Game {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
            DamageContainer container = event.getContainer();
            LivingEntity livingEntity = event.getEntity();
            DamageSourceExtraData extraData = ((IDamageSourceExtension) container.getSource()).src$getExtraData();


            if (SRCEventFactory.modifyDamageBeforeCritical(container, livingEntity)) event.setCanceled(true);

            float criticalChance = (float) extraData.getAttributeOriginalHolder(SRCAttributes.CRITICAL_CHANCE).getNew(0);

            if (livingEntity.getRandom().nextFloat() <= criticalChance && SRCEventFactory.modPreCritical(container, livingEntity)) {
                float criticalDamage = (float) extraData.getAttributeOriginalHolder(SRCAttributes.CRITICAL_DAMAGE).getNew(1);
                extraData.addCriticalDamageEntity(livingEntity);
                container.setNewDamage(container.getNewDamage() * criticalDamage);
            }

            if (extraData.isBypassesCooldown()) {
                extraData.originalInvulnerabilityTicksAfterAttack = container.getPostAttackInvulnerabilityTicks();
                container.setPostAttackInvulnerabilityTicks(0);
            }

            if (SRCEventFactory.modifyDamageAfterCritical(container, livingEntity)) event.setCanceled(true);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void restDamage(LivingIncomingDamageEvent event) {
            ((IDamageSourceExtension) event.getContainer().getSource()).src$getExtraData().restToOriginal();
        }

        @SubscribeEvent
        public static void onLivingDamagePost(LivingDamageEvent.Post event) {
            DamageSource damageSource = event.getSource();
            LivingEntity livingEntity = event.getEntity();
            Entity attacker = damageSource.getDirectEntity();
            DamageSourceExtraData extraData = ((IDamageSourceExtension) damageSource).src$getExtraData();
            if (extraData.isBypassesCooldown()) {
                event.getEntity().invulnerableTime = extraData.originalInvulnerabilityTicksAfterAttack;
            }

            if (attacker != null && extraData.isCriticalDamageToEntity(livingEntity)) {
                attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, attacker.getSoundSource(), 1.0F, 1.0F);
                if (attacker instanceof LivingEntity livingEntity1 && livingEntity1.level() instanceof ServerLevel level) {
                    if (SimpleRPGCore.apothicAttributesLoaded) {
                        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) attacker.level(), livingEntity.chunkPosition(), new CritParticlePayload(livingEntity.getId()));
                    } else {
                        level.getChunkSource().broadcastAndSend(livingEntity1, new ClientboundAnimatePacket(livingEntity, 4));
                    }
                }
            }

            if (livingEntity.getAbsorptionAmount()>0 && livingEntity.getAbsorptionAmount() < livingEntity.getAttributeValue(SRCAttributes.MAX_OVER_HEAL_AMOUNT)) {
                livingEntity.getAttributes().getInstance(Attributes.MAX_ABSORPTION).removeModifier(ILivingEntityMixinExtension.OVER_HEAL);
                livingEntity.getAttributes().getInstance(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ILivingEntityMixinExtension.OVER_HEAL, livingEntity.getAbsorptionAmount(), AttributeModifier.Operation.ADD_VALUE));

            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void LivingHealEvent(LivingHealEvent event) {
            LivingEntity living = event.getEntity();
            if (living.getAttributes().hasAttribute(SRCAttributes.HEAL_EFFECT)) {
                double healEffect = living.getAttributeValue(SRCAttributes.HEAL_EFFECT);
                event.setAmount((float) (event.getAmount() * healEffect));
            }

            float overheal = (float) living.getAttributeValue(SRCAttributes.OVER_HEAL);
            float maxOverheal = (float) living.getAttributeValue(SRCAttributes.MAX_OVER_HEAL_AMOUNT);
            if (overheal > 0.0F && living.getAbsorptionAmount() < maxOverheal) {
                float overHealAmount = Math.min(maxOverheal, living.getAbsorptionAmount() + Math.max(0, event.getAmount() * overheal - (living.getMaxHealth() - living.getHealth())));
                living.getAttributes().getInstance(Attributes.MAX_ABSORPTION).removeModifier(ILivingEntityMixinExtension.OVER_HEAL);
                living.getAttributes().getInstance(Attributes.MAX_ABSORPTION).addTransientModifier(new AttributeModifier(ILivingEntityMixinExtension.OVER_HEAL, overHealAmount, AttributeModifier.Operation.ADD_VALUE));

                living.setAbsorptionAmount(overHealAmount);
            }
        }

        @SubscribeEvent
        public static void PlayerTickEvent$Post(PlayerTickEvent.Post event) {
            PlayerExtraData extraData = event.getEntity().getCapability(SRCCapabilities.SRC_PLAYER_DATA);
            if (extraData != null) {
                extraData.tick();
            }
        }
    }


    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void modifyAttribute(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SRCAttributes.LIFE_STEAL);
            event.add(EntityType.PLAYER, SRCAttributes.ARMOR_PENETRATION);
            event.add(EntityType.PLAYER, SRCAttributes.MINING_FORTUNE);
            event.add(EntityType.PLAYER, SRCAttributes.MOB_LOOTING);
            event.add(EntityType.PLAYER, SRCAttributes.MAX_MANA);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_REGAIN);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_POWER);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_COST);
            event.getTypes().forEach(entityType -> {
                event.add(entityType, SRCAttributes.CRITICAL_CHANCE);
                event.add(entityType, SRCAttributes.CRITICAL_DAMAGE);
                event.add(entityType, SRCAttributes.HEAL_EFFECT);
                event.add(entityType, SRCAttributes.MENDING);
                event.add(entityType, SRCAttributes.OVER_HEAL);
                event.add(entityType, SRCAttributes.MAX_OVER_HEAL_AMOUNT);
            });
        }

        @SubscribeEvent
        public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
            event.registerEntity(SRCCapabilities.SRC_PLAYER_DATA, EntityType.PLAYER, (player, ctx) -> new PlayerExtraData(player));
        }

        @SubscribeEvent
        public static void RegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToClient(PlayerExtraDataSycPack.TYPE, PlayerExtraDataSycPack.STREAM_CODEC, PlayerExtraDataSycPack::clientHandler);
        }
    }
}
