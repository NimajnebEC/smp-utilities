package net.nimajnebec.smputilities.commands;

import de.myzelyam.api.vanish.VanishAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nimajnebec.smputilities.SMP;
import net.nimajnebec.smputilities.datatypes.GamemodeDataType;
import net.nimajnebec.smputilities.datatypes.LocationDataType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ModerateCommand implements CommandExecutor {
    public final NamespacedKey KEY_IS_MODERATING;
    public final NamespacedKey KEY_OLD_LOCATION;
    public final NamespacedKey KEY_OLD_GAMEMODE;

    public ModerateCommand(SMP plugin) {
        this.KEY_IS_MODERATING = new NamespacedKey(plugin, "is_moderating");
        this.KEY_OLD_LOCATION = new NamespacedKey(plugin, "old_location");
        this.KEY_OLD_GAMEMODE = new NamespacedKey(plugin, "old_gamemode");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!Bukkit.getPluginManager().isPluginEnabled("SuperVanish") && !Bukkit.getPluginManager().isPluginEnabled("PremiumVanish")) {
            sender.sendMessage(Component.text("[!] SuperVanish plugin not installed").color(NamedTextColor.RED));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("[!] Only players may use this command").color(NamedTextColor.RED));
            return true;
        }

        PersistentDataContainer container = player.getPersistentDataContainer();
        if (container.has(KEY_IS_MODERATING, PersistentDataType.BOOLEAN) && container.get(KEY_IS_MODERATING, PersistentDataType.BOOLEAN))
            this.stopModerating(player);
        else this.startModerating(player);

        return true;
    }

    private void stopModerating(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(KEY_IS_MODERATING, PersistentDataType.BOOLEAN, false);
        player.teleport(container.get(KEY_OLD_LOCATION, LocationDataType.INSTANCE));
        player.setGameMode(container.get(KEY_OLD_GAMEMODE, GamemodeDataType.INSTANCE));
        VanishAPI.showPlayer(player);

        player.sendMessage(Component.text("[!] You are no longer in moderation mode").color(NamedTextColor.GREEN));

        // Cleanup Keys
        container.remove(KEY_OLD_GAMEMODE);
        container.remove(KEY_OLD_LOCATION);
    }

    private void startModerating(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(KEY_IS_MODERATING, PersistentDataType.BOOLEAN, true);
        container.set(KEY_OLD_GAMEMODE, GamemodeDataType.INSTANCE, player.getGameMode());
        container.set(KEY_OLD_LOCATION, LocationDataType.INSTANCE, player.getLocation());
        player.setGameMode(GameMode.SPECTATOR);
        VanishAPI.hidePlayer(player);

        player.sendMessage(Component.text("[!] You are now in moderation mode").color(NamedTextColor.GREEN));
    }
}
