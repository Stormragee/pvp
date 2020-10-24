package pl.trollcraft.pvp.cmds;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.List;

public class CmdManager {

    private static String bypassPermission;
    private static List<String> cmds;


    public static void save() {
        YamlConfiguration conf = Configs.load("cmds.yml");
        conf.set("cmds", cmds);
        Configs.save(conf, "cmds.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("cmds.yml");
        bypassPermission = conf.getString("bypassPermission");

        if (!conf.contains("cmds")) cmds = new ArrayList<>();
        else cmds = conf.getStringList("cmds");
    }

    public static boolean add(String cmd){
        if (contains(cmd)) return false;
        cmds.add(cmd);
        save();
        return true;
    }

    public static boolean remove(String cmd){
        if (!contains(cmd)) return false;
        cmds.remove(cmd);
        save();
        return true;
    }

    public static String getBypassPermission() { return bypassPermission; }

    public static String getCmds() {
        StringBuilder cmdsText = new StringBuilder();
        int i = 1;
        for (String cmd : cmds)
            cmdsText.append(ChatUtils.fixColor("&e" + i + ". &7" + cmd + "\n"));
        return cmdsText.toString();
    }

    public static boolean contains(String cmd){
        for (String c : cmds) {
            if (cmd.contains(c)) return true;
        }
        return false;
    }

}
