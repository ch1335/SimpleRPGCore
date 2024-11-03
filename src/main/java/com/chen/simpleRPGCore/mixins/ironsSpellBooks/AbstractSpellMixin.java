package com.chen.simpleRPGCore.mixins.ironsSpellBooks;


import com.chen.simpleRPGCore.attribute.SRCAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractSpell.class)
public abstract class AbstractSpellMixin {

    @Shadow
    public abstract int getManaCost(int level);

    @ModifyVariable(method = "canBeCastedBy", at = @At(value = "STORE", ordinal = 0), ordinal = 2)
    private boolean canBeCastedBy(boolean value, @Local(name = "spellLevel") int spellLevel, @Local(name = "playerMagicData") MagicData playerMagicData, @Local(name = "player") Player player) {
        float playerMana = playerMagicData.getMana();
        return playerMana - (float) this.getManaCost(spellLevel) * player.getAttributeValue(SRCAttributes.MANA_COST) >= 0.0F;
    }
}
