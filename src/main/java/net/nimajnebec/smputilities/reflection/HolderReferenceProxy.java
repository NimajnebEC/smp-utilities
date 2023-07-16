package net.nimajnebec.smputilities.reflection;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import xyz.jpenilla.reflectionremapper.proxy.annotation.Proxies;

@Proxies(Holder.Reference.class)
public interface HolderReferenceProxy {
    <T> void bindKey(Holder.Reference<T> instance, ResourceKey<T> registryKey);
}
