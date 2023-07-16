package net.nimajnebec.smputilities.enchantment;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.nimajnebec.smputilities.reflection.ObfuscationMapping;

public class ImpostorInjector {
    private final EnchantmentImpostor impostor;
    private ResourceKey<Enchantment> registryKey;
    private int registryId;

    public ImpostorInjector(EnchantmentImpostor impostor) {
        this.impostor = impostor;
    }

    public EnchantmentImpostor getImpostor() {
        return this.impostor;
    }

    public void inject() throws IllegalAccessException {
        this.registryKey = BuiltInRegistries.ENCHANTMENT.getResourceKey(impostor.getTarget()).get();
        this.registryId = BuiltInRegistries.ENCHANTMENT.getId(impostor.getTarget());
        this.replaceRegistry(impostor);
    }

    public void restore() throws IllegalAccessException {
        this.replaceRegistry(this.impostor.getTarget());
    }

    private void replaceRegistry(Enchantment enchantment) throws IllegalAccessException {
        Holder.Reference<Enchantment> reference = Holder.Reference.createIntrusive(BuiltInRegistries.ENCHANTMENT.asLookup(), enchantment);
        ObfuscationMapping.PROXY_HOLDER.bindKey(reference, this.registryKey);

        MappedRegistry<Enchantment> registry = (MappedRegistry<Enchantment>) BuiltInRegistries.ENCHANTMENT;
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.getById(registry).set(registryId, reference);
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.getToId(registry).put(enchantment, registryId);
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.geByLocation(registry).put(registryKey.location(), reference);
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.getByKey(registry).put(registryKey, reference);
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.getByValue(registry).put(enchantment, reference);
        ObfuscationMapping.PROXY_MAPPED_REGISTRY.getTags(registry);
    }

}
