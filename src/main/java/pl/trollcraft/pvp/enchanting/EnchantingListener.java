package pl.trollcraft.pvp.enchanting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.help.Help;

import java.util.HashMap;
import java.util.Map;

public class EnchantingListener implements Listener {

    public Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
        if (itemStack.getItemMeta() instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta esm = (EnchantmentStorageMeta) itemStack.getItemMeta();
            return esm.getStoredEnchants();
        }
        else
            return itemStack.getEnchantments();

    }

    @EventHandler(priority= EventPriority.LOWEST)
    public void onEnchant(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        Map<Enchantment, Integer> enchant = new HashMap<>();

        if (inv != null
                && event.getCursor() != null && event.getCurrentItem() != null
                && event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)
                && event.getClickedInventory() instanceof PlayerInventory) {

            ItemStack book = event.getCursor();
            ItemStack item = event.getCurrentItem();

            if (book.getType() == Material.ENCHANTED_BOOK) {
;
                ItemMeta imeta = item.getItemMeta();
                for (Map.Entry<Enchantment, Integer> entry1 : getEnchantments(book).entrySet()) {
                    enchant.put(entry1.getKey(), entry1.getValue());
                }
                for (Map.Entry<Enchantment, Integer> entry : enchant.entrySet()) {
                    if (item.containsEnchantment(entry.getKey())) {
                        if (item.getEnchantmentLevel(entry.getKey()) >= entry.getValue()) {
                            player.sendMessage(Help.color("&cItem posiada juz ten enchant!"));
                            return;
                        }
                    }
                    if (!(entry.getKey().canEnchantItem(item))) {
                        player.sendMessage(Help.color("&cTen item nie moze miec tego enchantu!"));
                        return;
                    }
                    imeta.addEnchant(entry.getKey(), entry.getValue(), false);
                }
                player.setItemOnCursor(null);
                player.sendMessage(Help.color("&5&lEnchantowales item!"));
                item.setItemMeta(imeta);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }

}
