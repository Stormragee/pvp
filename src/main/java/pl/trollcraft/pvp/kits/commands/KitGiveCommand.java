package pl.trollcraft.pvp.kits.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.kits.Kit;
import pl.trollcraft.pvp.kits.KitsManager;

public class KitGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length != 2) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/kitgive <kit> <gracz>"));
            return true;
        }

        Kit kit = KitsManager.getKit(args[0]);
        if (kit == null) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKit nie istnieje."));
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null || !player.isOnline()) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cGracz nie jest online."));
            return true;
        }

        kit.give(player, false);
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aOtrzymales kit &e" + kit.getName()));

        return true;
    }
}
