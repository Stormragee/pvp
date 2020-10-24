package pl.trollcraft.pvp.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.warp.Warp;

public class SetWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda dostepna jedynie dla gracza online."));
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length != 1) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cUzycie: /setwarp <nazwa>"));
            return true;
        }

        Player player = (Player) sender;
        String name = args[0].toLowerCase();

        if (Warp.get(name) != null){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cWarp o takiej nazwie juz istnieje."));
            return true;
        }

        new Warp(name, player.getLocation()).save();
        ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aWarp utworzony (" + name + ")"));

        return true;
    }
}
