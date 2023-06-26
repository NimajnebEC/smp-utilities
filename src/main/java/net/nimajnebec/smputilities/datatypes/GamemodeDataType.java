package net.nimajnebec.smputilities.datatypes;

import org.bukkit.GameMode;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class GamemodeDataType implements PersistentDataType<String, GameMode> {
    public static GamemodeDataType INSTANCE = new GamemodeDataType();

    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<GameMode> getComplexType() {
        return GameMode.class;
    }

    @Override
    public @NotNull String toPrimitive(@NotNull GameMode gameMode, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return gameMode.name();
    }

    @Override
    public @NotNull GameMode fromPrimitive(@NotNull String s, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return GameMode.valueOf(s);
    }
}
