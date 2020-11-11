package pl.trollcraft.pvp.kits;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class KitsManager {

    private static Kit playerDefault;
    private static Kit vipDefault;
    private static Kit svipDefault;
    private static Kit hunterDefault;

    private static ArrayList<Kit> kits = new ArrayList<>();

    public static void register(Kit kit) { kits.add(kit); }

    public static void remove(Kit kit) {
        kits.remove(kit);
        YamlConfiguration conf = Configs.load("kits.yml");
        conf.set("kits." + kit.getName(), null);
        Configs.save(conf, "kits.yml");
    }

    public static void save(Kit kit) {
        YamlConfiguration conf = Configs.load("kits.yml");
        conf.set("kits." + kit.getName(), null);
        conf.set("kits." + kit.getName() + ".permission", kit.getPermission());
        conf.set("kits." + kit.getName() + ".clearInventory", kit.clearsInventory());
        conf.set("kits." + kit.getName() + ".cooldown", kit.getCooldown());

        for (int i = 0 ; i < kit.getArmor().length ; i++)
            conf.set("kits." + kit.getName() + ".armor." + i, kit.getArmor()[i]);

        for (int i = 0 ; i < kit.getItems().size() ; i++)
            conf.set("kits." + kit.getName() + ".items." + i + ".item", kit.getItems().get(i));

        Configs.save(conf, "kits.yml");
    }

    public static void load() {

        YamlConfiguration conf = Configs.load("kits.yml");
        assert conf != null;

        conf.getConfigurationSection("kits").getKeys(false).forEach(name -> {

            String permission = conf.getString("kits." + name + ".permission");
            boolean clearInventory = conf.getBoolean("kits." + name + ".clearInventory");
            long cooldown = conf.getLong("kits." + name + ".cooldown");

            ItemStack[] armor = new ItemStack[4];
            for (int i = 0 ; i < 4 ; i++)
                armor[i] = conf.getItemStack("kits." + name + ".armor." + i);

            ArrayList<ItemStack> items = new ArrayList<>();
            conf.getConfigurationSection("kits." + name + ".items").getKeys(false).forEach( i -> items.add(conf.getItemStack("kits." + name + ".items." + i + ".item")));

            kits.add(new Kit(name, permission, clearInventory, cooldown, armor, items));
        } );

        playerDefault = getKit(conf.getString("default.player"));
        vipDefault = getKit(conf.getString("default.vip"));
        svipDefault = getKit(conf.getString("default.svip"));
        hunterDefault = getKit(conf.getString("default.hunter"));
    }

    public static Kit getPlayerDefault() { return playerDefault; }
    public static Kit getVipDefault() { return vipDefault; }
    public static Kit getSvipDefault() { return svipDefault; }
    public static Kit getHunterDefault() { return hunterDefault; }

    public static Kit getKit(String name) {
        for (Kit kit : kits)
            if (kit.getName().equals(name)) return kit;
        return null;
    }

}
