package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.client.gui.AdditionalHeartRender;
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

@Mixin(value = Gui.class,priority = 500)
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

    @Inject(method = "renderHealthLevel", at = @At("RETURN"))
    private void renderHealthLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            int i = Mth.ceil(player.getHealth());
            if (i > this.lastHealth) {
                this.healthBlinkTime = this.tickCount + 10;
            }
        }
    }
    @Inject(method = "renderHealthLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V"))
    private void BeforeRenderHealthLevel(GuiGraphics guiGraphics, CallbackInfo ci) {
        AdditionalHeartRender.render(guiGraphics);
    }

}
