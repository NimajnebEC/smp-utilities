package net.nimajnebec.smputilities.reflection;

import net.minecraft.world.item.enchantment.Enchantment;
import xyz.jpenilla.reflectionremapper.ReflectionRemapper;
import xyz.jpenilla.reflectionremapper.proxy.ReflectionProxyFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObfuscationMapping<T> {

    public static final ObfuscationMapping<Method> METHOD_ENCHANTMENT_GET_CREATE_DESCRIPTION_ID;
    public static final ObfuscationMapping<Method> METHOD_ENCHANTMENT_CHECK_COMPATIBILITY;
    public static final MappedRegistryProxy PROXY_MAPPED_REGISTRY;
    public static final HolderReferenceProxy PROXY_HOLDER;

    static {
        // Setup Reflection Remapper
        ReflectionRemapper remapper = ReflectionRemapper.forReobfMappingsInPaperJar();

        // Initialise Fields
        METHOD_ENCHANTMENT_CHECK_COMPATIBILITY = createMethodMapping(remapper, Enchantment.class, "checkCompatibility", Enchantment.class);
        METHOD_ENCHANTMENT_GET_CREATE_DESCRIPTION_ID = createMethodMapping(remapper, Enchantment.class, "getOrCreateDescriptionId");

        // Initialise Proxies
        final ReflectionProxyFactory reflectionProxyFactory = ReflectionProxyFactory.create(remapper, ObfuscationMapping.class.getClassLoader());
        PROXY_MAPPED_REGISTRY = reflectionProxyFactory.reflectionProxy(MappedRegistryProxy.class);
        PROXY_HOLDER = reflectionProxyFactory.reflectionProxy(HolderReferenceProxy.class);
    }

    public static ObfuscationMapping<Field> createFieldMapping(ReflectionRemapper remapper, Class<?> klass, String mappedName) {
        return new ObfuscationMapping<>(klass, mappedName, remapper.remapFieldName(klass, mappedName), false);
    }

    public static ObfuscationMapping<Method> createMethodMapping(ReflectionRemapper remapper, Class<?> klass, String mappedName, Class<?>... paramTypes) {
        return new ObfuscationMapping<>(klass, mappedName, remapper.remapMethodName(klass, mappedName, paramTypes), true);
    }

    private final String obfuscatedName;
    private final Class<?>[] paramTypes;
    private final String mappedName;
    private final Class<?> klass;
    private final boolean method;

    private ObfuscationMapping(Class<?> klass, String mappedName, String obfuscatedName, boolean method, Class<?>... paramTypes) {
        this.obfuscatedName = obfuscatedName;
        this.paramTypes = paramTypes;
        this.mappedName = mappedName;
        this.method = method;
        this.klass = klass;
    }

    public String getMappedName() {
        return this.mappedName;
    }

    public String getObfuscatedName() {
        return this.obfuscatedName;
    }

    public T get(Class<?> holdingClass) {
        try {
            if (this.method) {
                final Method method = holdingClass.getDeclaredMethod(this.obfuscatedName, paramTypes);
                method.setAccessible(true);
                return (T) method;
            } else {
                final Field field = holdingClass.getDeclaredField(this.obfuscatedName);
                field.setAccessible(true);
                return (T) field;
            }
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public T get() {
        return this.get(this.klass);
    }
}
