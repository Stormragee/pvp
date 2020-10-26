package pl.trollcraft.pvp.plots;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class PlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("Komenda jedynie dla graczy."));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1)
            player.performCommand(String.format("p visit %s", args[0]));

        else
            player.performCommand("p home");

        return true;
    }
}
