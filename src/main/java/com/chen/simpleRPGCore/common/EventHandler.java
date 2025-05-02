package com.chen.simpleRPGCore.common;

import com.chen.simpleRPGCore.API.objects.DataSetterTypes;
import com.chen.simpleRPGCore.API.objects.SRCAttributes;
import com.chen.simpleRPGCore.API.objects.ShieldTypes;
import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.ShieldSystem.UnitShield;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.network.SimpleDataSetter;
import com.chen.simpleRPGCore.utils.Util;
import dev.shadowsoffire.apothic_attributes.payload.CritParticlePayload;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Objects;

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

            if (Util.canCriticalByTag(container.getSource()) && livingEntity.getRandom().nextFloat() <= criticalChance && SRCEventFactory.modPreCritical(container, livingEntity)) {
                float criticalDamage = container.getNewDamage() * (float) extraData.getAttributeOriginalHolder(SRCAttributes.CRITICAL_DAMAGE).getNew(1);
                extraData.addCriticalDamageEntity(livingEntity.getId());
                container.setNewDamage(criticalDamage);
                extraData.criticalDamage = criticalDamage;
            }

            if (SRCEventFactory.modifyDamageAfterCritical(container, livingEntity)) event.setCanceled(true);

            event.setAmount(event.getAmount() + extraData.getFinalDamageAddition());
        }

        @SubscribeEvent
        public static void updateData(PlayerEvent.PlayerChangedDimensionEvent event) {
            Objects.requireNonNull(event.getEntity().getCapability(SRCCapabilities.SRC_PLAYER_DATA)).sycAll();
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

            if (attacker != null && extraData.isCriticalDamageToEntity(livingEntity.getId())) {
                attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, attacker.getSoundSource(), 1.0F, 1.0F);
                if (attacker instanceof LivingEntity livingEntity1 && livingEntity1.level() instanceof ServerLevel level) {
                    if (SimpleRPGCore.apothicAttributesLoaded) {
                        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) attacker.level(), livingEntity.chunkPosition(), new CritParticlePayload(livingEntity.getId()));
                    } else {
                        level.getChunkSource().broadcastAndSend(livingEntity1, new ClientboundAnimatePacket(livingEntity, 4));
                    }
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void handleShield(LivingDamageEvent.Pre event) {
            Shield.handleShieldAbsorb(event);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void LivingHealEvent(LivingHealEvent event) {
            LivingEntity living = event.getEntity();
            if (living.getAttributes().hasAttribute(SRCAttributes.HEAL_EFFECT)) {
                double healEffect = living.getAttributeValue(SRCAttributes.HEAL_EFFECT);
                event.setAmount((float) (event.getAmount() * healEffect));
            }

            float overHealRate = (float) living.getAttributeValue(SRCAttributes.OVER_HEAL);
            float maxOverHealAmount = (float) (living.getMaxHealth() * living.getAttributeValue(SRCAttributes.MAX_OVER_HEAL_PERCENTAGE));
            float OriginalOverHealAmount = event.getAmount() - (living.getMaxHealth() - living.getHealth());

            if (overHealRate > 0 && OriginalOverHealAmount > 0) {
                float overHealAmount = OriginalOverHealAmount * overHealRate;
                MobExtraData mobExtraData = living.getCapability(SRCCapabilities.SRC_MOB_DATA);
                if (mobExtraData != null) {
                    UnitShield shield = mobExtraData.getShield(ShieldTypes.OVER_HEAL_SHIELD.value());
                    if (shield != null) {
                        shield.addShieldAmount(overHealAmount, maxOverHealAmount);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void EntityTickEvent$Post(EntityTickEvent.Post event) {
            if (!event.getEntity().level().isClientSide) {
                MobExtraData mobExtraData = event.getEntity().getCapability(SRCCapabilities.SRC_MOB_DATA);
                if (mobExtraData != null) {
                    mobExtraData.tick();
                }
            }
        }

        @SubscribeEvent
        public static void PlayerTickEvent$Post(PlayerTickEvent.Post event) {
            if (!event.getEntity().level().isClientSide) {
                PlayerExtraData playerExtraData = event.getEntity().getCapability(SRCCapabilities.SRC_PLAYER_DATA);
                if (playerExtraData != null) {
                    playerExtraData.tick();
                }
            }
        }

        @SubscribeEvent
        public static void onPLayerLoginIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                Objects.requireNonNull(player.getCapability(SRCCapabilities.SRC_PLAYER_DATA)).sycAll();
                Objects.requireNonNull(player.getCapability(SRCCapabilities.SRC_MOB_DATA)).getShieldManager().sycShieldAmount(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                PlayerExtraData playerExtraData = player.getCapability(SRCCapabilities.SRC_PLAYER_DATA);
                if (playerExtraData != null) {
                    playerExtraData.sycAll();
                }
                MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
                if (mobExtraData != null) {
                    mobExtraData.getShieldManager().sycShieldAmount(player);
                }
            }
        }

        public static class IronsSpellBooksEventHandler {
            @SubscribeEvent(priority = EventPriority.LOWEST)
            public static void SpellOnCastEvent(SpellOnCastEvent event) {
                event.setManaCost((int) SRCEventFactory.onPlayerCostMana(event.getEntity(), (float) (event.getManaCost() * event.getEntity().getAttributeValue(SRCAttributes.MANA_COST)), event));
            }
        }
    }


    @EventBusSubscriber(modid = SimpleRPGCore.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void modifyAttribute(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SRCAttributes.MINING_FORTUNE);
            event.add(EntityType.PLAYER, SRCAttributes.MOB_LOOTING);
            event.add(EntityType.PLAYER, SRCAttributes.MAX_MANA);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_REGAIN);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_POWER);
            event.add(EntityType.PLAYER, SRCAttributes.MANA_COST);
            event.getTypes().forEach(entityType -> {
                event.add(entityType, SRCAttributes.LIFE_STEAL);
                event.add(entityType, SRCAttributes.ARMOR_PENETRATION);
                event.add(entityType, SRCAttributes.CRITICAL_CHANCE);
                event.add(entityType, SRCAttributes.CRITICAL_DAMAGE);
                event.add(entityType, SRCAttributes.HEAL_EFFECT);
                event.add(entityType, SRCAttributes.MENDING);
                event.add(entityType, SRCAttributes.OVER_HEAL);
                event.add(entityType, SRCAttributes.MAX_OVER_HEAL_PERCENTAGE);
            });
        }

        @SubscribeEvent
        public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {

            event.registerEntity(SRCCapabilities.SRC_PLAYER_DATA, EntityType.PLAYER, (player, ctx) -> new PlayerExtraData(player));
            BuiltInRegistries.ENTITY_TYPE.stream()
                    .filter(DefaultAttributes::hasSupplier)
                    .forEach(entityType -> {
                        event.registerEntity(SRCCapabilities.SRC_MOB_DATA, entityType, (entity, ctx) -> {
                            if (entity instanceof LivingEntity livingEntity) {
                                return new MobExtraData(livingEntity);
                            }
                            return null;
                        });
                    });
        }

        @SubscribeEvent
        public static void RegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
            DataSetterTypes.init();
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playBidirectional(SimpleDataSetter.TYPE, SimpleDataSetter.STREAM_CODEC, SimpleDataSetter::handler);
        }
    }
}
