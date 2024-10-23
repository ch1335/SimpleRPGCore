package com.chen.simpleRPGCore.mixins.minecraft;

import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.chen.simpleRPGCore.common.DamageSourceExtraData;
import com.chen.simpleRPGCore.event.SRCEventFactory;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.IDamageSourceExtension;
import com.chen.simpleRPGCore.mixinsAPI.minecraft.ILivingEntityMixinExtension;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Stack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntityMixinExtension {

    @Shadow
    @Nullable
    protected Stack<DamageContainer> damageContainers;

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract void heal(float pHealAmount);

    @Shadow
    public abstract boolean hurt(@NotNull DamageSource pSource, float pAmount);

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private void actuallyHurt(DamageSource damageSource, float pDamageAmount, CallbackInfo ci) {
        float actuallyHealthLost = Math.min(this.getHealth(), this.damageContainers.peek().getNewDamage());

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        DamageSourceExtraData extraData = ((IDamageSourceExtension) damageSource).src$getExtraData();

        float life_steal = (float) extraData.getAttributeOriginalHolder(SRCAttributes.LIFE_STEAL).getNew(0);

        if (damageSource.getEntity() instanceof LivingEntity living && extraData.isMeleeDamageToEntity(livingEntity)) {
            float healAmount = actuallyHealthLost * life_steal;
            living.heal(healAmount);
        }



    }

    @Unique
    public void src$healBy(Entity healer, float amount) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (healer instanceof LivingEntity living) {
            float mending = healer == self ? 1 : (float) living.getAttributeValue(SRCAttributes.MENDING);
            this.heal(SRCEventFactory.onLivingBeHeal(self, healer, amount * mending));
        }
    }

    @ModifyArgs(method = "collectEquipmentChanges",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",ordinal = 0))
    private void disableVanilla(Args args){
        args.set(1,EMPTY_CONSUMER);
    }

    @Inject(method = "collectEquipmentChanges",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",ordinal = 0))
    private void forEachModifier1(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir){

    }
}
