package com.chen.simpleRPGCore.client.gui;

import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.chen.simpleRPGCore.common.specialEffect.SpecialEffectManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

public class SpecialEffectState implements LayeredDraw.Layer {

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData == null) {
            return;
        }
        SpecialEffectManager manager = mobExtraData.getSpecialEffectManager();

    }
}
