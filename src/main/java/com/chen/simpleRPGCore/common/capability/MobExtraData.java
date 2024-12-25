package com.chen.simpleRPGCore.common.capability;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.API.objects.SRCAttributes;
import com.chen.simpleRPGCore.API.objects.ShieldTypes;
import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.ShieldSystem.UnitShield;
import com.chen.simpleRPGCore.network.NetHandlers;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class MobExtraData {
    private final LivingEntity livingEntity;

    public MobExtraData(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public MobExtraData.DataHolder getDataHolder() {
        return livingEntity.getData(SRCAttachmentTypes.MOB_DATA);
    }

    public void sycShieldAmount() {
        if (livingEntity instanceof ServerPlayer player && !player.level().isClientSide) {
            NetHandlers.sendShieldAmount(player);
        }
    }

    public float getRenderShieldAmount() {
        if (livingEntity.level().isClientSide) {
            return getDataHolder().renderShieldAmount;
        } else {
            float totalShields = 0;
            for (IShield value : getDataHolder().shield.SHIELD_HOLDERS.values()) {
                totalShields += value.getTotalAmount();
            }
            return totalShields;
        }
    }

    public void setRenderShieldAmount(float amount) {
        getDataHolder().renderShieldAmount = amount;
    }

    public <T extends IShield> T getShield(Shield.ShieldType<T> shieldType) {
        return getDataHolder().shield.getShield(shieldType);
    }

    public void tick() {
        if (getRenderShieldAmount() != getDataHolder().oldShieldAmount) {
            getDataHolder().oldShieldAmount = getRenderShieldAmount();
            sycShieldAmount();
        }

        if (!livingEntity.level().isClientSide) {
            getDataHolder().overHealShield.setAmount((float) Math.min(livingEntity.getMaxHealth() * livingEntity.getAttributeValue(SRCAttributes.MAX_OVER_HEAL_PERCENTAGE), getDataHolder().overHealShield.getAmount()));
            getDataHolder().shield.SHIELD_HOLDERS.values().forEach(IShield::tick);
        }
    }

    public static class DataHolder implements INBTSerializable<CompoundTag> {

        public final Shield shield = new Shield();

        public final UnitShield overHealShield;

        public float oldShieldAmount = 0;

        public float renderShieldAmount;

        public DataHolder(IAttachmentHolder holder) {
            shield.init();
            overHealShield = shield.getShield(ShieldTypes.OVER_HEAL_SHIELD);
        }

        @Override
        public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = new CompoundTag();
            shield.SHIELD_HOLDERS.forEach((shieldType, iShield) -> {
                tag.put(shieldType.resourceLocation.toString(), iShield.serializeNBT(provider));
            });
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
            Shield.getShieldsPriority().forEach(shieldType -> {
                shield.getShield(shieldType).deserializeNBT(provider, nbt.getCompound(shieldType.resourceLocation.toString()));
            });
        }
    }
}
