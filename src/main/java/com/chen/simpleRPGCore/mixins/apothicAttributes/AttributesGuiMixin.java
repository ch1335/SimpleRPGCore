package com.chen.simpleRPGCore.mixins.apothicAttributes;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(AttributesGui.class)
public abstract class AttributesGuiMixin {
    @Shadow @Nullable public abstract AttributeInstance getHoveredSlot(int mouseX, int mouseY);

    @Inject(method = "renderTooltip",at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z",ordinal = 1,shift = At.Shift.AFTER))
    private void renderTooltip(GuiGraphics gfx, int mouseX, int mouseY, CallbackInfo ci, @Local LocalRef<List<Component>> listLocalRef){
        if (this.getHoveredSlot(mouseX, mouseY).getAttribute().value() == ALObjects.Attributes.OVERHEAL.value()) {
            List<Component> list = listLocalRef.get();
            list.add(CommonComponents.EMPTY);
            list.add(Component.translatable("simple_rpg_core.attribute.fix").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
            list.add(Component.translatable("simple_rpg_core.attribute.fix.over_heal").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
            listLocalRef.set(list);
        }
    }
}
