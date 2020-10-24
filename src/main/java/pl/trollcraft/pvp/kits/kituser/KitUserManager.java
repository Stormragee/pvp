package pl.trollcraft.pvp.kits.kituser;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.kits.Kit;
import pl.trollcraft.pvp.kits.KitsManager;

import java.util.ArrayList;
import java.util.HashMap;

public class KitUserManager {

    private static ArrayList<KitUser> kitUsers = new ArrayList<>();

    public static void register(KitUser kitUser) { kitUsers.add(kitUser); }

    public static void save(KitUser kitUser) {
        YamlConfiguration conf = Configs.load("kitusers.yml");

        if (kitUser.getKits().isEmpty())
            conf.set("kitusers." + kitUser.getPlayer().getName() + ".init.used", 0);

        else {

            if (kitUser == null) Bukkit.getConsoleSender().sendMessage("kituser");

            kitUser.getKits().forEach( (kit, used) -> {
                conf.set("kitusers." + kitUser.getPlayer().getName() + "." + kit.getName() + ".used", used);
            });

        }

        Configs.save(conf, "kitusers.yml");
    }

    public static boolean load(Player player) {
        String name = player.getName();
        YamlConfiguration conf = Configs.load("kitusers.yml");
        if (!conf.contains("kitusers." + name)) {
            KitUser kitUser = new KitUser(player, new HashMap<>());
            register(kitUser);
            save(kitUser);
            return false;
        }

        HashMap<Kit, Long> kits = new HashMap<>();
        conf.getConfigurationSection("kitusers." + name).getKeys(false).forEach( kit -> {
            if (kit.equals("init")) return;

            Kit k = KitsManager.getKit(kit);
            if (k == null) return;

            long used = conf.getLong("kitusers." + name + "." + kit + ".used");
            kits.put(k, used);
        } );

        register(new KitUser(player, kits));
        return true;
    }

    public static KitUser get(Player player) {
        int id = player.getEntityId();
        for (KitUser kitUser : kitUsers)
            if (kitUser.getPlayer().getEntityId() == id) return kitUser;
        return null;
    }

}
