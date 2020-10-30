package pl.trollcraft.pvp.incognito;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.trollcraft.pvp.help.Help;

public class TrueNickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("pvp.truenick")) {
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        Incognito.getIncognitoNames().forEach( (pl,ni) -> {
            sender.sendMessage(pl + " >> " + ni);
        } );

        sender.sendMessage("");
        IncognitoData.getNicksInUse().forEach(ni -> sender.sendMessage(ni + " "));

        sender.sendMessage("");
        Incognito.getIncognitoPlayers().forEach(ni -> sender.sendMessage(ni + " "));

        return true;

    }
}
