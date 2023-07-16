package net.nimajnebec.smputilities.enchantment;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class AxeEnchantmentImpostor extends EnchantmentImpostor {

    public AxeEnchantmentImpostor(Enchantment handle) {
        super(handle);
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack stack) {
        return super.canEnchant(stack) || stack.getItem() instanceof AxeItem;
    }

}
