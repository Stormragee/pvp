package pl.trollcraft.pvp.plots;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.warp.DelayedWarp;
import pl.trollcraft.pvp.warp.Warp;

import java.util.concurrent.TimeUnit;

public class PlotsCommand implements CommandExecutor {

    private final Location PLOTS_LOC = Warp.get("dzialki").getLocation();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), PLOTS_LOC);
        player.sendMessage(Help.color("&7Teleportacja do swiata dzialkowego nastapi &e za 5 sekund."));

        return true;

    }



}
