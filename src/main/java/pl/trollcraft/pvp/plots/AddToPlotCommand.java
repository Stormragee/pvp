package pl.trollcraft.pvp.plots;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class AddToPlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1)
            player.performCommand(String.format("p add %s", args[0]));

        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/dodaj <gracz>"));

        return true;
    }
}
