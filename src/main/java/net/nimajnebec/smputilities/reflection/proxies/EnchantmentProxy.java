package net.nimajnebec.smputilities.reflection.proxies;

import net.minecraft.world.item.enchantment.Enchantment;
import xyz.jpenilla.reflectionremapper.proxy.annotation.Proxies;

@Proxies(Enchantment.class)
public interface EnchantmentProxy {
    boolean checkCompatibility(Enchantment instance, Enchantment other);

    String getOrCreateDescriptionId(Enchantment instance);
}
