package net.nimajnebec.smputilities;

import net.nimajnebec.smputilities.commands.ModerateCommand;
import net.nimajnebec.smputilities.enchantment.EnchantmentController;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMP extends JavaPlugin {
    final EnchantmentController enchantmentController = new EnchantmentController(this.getConfig());

    @Override
    public void onEnable() {
        this.getCommand("moderate").setExecutor(new ModerateCommand(this));
        this.saveDefaultConfig();
        enchantmentController.setup();
    }

    @Override
    public void onDisable() {
        enchantmentController.cleanup();
    }
}
