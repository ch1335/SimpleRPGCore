package com.chen.simpleRPGCore.common.ShieldSystem;

import com.chen.simpleRPGCore.API.IShield;
import com.chen.simpleRPGCore.API.objects.SRCRegistries;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.network.NetHandlers;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityShieldManager {
    public final Shield shield = new Shield();

    public float oldShieldAmount = 0;

    public float renderShieldAmount;

    public void init() {

    }

    public EntityShieldManager() {
        shield.init();
    }

    public CompoundTag save(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        shield.SHIELD_HOLDERS.forEach((shieldType, iShield) -> {
            tag.put(Objects.requireNonNull(SRCRegistries.SHIELD_TYPE.getKey(shieldType)).toString(), iShield.serializeNBT(provider));
        });
        return tag;
    }

    public void load(HolderLookup.Provider provider, @NotNull CompoundTag nbt) {
        Shield.getShieldsPriority().forEach(shieldType -> {
            shield.getShield(shieldType).deserializeNBT(provider, nbt.getCompound(Objects.requireNonNull(SRCRegistries.SHIELD_TYPE.getKey(shieldType)).toString()));
        });
    }

    public float getShieldAmount() {
        float totalShields = 0;
        for (IShield value : shield.SHIELD_HOLDERS.values()) {
            totalShields += value.getTotalAmount();
        }
        return totalShields;
    }

    public void tick(MobExtraData mobExtraData, LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            float shieldAmount = getShieldAmount();
            if (shieldAmount != oldShieldAmount) {
                oldShieldAmount = shieldAmount;
                sycShieldAmount(livingEntity, shieldAmount);
            }
            shield.SHIELD_HOLDERS.values().forEach(iShield -> iShield.tick(livingEntity));
        }
    }

    public void sycShieldAmount(LivingEntity livingEntity, float amount) {
        if (livingEntity instanceof ServerPlayer player && !player.level().isClientSide) {
            NetHandlers.sendShieldAmount(player, amount);
        }
    }

    public void sycShieldAmount(LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player && !player.level().isClientSide) {
            NetHandlers.sendShieldAmount(player, -1);
        }
    }
}
