package net.nimajnebec.smputilities.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class LocationDataType implements PersistentDataType<PersistentDataContainer, Location> {
    public static final LocationDataType INSTANCE = new LocationDataType();
    static final NamespacedKey KEY_WORLD = new NamespacedKey("location", "world");
    static final NamespacedKey KEY_X = new NamespacedKey("location", "x");
    static final NamespacedKey KEY_Y = new NamespacedKey("location", "y");
    static final NamespacedKey KEY_Z = new NamespacedKey("location", "z");
    static final NamespacedKey KEY_PITCH = new NamespacedKey("location", "pitch");
    static final NamespacedKey KEY_YAW = new NamespacedKey("location", "yaw");

    @Override
    public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public @NotNull Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public @NotNull PersistentDataContainer toPrimitive(@NotNull Location location, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        PersistentDataContainer container = persistentDataAdapterContext.newPersistentDataContainer();
        container.set(KEY_WORLD, PersistentDataType.STRING, location.getWorld().getName());
        container.set(KEY_PITCH, PersistentDataType.FLOAT, location.getPitch());
        container.set(KEY_YAW, PersistentDataType.FLOAT, location.getYaw());
        container.set(KEY_X, PersistentDataType.DOUBLE, location.x());
        container.set(KEY_Y, PersistentDataType.DOUBLE, location.y());
        container.set(KEY_Z, PersistentDataType.DOUBLE, location.z());
        return container;
    }

    @Override
    public @NotNull Location fromPrimitive(@NotNull PersistentDataContainer container, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        String worldName = container.get(KEY_WORLD, PersistentDataType.STRING);
        World world = Bukkit.getServer().getWorld(worldName);
        float pitch = container.get(KEY_PITCH, PersistentDataType.FLOAT);
        float yaw = container.get(KEY_YAW, PersistentDataType.FLOAT);
        double x = container.get(KEY_X, PersistentDataType.DOUBLE);
        double y = container.get(KEY_Y, PersistentDataType.DOUBLE);
        double z = container.get(KEY_Z, PersistentDataType.DOUBLE);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
