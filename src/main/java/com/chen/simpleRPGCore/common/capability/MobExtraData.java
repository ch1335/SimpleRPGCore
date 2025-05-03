package com.chen.simpleRPGCore.common.capability;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.common.ShieldSystem.EntityShieldManager;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffectManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
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

    public EntityShieldManager getShieldManager() {
        return getDataHolder().shieldManager;
    }

    public SpecialEffectManager getSpecialEffectManager() {
        return getDataHolder().specialMobEffectManager;
    }

    public <T extends IShield> T getShield(Shield.ShieldType<T> shieldType) {
        return getShieldManager().shield.getShield(shieldType);
    }

    public void tick() {
        getShieldManager().tick(this, livingEntity);
//        getSpecialEffectManager().tick(livingEntity);
    }

    public static class DataHolder implements INBTSerializable<CompoundTag> {
        private final EntityShieldManager shieldManager = new EntityShieldManager();

        private final SpecialEffectManager specialMobEffectManager = new SpecialEffectManager();

        public EntityShieldManager getShieldManager() {
            return shieldManager;
        }

        public DataHolder(IAttachmentHolder holder) {
            shieldManager.init();
        }

        @Override
        public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            CompoundTag tag = new CompoundTag();
            tag.put("ShieldData", shieldManager.save(provider));
            tag.put("SpecialEffectData", specialMobEffectManager.save(provider));
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
            shieldManager.load(provider, nbt.getCompound("ShieldData"));
            specialMobEffectManager.load(provider, nbt.getCompound("SpecialEffectData"));
        }
    }
}
