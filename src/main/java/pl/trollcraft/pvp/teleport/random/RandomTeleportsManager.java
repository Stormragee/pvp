package pl.trollcraft.pvp.teleport.random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class RandomTeleportsManager {

    private final ArrayList<RandomTeleport> randomTeleports;

    public RandomTeleportsManager() {
        this.randomTeleports = new ArrayList<>();
    }

    public void register(RandomTeleport randomTeleport) {
        randomTeleports.add(randomTeleport);
    }

    public ArrayList<RandomTeleport> getRandomTeleports() {
        return randomTeleports;
    }

    public void load() {

        YamlConfiguration conf = Configs.load("randomteleports.yml");
        assert conf != null;

        ConfigurationSection sec = conf.getConfigurationSection("randomteleports");
        sec.getKeys(false).forEach( name -> {

            String worldName = conf.getString(String.format("randomteleports.%s.world", name));
            World world = Bukkit.getWorld(worldName);

            Point min = new Point(), max = new Point();
            min.setX(conf.getInt(String.format("randomteleports.%s.min.x", name)));
            min.setZ(conf.getInt(String.format("randomteleports.%s.min.z", name)));
            max.setX(conf.getInt(String.format("randomteleports.%s.max.x", name)));
            max.setZ(conf.getInt(String.format("randomteleports.%s.max.z", name)));

            int offsetMin = conf.getInt(String.format("randomteleports.%s.offset.min", name));
            int offsetMax = conf.getInt(String.format("randomteleports.%s.offset.max", name));

            World w = Bukkit.getWorld(conf.getString(String.format("randomteleports.%s.teleport.center.world", name)));
            double x = conf.getDouble(String.format("randomteleports.%s.teleport.center.x", name));
            double y = conf.getDouble(String.format("randomteleports.%s.teleport.center.y", name));
            double z = conf.getDouble(String.format("randomteleports.%s.teleport.center.z", name));

            Location teleportCenter = new Location(w, x, y, z);
            int teleportRange = conf.getInt(String.format("randomteleports.%s.teleport.range", name));

            int interval = conf.getInt(String.format("randomteleports.%s.interval", name));

            register(new RandomTeleport(world, min, max, offsetMin, offsetMax, teleportCenter, teleportRange, interval));

        } );

    }


}
