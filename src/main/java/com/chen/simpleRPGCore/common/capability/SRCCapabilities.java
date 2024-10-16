package com.chen.simpleRPGCore.common.capability;

import com.chen.simpleRPGCore.SimpleRPGCore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

public class SRCCapabilities {
    public static final EntityCapability<IPlayerExtraData, @Nullable Void> SRC_PLAYER_DATA = EntityCapability.createVoid(create("src_player_data"), IPlayerExtraData.class);

    private static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, path);
    }
}
