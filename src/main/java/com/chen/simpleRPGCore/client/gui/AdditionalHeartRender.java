package com.chen.simpleRPGCore.client.gui;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class AdditionalHeartRender {
    private static final ResourceLocation SHIELD_HEART = ResourceLocation.fromNamespaceAndPath(SimpleRPGCore.MODID, "textures/hud/over_heal_heart.png");

    public static void render(GuiGraphics guiGraphics) {

        Gui gui = Minecraft.getInstance().gui;
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        renderShieldHeart(gui, player, guiGraphics);
    }

    public static void renderShieldHeart(Gui gui, Player player, GuiGraphics guiGraphics) {
        MobExtraData mobExtraData = player.getCapability(SRCCapabilities.SRC_MOB_DATA);
        if (mobExtraData == null) {
            return;
        }

        float f = Math.max((float) player.getAttributeValue(Attributes.MAX_HEALTH), mobExtraData.getRenderShieldAmount());
        int k1 = Mth.ceil(player.getAbsorptionAmount());
        int l1 = Mth.ceil((f + (float) k1) / 2.0F / 10.0F);
        int height = Math.max(10 - (l1 - 2), 3);
        int l2 = Mth.ceil(mobExtraData.getRenderShieldAmount() / 20F);
        int x = guiGraphics.guiWidth() / 2 - 91;
        int y = guiGraphics.guiHeight() - gui.leftHeight;
        gui.leftHeight += (l2 - 1) * height + 10 - (10 - height);
        renderHearts(guiGraphics, x, y + (10 - height), height, mobExtraData.getRenderShieldAmount(), (int) mobExtraData.getRenderShieldAmount());
    }

    private static void renderHearts(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int height,
            float maxHealth,
            int currentHealth
    ) {
        int i = Mth.ceil((double) maxHealth / 2.0);
        for (int l = i - 1; l >= 0; l--) {
            int i1 = l / 10;
            int j1 = l % 10;
            int k1 = x + j1 * 8;
            int l1 = y - i1 * height;
            renderHeartBackGround(guiGraphics, k1, l1);
            int i2 = l * 2;
            if (i2 < currentHealth) {
                boolean flag4 = i2 + 1 == currentHealth;
                renderHeart(guiGraphics, k1, l1, flag4);
            }
        }
    }


    private static void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean halfHeart) {
        RenderSystem.enableBlend();
        if (halfHeart) {
            guiGraphics.blit(SHIELD_HEART, x, y, 9, 9, 0, 9, 9, 9, 9, 18);
        } else {
            guiGraphics.blit(SHIELD_HEART, x, y, 0, 0, 9, 9, 9, 18);
        }
        RenderSystem.disableBlend();
    }

    private static void renderHeartBackGround(GuiGraphics guiGraphics, int x, int y) {
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(Gui.HeartType.CONTAINER.getSprite(false, false, false), x, y, 9, 9);
        RenderSystem.disableBlend();
    }
}
