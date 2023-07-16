package net.nimajnebec.smputilities.enchantment;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.nimajnebec.smputilities.reflection.ObfuscationMapper;

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
        ObfuscationMapper.HOLDER_REFERENCE.bindKey(reference, this.registryKey);

        MappedRegistry<Enchantment> registry = (MappedRegistry<Enchantment>) BuiltInRegistries.ENCHANTMENT;
        ObfuscationMapper.MAPPED_REGISTRY.getById(registry).set(registryId, reference);
        ObfuscationMapper.MAPPED_REGISTRY.getToId(registry).put(enchantment, registryId);
        ObfuscationMapper.MAPPED_REGISTRY.geByLocation(registry).put(registryKey.location(), reference);
        ObfuscationMapper.MAPPED_REGISTRY.getByKey(registry).put(registryKey, reference);
        ObfuscationMapper.MAPPED_REGISTRY.getByValue(registry).put(enchantment, reference);
        ObfuscationMapper.MAPPED_REGISTRY.getTags(registry);
    }

}
