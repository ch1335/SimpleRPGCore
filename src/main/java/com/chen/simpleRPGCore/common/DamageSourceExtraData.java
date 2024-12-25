package com.chen.simpleRPGCore.common;

import com.chen.simpleRPGCore.API.objects.SRCAttributes;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DamageSourceExtraData {
    private final Set<TagKey<DamageType>> originalAdditionalTags = new HashSet<>();
    private final Set<TagKey<DamageType>> additionalTags = new HashSet<>();

    private final IntArraySet meleeDamageEntities = new IntArraySet();

    private final IntArraySet sweepingDamageEntities = new IntArraySet();

    private final IntArraySet criticalDamageEntities = new IntArraySet();

    private final ImmutableMap<Attribute, OriginalDataHolder<Double>> attributeData;

    public ImmutableList<Shield.ShieldType> byPassesShields = ImmutableList.of();

    public void setByPassesShields(List<Shield.ShieldType> byPassesShields) {
        this.byPassesShields = ImmutableList.copyOf(byPassesShields);
    }

    //Critical damage already caused
    public float criticalDamage = 0;

    //this is paper for kubejs. don't use this in mod
    public Optional<Object> customDataHolder = Optional.empty();

    private float finalDamageAddition = 0;

    public void setCustomData(Object customDataHolder) {
        this.customDataHolder = Optional.of(customDataHolder);
    }

    public DamageSourceExtraData(Entity entity) {
        ImmutableMap.Builder<Attribute, OriginalDataHolder<Double>> builder = ImmutableMap.builder();
        if (entity instanceof LivingEntity livingEntity) {
            ExtraAttributes.attributes.forEach(attribute -> {
                if (livingEntity.getAttributes().hasAttribute(attribute)) {
                    builder.put(attribute.value(), new OriginalDataHolder<>(livingEntity.getAttributeValue(attribute)));
                }
            });
        }
        attributeData = builder.build();
    }

    public DamageSourceExtraData() {
        attributeData = ImmutableMap.of();
    }

    public Set<TagKey<DamageType>> getAdditionalTags() {
        return additionalTags;
    }

    public Set<TagKey<DamageType>> getOriginalAdditionalTags() {
        return originalAdditionalTags;
    }

    public void addAdditionTags(Holder<DamageType> tagKey) {
        additionalTags.addAll(tagKey.tags().toList());
    }

    public void addOriginalAdditionTags(Holder<DamageType> tagKey) {
        originalAdditionalTags.addAll(tagKey.tags().toList());
    }

    public void addAdditionTags(TagKey<DamageType> tagKey) {
        additionalTags.add(tagKey);
    }

    public void addOriginalAdditionTags(TagKey<DamageType> tagKey) {
        originalAdditionalTags.add(tagKey);
    }

    public ImmutableMap<Attribute, OriginalDataHolder<Double>> getAttributeData() {
        return attributeData;
    }

    public OriginalDataHolder.AttributeOriginalDataHolder getAttributeOriginalHolder(Holder<Attribute> attribute) {
        return OriginalDataHolder.AttributeOriginalDataHolder.of(attributeData.get(attribute.value()));
    }

    // Set damage to melee damage for an entity
    public void addMeleeDamageEntity(int id) {
        meleeDamageEntities.add(id);
    }

    // Set damage to sweeping damage for an entity
    public void addSweepingDamageEntity(int id) {
        sweepingDamageEntities.add(id);
    }

    // Set damage to critical damage for an entity
    public void addCriticalDamageEntity(int id) {
        criticalDamageEntities.add(id);
    }

    public boolean isMeleeDamageToEntity(int id) {
        return meleeDamageEntities.contains(id);
    }


    public boolean isSweepingDamageToEntity(int id) {
        return sweepingDamageEntities.contains(id);
    }

    public boolean isCriticalDamageToEntity(int id) {
        return criticalDamageEntities.contains(id);
    }

    public void restToOriginal() {
        attributeData.values().forEach(OriginalDataHolder::restToOriginal);
        criticalDamage = 0;
        finalDamageAddition = 0;
        additionalTags.clear();
        additionalTags.addAll(originalAdditionalTags);
    }

    public float getFinalDamageAddition() {
        return finalDamageAddition;
    }

    public void setFinalDamageAddition(float finalDamageAddition) {
        this.finalDamageAddition = finalDamageAddition;
    }

    public void addFinalDamageAddition(float finalDamageAddition) {
        this.finalDamageAddition += finalDamageAddition;
    }

    public static class ExtraAttributes {
        public static Set<Holder<Attribute>> attributes = new HashSet<>();

        public static void addAttributes() {
            ImmutableSet.Builder<Holder<Attribute>> builder = ImmutableSet.builder();
            attributes.add(SRCAttributes.CRITICAL_CHANCE);
            attributes.add(SRCAttributes.CRITICAL_DAMAGE);
            attributes.add(SRCAttributes.LIFE_STEAL);
            attributes.add(SRCAttributes.ARMOR_PENETRATION);
            SRCEventFactory.addDamageSourceExtraAttributes(attributes);
            attributes.forEach(builder::add);
            attributes = builder.build();
        }
    }
}
