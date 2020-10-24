package pl.trollcraft.pvp.help;

import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

public class SimpleWorldManager {

    public SimpleWorldManager() {
        YamlConfiguration conf = Configs.load("worlds.yml");
        conf.getConfigurationSection("worlds").getKeys(false).forEach( world ->
                new WorldCreator(world).createWorld());
    }


}
