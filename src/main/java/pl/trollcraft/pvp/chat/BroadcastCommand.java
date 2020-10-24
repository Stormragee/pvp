package pl.trollcraft.pvp.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("prison.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Uzycie: &e/" + label + " (-p) <wiadomosc>"));
            return true;
        }

        int start = 0;
        boolean p = false; // Show nickname.

        if (args[0].startsWith("-")){

            if (args[0].length() > 1){
                start = 1;
                if (args[0].contains("p"))
                    p = true;

            }
            else {
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBledne argumenty."));
                return true;
            }

        }

        String prefix = "&e------------- &e&l[Ogloszenie] &e-------------";
        String suffix = "&e----------------------------------------------";

        StringBuilder msg = new StringBuilder();

        if (p)
            prefix = "&e----- &e&l[Ogloszenie " + sender.getName() + "] &e-----";

        for (int i = start ; i < args.length ; i++)
            msg.append(args[i] + " ");

        String message = ChatUtils.fixColor(msg.toString());

        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatUtils.sendMessage(player, "");
            ChatUtils.sendMessage(player, prefix);
            ChatUtils.sendMessage(player, "");
            ChatUtils.sendMessage(player, message);
            ChatUtils.sendMessage(player, "");
            ChatUtils.sendMessage(player, suffix);
            ChatUtils.sendMessage(player, "");

        }

        return true;
    }
}
