package pl.trollcraft.pvp.plots;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.warp.DelayedWarp;
import pl.trollcraft.pvp.warp.Warp;

import java.util.concurrent.TimeUnit;

public class PlotsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;

        Warp plots = Warp.get("dzialki");
        new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), plots.getLocation());
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Teleportacja nastapi za &e5 sekund.\n&7Nie ruszaj sie."));

        return true;
    }
}
