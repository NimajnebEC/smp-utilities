package net.nimajnebec.smputilities.enchantment;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.nimajnebec.smputilities.reflection.ObfuscationMapping;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class EnchantmentImpostor extends Enchantment {

    private final Enchantment target;
    private final Method checkCompatibilityMethod;
    private final Method getDescriptionMethod;

    public EnchantmentImpostor(Enchantment handle) {
        super(handle.getRarity(), handle.category, handle.slots);
        this.target = handle;

        checkCompatibilityMethod = ObfuscationMapping.METHOD_ENCHANTMENT_CHECK_COMPATIBILITY.get();
        getDescriptionMethod = ObfuscationMapping.METHOD_ENCHANTMENT_GET_CREATE_DESCRIPTION_ID.get();
    }

    public Enchantment getTarget() {
        return this.target;
    }

    @Override
    public @NotNull Map<EquipmentSlot, ItemStack> getSlotItems(@NotNull LivingEntity entity) {
        return target.getSlotItems(entity);
    }

    @Override
    public @NotNull Rarity getRarity() {
        return target.getRarity();
    }

    @Override
    public int getMinLevel() {
        return target.getMinLevel();
    }

    @Override
    public int getMaxLevel() {
        return target.getMaxLevel();
    }

    @Override
    public int getMinCost(int level) {
        return target.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return target.getMaxCost(level);
    }

    @Override
    public int getDamageProtection(int level, @NotNull DamageSource source) {
        return target.getDamageProtection(level, source);
    }

    @Override
    public float getDamageBonus(int level, @NotNull MobType group) {
        return target.getDamageBonus(level, group);
    }

    @Override
    public boolean checkCompatibility(@NotNull Enchantment other) {
        try {
            return (boolean) checkCompatibilityMethod.invoke(target, other);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull String getOrCreateDescriptionId() {
        try {
            return (String) getDescriptionMethod.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull String getDescriptionId() {
        return target.getDescriptionId();
    }

    @Override
    public @NotNull Component getFullname(int level) {
        return target.getFullname(level);
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack stack) {
        return target.canEnchant(stack);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity user, @NotNull Entity target, int level) {
        this.target.doPostAttack(user, target, level);
    }

    @Override
    public void doPostHurt(@NotNull LivingEntity user, @NotNull Entity attacker, int level) {
        target.doPostHurt(user, attacker, level);
    }

    @Override
    public boolean isTreasureOnly() {
        return target.isTreasureOnly();
    }

    @Override
    public boolean isCurse() {
        return target.isCurse();
    }

    @Override
    public boolean isTradeable() {
        return target.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return target.isDiscoverable();
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return target.equals(obj);
    }

    @Override
    public String toString() {
        return target.toString();
    }
}