package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.attribute.SRCAttribute;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.ILivingEntityMixinExtension;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "addModifierTooltip", at = @At("HEAD"), cancellable = true)
    private void addModifierTooltip(Consumer<Component> pTooltipAdder, Player pPlayer, Holder<Attribute> pAttribute, AttributeModifier pModifier, CallbackInfo ci) {
        if (pAttribute.value() instanceof SRCAttribute attribute && attribute.isPercentage) {
            double d0 = pModifier.amount();

            double d1 = d0 * 100;

            if (d0 > 0.0) {
                pTooltipAdder.accept(
                        Component.translatable(
                                        "attribute.modifier.plus." + pModifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1) + "%",
                                        Component.translatable(pAttribute.value().getDescriptionId())
                                )
                                .withStyle(pAttribute.value().getStyle(true))
                );
            } else if (d0 < 0.0) {
                pTooltipAdder.accept(
                        Component.translatable(
                                        "attribute.modifier.take." + pModifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-d1) + "%",
                                        Component.translatable(pAttribute.value().getDescriptionId())
                                )
                                .withStyle(pAttribute.value().getStyle(false))
                );
            }

            ci.cancel();
        }
    }

    @Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",at = @At("HEAD"), cancellable = true)
    private void forEachModifier(EquipmentSlot pEquipmentSLot, BiConsumer<Holder<Attribute>, AttributeModifier> pAction, CallbackInfo ci){
        if (pAction == ILivingEntityMixinExtension.EMPTY_CONSUMER) {
            ci.cancel();
        }
    }
}
