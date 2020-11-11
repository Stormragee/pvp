package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class UniqueItem {

    private final int id;
    private final PotionEffect potionEffect;
    private final ItemStack itemStack;

    // ---- ---- ---- ----

    public UniqueItem(int id, PotionEffect potionEffect, ItemStack itemStack) {
        this.id = id;
        this.potionEffect = potionEffect;
        this.itemStack = itemStack;
    }

    public void give(Player player) {
        ItemStack is = itemStack.clone();
        is.addEnchantment(EnchantRegister.UNIQUE_ENCHANTMENT, id);
        player.getInventory().addItem(is);
    }

    public void affect(Player player) {
        player.addPotionEffect(potionEffect);
    }

    public void stopAffect(Player player) {
        player.removePotionEffect(potionEffect.getType());
    }

    public int getId() {
        return id;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
