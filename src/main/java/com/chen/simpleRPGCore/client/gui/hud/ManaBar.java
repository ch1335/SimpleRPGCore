package com.chen.simpleRPGCore.client.gui.hud;

import com.chen.simpleRPGCore.API.objects.SRCAttributes;
import com.chen.simpleRPGCore.SimpleRPGConfig;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ManaBar implements LayeredDraw.Layer {

    private float delayMana = 0F;


    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        if (!SimpleRPGConfig.commonConfig.enableManaSystem) {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        Gui gui = Minecraft.getInstance().gui;;

        float maxMana = (float) player.getAttributeValue(SRCAttributes.MAX_MANA);
        float currentMana = Math.min(Minecraft.getInstance().player.getCapability(SRCCapabilities.SRC_PLAYER_DATA).getMana(), maxMana);

        PoseStack poseStack = guiGraphics.pose();
        delayMana = Math.clamp(delayMana, currentMana, maxMana);
        float manaPercentage = currentMana / maxMana;
        float delayManaPercentage = delayMana / maxMana;
        poseStack.pushPose();
        int x = guiGraphics.guiWidth() / 2 + 91 - 81;
        int y = guiGraphics.guiHeight() - Minecraft.getInstance().gui.rightHeight;
        guiGraphics.blit(Huds.ICON, x, y + 2, 0, 25, 81, 13);

        int fillX = x + 1;
        int fillY = y + 3;
        guiGraphics.fill((int) (fillX + 79 * (1 - delayManaPercentage)), fillY, fillX + 79, fillY + 3, Color.WHITE.getRGB());
        guiGraphics.fill((int) (fillX + 79 * (1 - manaPercentage)), fillY, fillX + 79, fillY + 3, Color.CYAN.getRGB());
        poseStack.popPose();

        String string = (int) currentMana + "/" + (int) maxMana;
        int width = Minecraft.getInstance().font.width(string);
        guiGraphics.drawString(Minecraft.getInstance().font, string, x - width + 81, y - 10 + 3, Color.CYAN.getRGB(), false);
        delayMana -= Math.max((delayMana - currentMana) / 30, (delayMana - currentMana) / 10);
        gui.rightHeight += 20;
    }
}
