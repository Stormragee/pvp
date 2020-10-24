package pl.trollcraft.pvp.warp.portals;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.warp.Warp;

import java.util.ArrayList;

public class PortalsManager {

    private static ArrayList<Portal> portals = new ArrayList<>();

    public static void register(Portal portal) { portals.add(portal); }

    public static void remove(Portal portal) {
        portals.remove(portal);
        YamlConfiguration conf = Configs.load("portals.yml");
        conf.set("portals." + portal.getName(), null);
        Configs.save(conf, "portals.yml");
    }

    public static void save(Portal portal) {
        YamlConfiguration conf = Configs.load("portals.yml");
        conf.set("portals." + portal.getName() + ".warp", portal.getWarp().getName());
        conf.set("portals." + portal.getName() + ".a", ChatUtils.locToString(portal.getA()));
        conf.set("portals." + portal.getName() + ".b", ChatUtils.locToString(portal.getB()));
        Configs.save(conf, "portals.yml");
    }

    public static void load() {

        YamlConfiguration conf = Configs.load("portals.yml");
        conf.getConfigurationSection("portals").getKeys(false).forEach( name -> {

            Warp warp = Warp.get(conf.getString("portals." + name + ".warp"));
            Location a = ChatUtils.locFromString(conf.getString("portals." + name + ".a"));
            Location b = ChatUtils.locFromString(conf.getString("portals." + name + ".b"));

            portals.add(new Portal(name, warp, a, b));

        } );

    }

    public static Portal getPortal(Location loc) {
        for (Portal p : portals) {
            if (p.getA() == null || p.getB() == null) continue;
            if (p.isInPortal(loc)) return p;
        }
        return null;
    }

    public static Portal getPortal(String name) {
        for (Portal p : portals)
            if (p.getName().equals(name)) return p;
        return null;
    }

}
