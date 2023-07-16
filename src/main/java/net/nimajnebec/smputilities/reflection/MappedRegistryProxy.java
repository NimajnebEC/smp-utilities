package net.nimajnebec.smputilities.reflection;

import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import xyz.jpenilla.reflectionremapper.proxy.annotation.FieldGetter;
import xyz.jpenilla.reflectionremapper.proxy.annotation.FieldSetter;
import xyz.jpenilla.reflectionremapper.proxy.annotation.Proxies;

import java.util.Map;

@Proxies(MappedRegistry.class)
public interface MappedRegistryProxy {
    @FieldGetter("byId")
    <T> ObjectList<Holder.Reference<T>> getById(MappedRegistry<T> instance);

    @FieldGetter("toId")
    <T> Reference2IntOpenHashMap<T> getToId(MappedRegistry<T> instance);

    @FieldGetter("byLocation")
    <T> Map<ResourceLocation, Holder.Reference<T>> geByLocation(MappedRegistry<T> instance);

    @FieldGetter("byKey")
    <T> Map<ResourceKey<T>, Holder.Reference<T>> getByKey(MappedRegistry<T> instance);

    @FieldGetter("byValue")
    <T> Map<T, Holder.Reference<T>> getByValue(MappedRegistry<T> instance);

    @FieldGetter("tags")
    <T> Map<TagKey<T>, HolderSet.Named<T>> getTags(MappedRegistry<T> instance);
}
