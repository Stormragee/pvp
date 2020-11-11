package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class UniqueEnchantment extends Enchantment {

    public UniqueEnchantment() {
        super(19060);
    }

    @Override
    public String getName() {
        return "Blast";
    }

    @Override
    public int getMaxLevel() {
        return 100000;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }

}
