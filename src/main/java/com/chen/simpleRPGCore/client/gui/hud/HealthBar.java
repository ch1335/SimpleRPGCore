package com.chen.simpleRPGCore.client.gui.hud;

import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.awt.*;

public class HealthBar implements LayeredDraw.Layer {
    private float delayHealth = 0F;

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Gui gui = Minecraft.getInstance().gui;
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData == null) {
            return;
        }

        guiGraphics.pose().pushPose();
        float renderShieldAmount = mobExtraData.getShieldManager().renderShieldAmount;
        float currentHealth = player.getHealth();
        float maxHealth = player.getMaxHealth();
        float absorptionAmount = player.getAbsorptionAmount();

        float totalAmount = currentHealth + absorptionAmount + renderShieldAmount;
        float maxAmount = Math.max(totalAmount, maxHealth);

        delayHealth = Math.clamp(delayHealth, totalAmount, maxAmount);
        float delayHealthPercentage = delayHealth / maxAmount;


        int x = guiGraphics.guiWidth() / 2 - 91;
        guiGraphics.blit(Huds.ICON, x, guiGraphics.guiHeight() - gui.leftHeight + 2, 0, 20, 81, 5);

        int fillY = guiGraphics.guiHeight() - gui.leftHeight + 3;

        float currentLength = x + 1;

        int healthNumberColor = Color.RED.getRGB();
        float healthLength = 79 * (currentHealth / maxAmount);
        this.fillBar(guiGraphics, currentLength, fillY, currentLength + 79 * delayHealthPercentage, fillY + 3, Color.WHITE.getRGB());
        this.fillBar(guiGraphics, currentLength, fillY, currentLength + healthLength, fillY + 3, Color.RED.getRGB());
        currentLength += healthLength;

        if (absorptionAmount > 0) {
            float absorptionLength = 79 * (absorptionAmount / maxAmount);
            this.fillBar(guiGraphics, currentLength, fillY, currentLength + absorptionLength, fillY + 3, Color.YELLOW.getRGB());
            currentLength += absorptionLength;
            healthNumberColor = Color.YELLOW.getRGB();
        }

        if (renderShieldAmount > 0) {
            float shieldLength = 79 * (renderShieldAmount / maxAmount);
            this.fillBar(guiGraphics, currentLength, fillY, currentLength + shieldLength, fillY + 3, Color.LIGHT_GRAY.getRGB());
            healthNumberColor = Color.LIGHT_GRAY.getRGB();
        }

        String current = String.format("%.1f/%.1f", currentHealth + absorptionAmount + renderShieldAmount, maxHealth);
        guiGraphics.drawString(Minecraft.getInstance().font, current, x, guiGraphics.guiHeight() - gui.leftHeight + 3 - 10, healthNumberColor, false);


        gui.leftHeight += 20;
        guiGraphics.pose().popPose();
        delayHealth -= Math.max((delayHealth - totalAmount) / 30, (delayHealth - totalAmount) / 10);
    }

    private void fillBar(GuiGraphics guiGraphics, float minX, float minY, float maxX, float maxY, int color) {
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        if (minX < maxX) {
            float i = minX;
            minX = maxX;
            maxX = i;
        }

        if (minY < maxY) {
            float j = minY;
            minY = maxY;
            maxY = j;
        }

        VertexConsumer vertexconsumer = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        vertexconsumer.addVertex(matrix4f, minX, minY, (float) 0).setColor(color);
        vertexconsumer.addVertex(matrix4f, minX, maxY, (float) 0).setColor(color);
        vertexconsumer.addVertex(matrix4f, maxX, maxY, (float) 0).setColor(color);
        vertexconsumer.addVertex(matrix4f, maxX, minY, (float) 0).setColor(color);
    }
}
