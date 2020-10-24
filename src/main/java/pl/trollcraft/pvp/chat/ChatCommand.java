package pl.trollcraft.pvp.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class ChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Zarzadzanie chat'em:"));
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/chat toggle - &7wlacza/wylacza chat,"));
            return true;
        }

        if (args[0].equalsIgnoreCase("gtoggle")) {

            if (!sender.hasPermission("prison.admin")){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
                return true;
            }

            ChatProcessor.switchChat();

            String message = "&7Administrator &awlaczyl chat.";
            if (!ChatProcessor.isChatEnabled())
                message = "&7Administrator &cwylaczyl chat.";

            message = ChatUtils.fixColor(message);

            for (Player p : Bukkit.getOnlinePlayers()) {
                ChatUtils.sendMessage(p, "");
                ChatUtils.sendMessage(p, ChatUtils.fixColor(message));
                ChatUtils.sendMessage(p, "");
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("toggle")) {

            if (!(sender instanceof Player)){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jedynie dla graczy online."));
                return true;
            }

            if (!sender.hasPermission("pvp.vip")){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda ta dostepna jest dla graczy &eVIP i SVIP."));
                return true;
            }

            Player player = (Player) sender;
            ChatProfile profile = ChatProfile.get(player);

            boolean chat = !profile.hasChatEnabled();
            profile.setChatEnabled(chat);

            if (chat)
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&aWlaczono chat."));
            else
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cWylaczono chat."));

            return true;
        }

        return true;
    }
}
