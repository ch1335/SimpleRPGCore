package com.chen.simpleRPGCore.common.capability;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

public class SRCCapabilities {
    public static final EntityCapability<PlayerExtraData, @Nullable Void> SRC_PLAYER_DATA = EntityCapability.createVoid(create("src_player_data"), PlayerExtraData.class);

    public static final EntityCapability<MobExtraData, @Nullable Void> SRC_MOB_DATA = EntityCapability.createVoid(create("src_mob_data"), MobExtraData.class);

    private static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, path);
    }
}
