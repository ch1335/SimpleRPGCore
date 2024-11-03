package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.SimpleRPGCore;
import com.chen.simpleRPGCore.attribute.AttributeTransformer;
import com.chen.simpleRPGCore.attribute.SRCAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.registries.DeferredHolder;
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
        AttributeTransformer.transform(location,cir,(MappedRegistry<Attribute>) (Object)this,this.byLocation);

//        if (key() == Registries.ATTRIBUTE) {
//            if (SimpleRPGCore.apothicAttributesLoaded) {
//                if (location.equals(SRCAttributes.AttributeResourceLocationHolder.CRITICAL_CHANCE.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.CRIT_CHANCE).getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.CRITICAL_DAMAGE.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.CRIT_DAMAGE).getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.LIFE_STEAL.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.LIFE_STEAL).getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.ARMOR_PENETRATION.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.ARMOR_PIERCE).getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.HEAL_EFFECT.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.HEALING_RECEIVED).getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.OVER_HEAL.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(((DeferredHolder<Attribute, Attribute>) dev.shadowsoffire.apothic_attributes.api.ALObjects.Attributes.OVERHEAL).getId()))));
//                }
//            }
//
//            if (SimpleRPGCore.ironsSSpellBooksLoaded) {
//                if (location.equals(SRCAttributes.AttributeResourceLocationHolder.MAX_MANA.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA.getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.MANA_REGAIN.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN.getId()))));
//                } else if (location.equals(SRCAttributes.AttributeResourceLocationHolder.MANA_POWER.getResourceLocation())) {
//                    cir.setReturnValue(Optional.ofNullable(this.byLocation.get(resolve(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER.getId()))));
//                }
//            }
//        }
    }

}
