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
    private OriginalState originalState;

    //set the damage weather bypass Cooldown
    public boolean bypassesCooldown = false;

    //whether this damage can do critical damage
    private boolean canCritical = true;

    //whether this damage can do life steal
    private boolean canDoLifeSteal = true;

    //The value of damage that cannot do critical hits
    private float unCriticalAbleDamage = 0;

    //Critical damage already caused
    public float criticalDamage = 0;

    public int originalInvulnerableTime = 0;
    //this is paper for kubejs. don't use this in mod
    public Optional<Object> customDataHolder = Optional.empty();


    public void setCustomData(Object customDataHolder) {
        this.customDataHolder = Optional.of(customDataHolder);
    }

    public DamageSourceExtraData(Entity entity) {
        this(entity, OriginalState.DEFAULT);
    }

    public DamageSourceExtraData(Entity entity, OriginalState originalState) {
        this.originalState = originalState;
        ImmutableMap.Builder<Attribute, AttributeOriginalData> builder = ImmutableMap.builder();
        if (entity instanceof LivingEntity livingEntity) {
            ExtraAttributes.attributes.forEach(attribute -> {
                if (livingEntity.getAttributes().hasAttribute(attribute)) {
                    builder.put(attribute.value(), new AttributeOriginalData(livingEntity.getAttributeValue(attribute)));
                }
            });
        }
        attributeData = builder.build();
    }

    public DamageSourceExtraData(OriginalState originalState) {
        this.originalState = originalState;
        attributeData = ImmutableMap.of();
    }

    public DamageSourceExtraData() {
        this(OriginalState.DEFAULT);
    }

    public void setOriginalState(OriginalState originalState) {
        this.originalState = originalState;
    }

    public ImmutableMap<Attribute, AttributeOriginalData> getAttributeData() {
        return attributeData;
    }

    public AttributeOriginalData.AttributeOriginalDataHolder getAttributeOriginalHolder(Holder<Attribute> attribute) {
        return AttributeOriginalData.AttributeOriginalDataHolder.of(attributeData.get(attribute.value()));
    }

    // Set damage to melee damage for an entity
    public void addMeleeDamageEntity(Entity entity) {
        meleeDamageEntities.add(entity.getId());
    }

    // Set damage to sweeping damage for an entity
    public void addSweepingDamageEntity(Entity entity) {
        sweepingDamageEntities.add(entity.getId());
    }

    // Set damage to critical damage for an entity
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
        canCritical = originalState.canCritical;
        unCriticalAbleDamage = originalState.unCriticalAbleDamage;
        canDoLifeSteal = originalState.canDoLifeSteal;
        bypassesCooldown = originalState.bypassesCooldown;
        criticalDamage = 0;
    }

    public boolean isCanCritical() {
        return canCritical;
    }

    public DamageSourceExtraData setCanCritical(boolean canCritical) {
        this.canCritical = canCritical;
        return this;
    }

    public boolean isCanDoLifeSteal() {
        return canDoLifeSteal;
    }

    public void setCanDoLifeSteal(boolean canDoLifeSteal) {
        this.canDoLifeSteal = canDoLifeSteal;
    }

    public float getUnCriticalAbleDamage() {
        return unCriticalAbleDamage;
    }

    public static class ExtraAttributes {
        public static Set<Holder<Attribute>> attributes = new HashSet<>();

        public static void addAttributes() {
            attributes.add(SRCAttributes.CRITICAL_CHANCE);
            attributes.add(SRCAttributes.CRITICAL_DAMAGE);
            attributes.add(SRCAttributes.LIFE_STEAL);
            attributes.add(SRCAttributes.ARMOR_PENETRATION);
            SRCEventFactory.addDamageSourceExtraAttributes(attributes);
        }
    }

    // set the damage by pass cooldown
    public void setBypassesCooldown(boolean bypassesCooldown) {
        this.bypassesCooldown = bypassesCooldown;
    }

    // get if the damage by pass cooldown
    public boolean isBypassesCooldown() {
        return bypassesCooldown;
    }

    public DamageSourceExtraData addUnCriticalAbleDamage(float amount) {
        unCriticalAbleDamage += amount;
        return this;
    }

    public static class OriginalState {
        public static final OriginalState DEFAULT = new OriginalState(new Builder());

        public final boolean bypassesCooldown;
        public final boolean canCritical;
        public final boolean canDoLifeSteal;
        public final float unCriticalAbleDamage;

        public OriginalState(Builder builder) {
            bypassesCooldown = builder.bypassesCooldown;
            canCritical = builder.canCritical;
            canDoLifeSteal = builder.canDoLifeSteal;
            unCriticalAbleDamage = builder.unCriticalAbleDamage;
        }

        public static class Builder {
            private boolean bypassesCooldown = false;
            private boolean canCritical = true;
            private boolean canDoLifeSteal = true;
            private float unCriticalAbleDamage = 0;

            public OriginalState build() {
                return new OriginalState(this);
            }

            public boolean isBypassesCooldown() {
                return bypassesCooldown;
            }

            public Builder setBypassesCooldown(boolean bypassesCooldown) {
                this.bypassesCooldown = bypassesCooldown;
                return this;
            }

            public Builder copyFrom(OriginalState state){
                bypassesCooldown = state.bypassesCooldown;
                canCritical = state.canCritical;
                canDoLifeSteal = state.canDoLifeSteal;
                unCriticalAbleDamage = state.unCriticalAbleDamage;
                return this;
            }

            public boolean isCanCritical() {
                return canCritical;
            }

            public Builder setCanCritical(boolean canCritical) {
                this.canCritical = canCritical;
                return this;
            }

            public boolean isCanDoLifeSteal() {
                return canDoLifeSteal;
            }

            public Builder setCanDoLifeSteal(boolean canDoLifeSteal) {
                this.canDoLifeSteal = canDoLifeSteal;
                return this;
            }

            public float getUnCriticalAbleDamage() {
                return unCriticalAbleDamage;
            }

            public Builder setUnCriticalAbleDamage(float unCriticalAbleDamage) {
                this.unCriticalAbleDamage = unCriticalAbleDamage;
                return this;
            }
        }
    }
}
