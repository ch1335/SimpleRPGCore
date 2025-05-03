package com.chen.simpleRPGCore.client.gui.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import org.jetbrains.annotations.NotNull;

public class FoodState implements LayeredDraw.Layer {
    private static final ResourceLocation FOOD_EMPTY_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty");
    private static final ResourceLocation FOOD_EMPTY_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty");
    private static final ResourceLocation FOOD_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full");
    private static final ResourceLocation FOOD_FULL_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full_hunger");

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        Gui gui = Minecraft.getInstance().gui;
        PoseStack poseStack = guiGraphics.pose();
        ResourceLocation resourceLocation;
        if (player == null) {
            return;
        }
        int x = guiGraphics.guiWidth() / 2 + 91 - 81;
        int y = guiGraphics.guiHeight() - Minecraft.getInstance().gui.rightHeight;

        if (player.hasEffect(MobEffects.HUNGER)) {
            resourceLocation = FOOD_FULL_HUNGER_SPRITE;
        } else {
            resourceLocation = FOOD_FULL_SPRITE;
        }
        poseStack.pushPose();
        poseStack.translate(x + 70, y, 0);
        poseStack.scale(1.1F, 1.1F, 0);
        guiGraphics.blitSprite(FOOD_EMPTY_SPRITE, 0, 0, 9, 9);
        guiGraphics.blitSprite(resourceLocation, 0, 0, 9, 9);
        poseStack.popPose();

        Component component = Component.empty().append(Component.literal(String.valueOf(player.getFoodData().getFoodLevel())).withColor(12092504)).append("|").append(Component.literal(String.format("%.0f", player.getFoodData().getSaturationLevel())));
        int width = Minecraft.getInstance().font.width(component);
        guiGraphics.drawString(Minecraft.getInstance().font, component, x + 43 - width +26, y + 1, 10317123, false);
        gui.rightHeight += 9;
    }
}
