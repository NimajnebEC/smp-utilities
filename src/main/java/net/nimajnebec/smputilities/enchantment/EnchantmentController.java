package net.nimajnebec.smputilities.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class EnchantmentController {

    private final List<ImpostorInjector> injectors = new ArrayList<>();
    private final FileConfiguration config;

    public EnchantmentController(FileConfiguration confg) {
        this.config = confg;
    }

    public void setup() {
        addEnabledInjectors();

        // Inject all enabled injectors
        try {
            for (ImpostorInjector injector: injectors) {
                    injector.inject();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void addEnabledInjectors() {
        // Add Infinity + Mending impostor
        this.add("enchantment.allow-mending-infinity", new EnchantmentImpostor(Enchantments.INFINITY_ARROWS) {
            @Override
            public boolean checkCompatibility(@NotNull Enchantment other) {
                return this != other;
            }
        });

        // Add Axe Impostors
        this.add("enchantment.allow-axe-fire-aspect", new AxeEnchantmentImpostor(Enchantments.FIRE_ASPECT));
        this.add("enchantment.allow-axe-knockback", new AxeEnchantmentImpostor(Enchantments.KNOCKBACK));
        this.add("enchantment.allow-axe-looting", new AxeEnchantmentImpostor(Enchantments.MOB_LOOTING) {
            @Override
            public boolean checkCompatibility(@NotNull Enchantment other) {
                // We have to override this to allow silk touch and looting
                return this != other;
            }
        });
    }

    private void add(String key, EnchantmentImpostor impostor) {
        if (config.getBoolean(key, false)) injectors.add(new ImpostorInjector(impostor));
    }

    public void cleanup() {
        try {
            for (ImpostorInjector injector: injectors) {
                injector.restore();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
