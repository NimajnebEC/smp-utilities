package net.nimajnebec.smputilities.reflection;

import net.nimajnebec.smputilities.reflection.proxies.EnchantmentProxy;
import net.nimajnebec.smputilities.reflection.proxies.HolderReferenceProxy;
import net.nimajnebec.smputilities.reflection.proxies.MappedRegistryProxy;
import xyz.jpenilla.reflectionremapper.ReflectionRemapper;
import xyz.jpenilla.reflectionremapper.proxy.ReflectionProxyFactory;

public final class ObfuscationMapper {

    public static final EnchantmentProxy ENCHANTMENT;
    public static final MappedRegistryProxy MAPPED_REGISTRY;
    public static final HolderReferenceProxy HOLDER_REFERENCE;

    static {
        // Setup Reflection Remapper
        ReflectionRemapper remapper = ReflectionRemapper.forReobfMappingsInPaperJar();

        // Initialise Proxies
        final ReflectionProxyFactory reflectionProxyFactory = ReflectionProxyFactory.create(remapper, ObfuscationMapper.class.getClassLoader());
        HOLDER_REFERENCE = reflectionProxyFactory.reflectionProxy(HolderReferenceProxy.class);
        MAPPED_REGISTRY = reflectionProxyFactory.reflectionProxy(MappedRegistryProxy.class);
        ENCHANTMENT = reflectionProxyFactory.reflectionProxy(EnchantmentProxy.class);
    }
}
