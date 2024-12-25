package com.chen.simpleRPGCore.mixins.ironsSpellBooks;

import com.chen.simpleRPGCore.network.NetHandlers;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagicData.class)
public class MagicDataMixin {
    @Shadow
    private ServerPlayer serverPlayer;

    @Shadow
    private float mana;

    @Unique
    private float src$oldMana;

    @Inject(method = "setMana", at = @At("HEAD"))
    private void setManaHead(float mana, CallbackInfo ci) {
        src$oldMana = this.mana;
    }

    @Inject(method = "setMana", at = @At("RETURN"))
    private void setManaReturn(float mana, CallbackInfo ci) {
        if (this.serverPlayer != null && mana != src$oldMana) {
            NetHandlers.sendMana(serverPlayer);
        }
    }
}
