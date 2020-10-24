package pl.trollcraft.pvp.warehouses;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.gui.GUI;

import java.util.Arrays;
import java.util.Collection;

public class WarehousesManager {

    private static Multimap<Player, Warehouse> warehouses = ArrayListMultimap.create();

    private static double slotPrice;

    public static void register(Player player, Warehouse warehouse) { warehouses.put(player, warehouse); }
    public static void unregister(Player player) { warehouses.removeAll(player); }

    public static boolean openWarehouses(Player player) {

        Collection<Warehouse> warehouses = WarehousesManager.warehouses.get(player);
        if (warehouses.isEmpty()) return false;

        GUI gui = new GUI(9, "§0§lTwoje magazyny");
        int i = 0;
        ItemStack is;
        ItemMeta im;
        for (Warehouse warehouse : warehouses) {

            is = new ItemStack(Material.CHEST, i+1);
            im = is.getItemMeta();
            im.setDisplayName(ChatUtils.fixColor(warehouse.getName()));

            if (warehouse.hasExpired()) {
                im.setLore(Arrays.asList(new String[]{"", ChatUtils.fixColor("&cWynajem zakonczyl sie."),
                        ChatUtils.fixColor("&7Wynajmij za: &e" + warehouse.getPrice() + "TC"),
                        ChatUtils.fixColor("&eKliknij, by przedluzyc wynajem.")}));
                is.setItemMeta(im);

                gui.addItem(i, is, e -> {
                    e.setCancelled(true);
                    EconomyProfile profile = EconomyManager.get(player);
                    if (profile.take(warehouse.getPrice())){
                        warehouse.rent();
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aMagazyn przedluzono!"));
                        warehouse.open(player);
                    }
                    else ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak srodkow."));

                });
            }
            else {
                im.setLore(Arrays.asList(new String[]{"", ChatUtils.fixColor("&aWynajem konczy sie za:"),
                        ChatUtils.fixColor("&e" + warehouse.getExpiresFormatted())}));
                is.setItemMeta(im);

                gui.addItem(i, is, e -> {
                    e.setCancelled(true);
                    warehouse.open(player);
                });
            }

            i++;
        }
        gui.open(player);
        return true;

    }

    public static void save(Player player) {
        Collection<Warehouse> warehouses = WarehousesManager.warehouses.get(player);
        if (warehouses.isEmpty()) return;

        YamlConfiguration conf = Configs.load("warehouses.yml");

        if (conf.contains("warehouses." + player.getName()))
            conf.set("warehouses." + player.getName(), null);

        warehouses.forEach( warehouse -> {

            conf.set("warehouses." + player.getName() + "." + warehouse.getId() + ".name", warehouse.getName());
            conf.set("warehouses." + player.getName() + "." + warehouse.getId() + ".expires", warehouse.getExpires());
            conf.set("warehouses." + player.getName() + "." + warehouse.getId() + ".slots", warehouse.getSlots());

            ItemStack[] items = warehouse.getItems();
            for (int i = 0 ; i < items.length ; i++)
                conf.set("warehouses." + player.getName() + "." + warehouse.getId() + ".items." + i + ".item", items[i]);

        } );

        Configs.save(conf, "warehouses.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("warehouses.yml");
        slotPrice = conf.getDouble("slotPrice");
    }

    public static void load(Player player) {
        if (warehouses.containsKey(player)) warehouses.removeAll(player);

        YamlConfiguration conf = Configs.load("warehouses.yml");

        if (!conf.contains("warehouses." + player.getName())) return;

        conf.getConfigurationSection("warehouses." + player.getName()).getKeys(false).forEach( id -> {

            String name = conf.getString("warehouses." + player.getName() + "." + id + ".name");
            long expires = conf.getLong("warehouses." + player.getName() + "." + id + ".expires");
            int slots = conf.getInt("warehouses." + player.getName() + "." + id + ".slots");

            Warehouse warehouse = new Warehouse(Integer.parseInt(id), name, slots, expires);

            conf.getConfigurationSection("warehouses." + player.getName() + "." + id + ".items").getKeys(false).forEach( i -> {
                warehouse.getInventory().setItem(Integer.parseInt(i), conf.getItemStack("warehouses." + player.getName() + "." + id + ".items." + i + ".item"));
            } );

            register(player, warehouse);

        } );

    }

    public static double getSlotPrice() { return slotPrice; }

    public static Collection<Warehouse> getWarehouses(Player player) {
        return warehouses.get(player);
    }

}
