package pl.trollcraft.pvp.data;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

public class ConfigData {

    private static double resetPrice;

    public static void load() {
        YamlConfiguration conf = Configs.load("config.yml");
        assert conf != null;

        resetPrice = conf.getDouble("reset-price");
    }

    public static double getResetPrice() {
        return resetPrice;
    }

}
