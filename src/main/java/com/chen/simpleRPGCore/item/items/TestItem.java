package com.chen.simpleRPGCore.item.items;

import com.chen.simpleRPGCore.client.SimpleRPGCoreClient;
import com.chen.simpleRPGCore.common.capability.MobExtraData;
import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import com.chen.simpleRPGCore.common.specialEffects.TestEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {

        PlayerExtraData playerExtraData = pPlayer.getCapability(SRCCapabilities.SRC_PLAYER_DATA);

        MobExtraData mobExtraData = pPlayer.getCapability(SRCCapabilities.SRC_MOB_DATA);

        TestEffect testEffect = new TestEffect(pPlayer);
        testEffect.duration = 100;
        if (mobExtraData != null) {
            mobExtraData.getSpecialEffectManager().addEffect(testEffect);
        }

        for (Entity entity : pLevel.getEntities(pPlayer, AABB.ofSize(pPlayer.position(), 5, 5, 5))) {
            MobExtraData data = entity.getCapability(SRCCapabilities.SRC_MOB_DATA);
            TestEffect testEffect2 = new TestEffect(pPlayer);
            testEffect2.duration = 100;
            if (data != null) {
                data.getSpecialEffectManager().addEffect(testEffect2);
            }
        }

        if (playerExtraData != null) {
            playerExtraData.costMana((float) (1 + pPlayer.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5), "test");


        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        Player player = SimpleRPGCoreClient.getLocalPlayer();
        if (player == null) {
            return;
        }

        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("simple_rpg_core.itemSkill.test.detail", 1, Component.literal("(" + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5 + ")").withColor(ChatFormatting.RED.getColor())));
        } else {
            tooltipComponents.add(Component.translatable("simple_rpg_core.itemSkill.test.normal", 1 + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5));
        }
    }
}
