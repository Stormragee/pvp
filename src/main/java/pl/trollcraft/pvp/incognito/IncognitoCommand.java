package pl.trollcraft.pvp.incognito;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class IncognitoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy.");
            return true;
        }

        Player player = (Player) sender;
        if (Incognito.isIncognito(player)){
            Incognito.off(player);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cWylaczono &7tryb incognito."));
        }
        else{
            Incognito.on(player);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aWlaczono &7tryb incognito."));
        }

        return false;
    }
}
