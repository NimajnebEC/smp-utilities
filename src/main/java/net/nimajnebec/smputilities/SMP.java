package net.nimajnebec.smputilities;

import net.nimajnebec.smputilities.commands.ModerateCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMP extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("moderate").setExecutor(new ModerateCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
