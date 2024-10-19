package com.chen.simpleRPGCore.item.items;

import com.chen.simpleRPGCore.common.capability.PlayerExtraData;
import com.chen.simpleRPGCore.common.capability.SRCCapabilities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {

        PlayerExtraData playerExtraData = pPlayer.getCapability(SRCCapabilities.SRC_PLAYER_DATA);
        if (playerExtraData != null) {
            playerExtraData.costMana(10, "test");
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
