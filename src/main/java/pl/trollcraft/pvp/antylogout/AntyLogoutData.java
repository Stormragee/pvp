package pl.trollcraft.pvp.antylogout;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

import java.util.List;

public class AntyLogoutData {

    private static List<String> worlds;

    public static void load() {
        YamlConfiguration conf = Configs.load("antylogout.yml");

        assert conf != null;
        worlds = conf.getStringList("antylogout.worlds");
    }

    public static boolean shouldCheck(World world) {
        String worldName = world.getName();
        return worlds.contains(worldName);
    }

}
