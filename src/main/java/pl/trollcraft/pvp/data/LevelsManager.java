package pl.trollcraft.pvp.data;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class LevelsManager {

    private static ArrayList<Level> levels = new ArrayList<>();

    public static void register(Level level) { levels.add(level); }

    public static void load() {
        YamlConfiguration conf = Configs.load("levels.yml");

        conf.getConfigurationSection("levels").getKeys(false).forEach( lvl -> {
            int level = Integer.parseInt(lvl);
            int killsRequired = conf.getInt("levels." + lvl + ".killsRequired");
            register(new Level(level, killsRequired));
        } );

    }

    public static Level get(int level) {
        for (Level l : levels)
            if (l.getValue() == level) return l;
        return null;
    }

}
