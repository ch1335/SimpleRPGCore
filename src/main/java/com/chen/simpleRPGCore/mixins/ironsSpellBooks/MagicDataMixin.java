package com.chen.simpleRPGCore.mixins.ironsSpellBooks;

import com.chen.simpleRPGCore.network.PlayerExtraDataSycPack;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagicData.class)
public class MagicDataMixin {
    @Shadow private ServerPlayer serverPlayer;

    @Inject(method = "setMana",at = @At("RETURN"))
    private void setMana(float mana, CallbackInfo ci){
        if (this.serverPlayer != null) {
            PlayerExtraDataSycPack.SycMana(this.serverPlayer);
        }
    }
}
