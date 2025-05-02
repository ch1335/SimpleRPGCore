package com.chen.simpleRPGCore.API;

import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.common.ShieldSystem.GroupShield;
import com.chen.simpleRPGCore.common.ShieldSystem.Shield;
import com.chen.simpleRPGCore.common.ShieldSystem.UnitShield;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

public class SimpleRpgCoreAPI {
    public static <T extends IShield> T getShield(LivingEntity livingEntity, Shield.ShieldType<T> shieldType) {
        return livingEntity.getData(SRCAttachmentTypes.MOB_DATA).getShieldManager().shield.getShield(shieldType);
    }

    public static <T extends GroupShield<U>, U extends UnitShield> void addShield(LivingEntity livingEntity, Shield.ShieldType<T> shieldType, U unitShield) {
        livingEntity.getData(SRCAttachmentTypes.MOB_DATA).getShieldManager().shield.getShield(shieldType).addUnit(unitShield);
    }
}
