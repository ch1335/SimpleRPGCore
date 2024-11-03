package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDataMainMixinExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.data.Main.class)
public class DataMainMixin implements IDataMainMixinExtension {

    @Inject(method = "main",at = @At("HEAD"))
    private static void start(CallbackInfo ci){
        isRunData.set(true);
    }


}
