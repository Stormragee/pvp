package pl.trollcraft.pvp.chat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.HashMap;

public class AutoMessages {

    private static HashMap<Player, Boolean> autoMessagesEnabled = new HashMap<>();
    private static ArrayList<String> messages = new ArrayList<>();
    private static int index = 0;

    public static void init() {

        YamlConfiguration conf = Configs.load("automessages.yml");
        long interval = conf.getLong("automessages.interval") * 20l;
        conf.getStringList("automessages.messages").forEach( msg -> messages.add(ChatUtils.fixColor(msg)) );

        new BukkitRunnable() {

            @Override
            public void run() {

                String message = messages.get(index);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    ChatUtils.sendMessage(p, "");
                    ChatUtils.sendMessage(p, message);
                    ChatUtils.sendMessage(p, "");
                }

                if (index == messages.size() - 1)
                    index = 0;
                else
                    index++;

            }

        }.runTaskTimerAsynchronously(PVP.getPlugin(), interval, interval);


    }

}
