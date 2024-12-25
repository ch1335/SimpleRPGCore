package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.attribute.AttributeTransformer;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin implements Registry<Attribute> {

    @Shadow
    @Final
    private Map<ResourceLocation, Holder.Reference<Attribute>> byLocation;

    @Inject(method = "getHolder(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void getHolder(ResourceLocation location, CallbackInfoReturnable<Optional<Holder.Reference<Attribute>>> cir) {
        AttributeTransformer.transform(location, cir, (MappedRegistry<Attribute>) (Object) this, this.byLocation);
    }

}
