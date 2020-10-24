package pl.trollcraft.pvp.warp;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class Warp {

    private static ArrayList<Warp> warps = new ArrayList<>();

    private String name;
    private Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
        warps.add(this);
    }

    public void teleport(Player player) {
        if (!location.getChunk().isLoaded()) location.getChunk().load();
        player.teleport(location);
    }

    public void save() {
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.set("warps." + name + ".loc", ChatUtils.locToString(location));
        Configs.save(conf, "warps.yml");
    }

    public void remove() {
        warps.remove(this);
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.set("warps." + name, null);
        Configs.save(conf, "warps.yml");
    }

    public static Warp get(String name) {
        for (Warp w : warps){
            if (w.name.equalsIgnoreCase(name)) return w;
        }
        return null;
    }

    public String getName() { return name; }
    public Location getLocation() { return location; }

    public static void load() {
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.getConfigurationSection("warps").getKeys(false).forEach( name -> {
            Location loc = ChatUtils.locFromString(conf.getString("warps." + name + ".loc"));
            new Warp(name, loc);
        } );
    }

}
