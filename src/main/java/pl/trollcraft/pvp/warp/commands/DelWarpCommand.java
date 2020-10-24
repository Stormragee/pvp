package pl.trollcraft.pvp.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.warp.Warp;

public class DelWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length != 1) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/delwarp <nazwa>"));
            return true;
        }

        Warp warp = Warp.get(args[0]);
        warp.remove();

        ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUsunieto warp &e" + warp.getName()));

        return true;
    }
}
