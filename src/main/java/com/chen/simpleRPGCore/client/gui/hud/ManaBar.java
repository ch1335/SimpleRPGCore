package com.chen.simpleRPGCore.client.gui.hud;

import com.chen.simpleRPGCore.SimpleRPGConfig;
import com.chen.simpleRPGCore.attachmentType.SRCAttachmentTypes;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ManaBar implements LayeredDraw.Layer {

    private float delayMana = 0F;

    private float fullManaTick = 60;

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        float maxMana = (float) player.getAttributeValue(SRCAttributes.MAX_MANA);
        float currentMana = Math.min(Minecraft.getInstance().player.getData(SRCAttachmentTypes.PLAYER_DATA).mana, maxMana);

        if (currentMana < maxMana) {
            fullManaTick = 0;
        } else {
            fullManaTick += deltaTracker.getGameTimeDeltaPartialTick(true);
        }

        if (fullManaTick >= 60) {
            return;
        }
        PoseStack poseStack = guiGraphics.pose();
        delayMana = Math.clamp(delayMana, currentMana, maxMana);
        float manaPercentage = currentMana / maxMana;
        float delayManaPercentage = delayMana / maxMana;
        poseStack.pushPose();
        int x = guiGraphics.guiWidth() / 2 + SimpleRPGConfig.clientConfig.manaBarX;
        int y = guiGraphics.guiHeight() + SimpleRPGConfig.clientConfig.manaBarY;
        guiGraphics.blit(Huds.ICON, x, y, 10, 10, 107, 13);
        x += 3;
        y += 3;
        guiGraphics.fill(x, y, (int) (x + 101 * delayManaPercentage), y + 7, Color.white.getRGB());
        guiGraphics.blit(Huds.ICON, x, y, 13, 33, (int) (101 * manaPercentage), 7);
        poseStack.popPose();

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, (int) currentMana + "/" + (int) maxMana, x + 50, y - 10, Color.CYAN.getRGB());
        delayMana -= Math.max((delayMana - currentMana) / 30, (delayMana - currentMana) / 10);
    }
}
