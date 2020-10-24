package pl.trollcraft.pvp.help;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemSerializer {

    public static void serialize(ItemStack itemStack, YamlConfiguration conf, String path) {
        conf.set(path + ".type", itemStack.getType().name());

        if (itemStack.getData().getData() != 0)
            conf.set(path + ".data", itemStack.getData().getData());

        conf.set(path + ".amount", itemStack.getAmount());
    }

    public static ItemStack deserialize(YamlConfiguration conf, String path) {

        Material type = Material.getMaterial(conf.getString(path + ".type"));
        byte data = 0;

        if (conf.contains(path + ".data"))
            data = Byte.parseByte(conf.getString(path + ".data"));

        int amount = 1;
        if (conf.contains(path + ".amount"))
            amount = conf.getInt(path + ".amount");

        String name = null;
        List<String> lore = null;
        if (conf.contains(path + ".name"))
            name = conf.getString(path + ".name");
        if (conf.contains(path + ".lore"))
            lore = conf.getStringList(path + ".lore");

        ItemStack itemStack = new ItemStack(type, amount, data);

        if (conf.contains(path + ".enchantments")){
            conf.getStringList(path + ".enchantments").forEach( e -> {
                String[] enchData = e.split(":");
                Enchantment enchantment = Enchantment.getByName(enchData[0]);
                int level = Integer.parseInt(enchData[1]);
                itemStack.addEnchantment(enchantment, level);
            } );
        }

        if (name != null || lore != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (name != null)
                itemMeta.setDisplayName(name);
            if (lore != null)
                itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

}
