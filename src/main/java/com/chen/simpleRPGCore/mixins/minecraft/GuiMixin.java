package com.chen.simpleRPGCore.mixins.minecraft;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private int lastHealth;
    @Shadow
    private long healthBlinkTime;
    @Shadow
    private int tickCount;

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Inject(method = "renderHealthLevel", at = @At("HEAD"))
    private void renderHealthLevel(GuiGraphics p_283143_, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            int i = Mth.ceil(player.getHealth());
            if (i > this.lastHealth) {
                this.healthBlinkTime = this.tickCount + 10;
            }
        }
    }
}
