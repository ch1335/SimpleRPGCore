package com.chen.simpleRPGCore.client.gui.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ArmorState implements LayeredDraw.Layer {
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        Gui gui = Minecraft.getInstance().gui;
        PoseStack poseStack = guiGraphics.pose();
        if (player == null) {
            return;
        }

        float armorValue = (float) player.getAttributeValue(Attributes.ARMOR);
        int x = guiGraphics.guiWidth() / 2 - 91;
        int y = guiGraphics.guiHeight() - gui.leftHeight;

        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(1.1F,1.1F,0);
        guiGraphics.blitSprite(Huds.ARMOR_FULL_SPRITE, 0, 0, 9, 9);
        poseStack.popPose();

        guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf(armorValue), x + 12, y+1 , Color.LIGHT_GRAY.getRGB(), false);
        gui.leftHeight += 9;
    }
}
