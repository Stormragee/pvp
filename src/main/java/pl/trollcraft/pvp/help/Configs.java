package pl.trollcraft.pvp.help;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.trollcraft.pvp.PVP;

import java.io.File;
import java.io.IOException;

public class Configs {

    private static final PVP pvp = PVP.getPlugin();

    public static YamlConfiguration load(String configName){
        YamlConfiguration config;
        File file = new File(pvp.getDataFolder() + File.separator + configName);
        if (!file.exists())
            pvp.saveResource(configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(pvp.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
