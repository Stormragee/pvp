package pl.trollcraft.pvp.help.flying;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private static ArrayList<Player> flying = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        if (!sender.hasPermission("pvp.admin")) {
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        Player player = (Player) sender;

        if (player.isFlying()) {
            flying.remove(player);
            player.setFlying(false);
            player.sendMessage(Help.color("&aWylaczono latanie."));
        }
        else {
            flying.add(player);
            player.setFlying(true);
            player.sendMessage(Help.color("&aWlaczono latanie."));
        }

        return true;
    }

    public static boolean isFlying(Player player) {
        return flying.contains(player);
    }

}
