package pl.trollcraft.pvp.antylogout;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

import java.util.List;

public class AntyLogoutData {

    private static List<String> worlds;
    private static List<String> commandsAccepted;

    public static void load() {
        YamlConfiguration conf = Configs.load("antylogout.yml");

        assert conf != null;
        worlds = conf.getStringList("antylogout.worlds");
        commandsAccepted = conf.getStringList("antylogout.commands.accepted");
    }

    public static boolean shouldCheck(World world) {
        String worldName = world.getName();
        return worlds.contains(worldName);
    }

    public static boolean commandAccepted(String command) {
        String[] data = command.split(" ");
        String arg1 = data[0].substring(1);
        return commandsAccepted.contains(arg1);
    }

}
