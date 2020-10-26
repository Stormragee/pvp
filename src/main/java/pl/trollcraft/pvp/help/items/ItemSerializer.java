package pl.trollcraft.pvp.help.items;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;

public class ItemSerializer {

    private YamlConfiguration conf;
    private String section;

    private ItemStack itemStack;

    public ItemSerializer(YamlConfiguration conf, String section) {
        this.conf = conf;
        this.section = section;

        init();
        meta();
        enchants();
    }

    private void init() {
        Material type = Material.getMaterial(conf.getString(section + ".type"));
        int amount = conf.getInt(section + ".amount");
        itemStack = new ItemStack(type, amount);
    }

    private void meta() {

        if (!conf.contains(section + ".meta"))
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (conf.contains(section + ".meta.name"))
            itemMeta.setDisplayName(Help.color(conf.getString(section + ".meta.name")));

        if (conf.contains(section + ".meta.lore")) {
            ArrayList<String> lore = new ArrayList<>();
            conf.getStringList(section + ".meta.lore").forEach(l -> lore.add(Help.color(l)));
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);

    }

    private void enchants() {
        if (!conf.contains(section + ".enchants"))
            return;

        conf.getStringList(section + ".enchants").forEach( enchName -> {
            String[] data = enchName.split(":");
            Enchantment e = Enchantment.getByName(data[0]);
            int lvl = Integer.parseInt(data[1]);
            itemStack.addUnsafeEnchantment(e, lvl);
        } );

    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
