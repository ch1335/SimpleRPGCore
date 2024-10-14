package com.chen.simpleRPGCore.common;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DamageSourceExtraData {

    private final IntArraySet meleeDamageEntities = new IntArraySet();

    private final IntArraySet sweepingDamageEntities = new IntArraySet();

    private final IntArraySet criticalDamageEntities = new IntArraySet();

    private final ImmutableMap<Attribute, AttributeOriginalData> attributeData;

    public boolean bypassesCooldown = false;

    public int originalInvulnerabilityTicksAfterAttack = 20;
    //this is paper for kubejs. don't use this in mod
    public Optional<Object> customDataHolder = Optional.empty();

    public void setCustomData(Object customDataHolder) {
        this.customDataHolder = Optional.of(customDataHolder);
    }

    public DamageSourceExtraData(Entity entity) {
        ImmutableMap.Builder<Attribute, AttributeOriginalData> builder = ImmutableMap.builder();
        if (entity instanceof LivingEntity livingEntity) {
            ExtraAttributes.attributes.forEach(attribute -> {
                if (livingEntity.getAttributes().hasAttribute(attribute)) {
                    builder.put(attribute.value(),new AttributeOriginalData(livingEntity.getAttributeValue(attribute)));
                }
            });
        }
        attributeData = builder.build();
    }

    public DamageSourceExtraData() {
        attributeData = ImmutableMap.of();
    }

    public AttributeOriginalData.AttributeOriginalDataHolder getAttributeOriginalHolder(Holder<Attribute> attribute){
       return AttributeOriginalData.AttributeOriginalDataHolder.of(attributeData.get(attribute.value()));
    }
    public void addMeleeDamageEntity(Entity entity) {
        meleeDamageEntities.add(entity.getId());
    }


    public void addSweepingDamageEntity(Entity entity) {
        sweepingDamageEntities.add(entity.getId());
    }


    public void addCriticalDamageEntity(Entity entity) {
        criticalDamageEntities.add(entity.getId());
    }


    public boolean isMeleeDamageToEntity(Entity entity) {
        return meleeDamageEntities.contains(entity.getId());
    }


    public boolean isSweepingDamageToEntity(Entity entity) {
        return sweepingDamageEntities.contains(entity.getId());
    }


    public boolean isCriticalDamageToEntity(Entity entity) {
        return criticalDamageEntities.contains(entity.getId());
    }

    public void restToOriginal() {
        attributeData.values().forEach(AttributeOriginalData::restToOriginal);
    }

    public static class ExtraAttributes{
        public static Set<Holder<Attribute>> attributes = new HashSet<>();

        public static void addAttributes(){
            attributes.add(SRCAttributes.CRITICAL_CHANCE);
            attributes.add(SRCAttributes.CRITICAL_DAMAGE);
            attributes.add(SRCAttributes.LIFE_STEAL);
            attributes.add(SRCAttributes.ARMOR_PENETRATION);
            SRCEventFactory.addDamageSourceExtraAttributes(attributes);
        }
    }

    public void setBypassesCooldown(boolean bypassesCooldown) {
        this.bypassesCooldown = bypassesCooldown;
    }

    public boolean isBypassesCooldown() {
        return bypassesCooldown;
    }
}
