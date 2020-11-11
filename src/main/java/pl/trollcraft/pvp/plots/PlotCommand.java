package pl.trollcraft.pvp.plots;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.commands.Auto;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.*;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.TaskManager;
import com.plotsquared.bukkit.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.warp.DelayedWarp;

import java.util.concurrent.TimeUnit;

public class PlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("Komenda jedynie dla graczy."));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            PlotPlayer plotPlayer = PlotPlayer.wrap(offlinePlayer);

            if (plotPlayer == null) {
                player.sendMessage(Help.color("&cBrak gracza."));
                return true;
            }

            if (plotPlayer.getPlots().size() == 0) {
                player.sendMessage(Help.color("&cGracz nie posiada dzialki."));
                return true;
            }

            Location loc = BukkitUtil.getLocation(plotPlayer.getPlots().iterator().next().getCenter());
            new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), loc);
            player.sendMessage(Help.color("&7Teleportacja na dzialke nastapi za &e5 sekund"));

        }
        else {

            PlotPlayer plotPlayer = PlotPlayer.wrap(player);
            int plots = plotPlayer.getPlots().size();

            if (plots >= 1) {

                Location loc = BukkitUtil.getLocation(plotPlayer.getPlots().iterator().next().getCenter());
                new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), loc);
                player.sendMessage(Help.color("&7Teleportacja na dzialke nastapi za &e5 sekund"));

            }

            else {

                EconomyProfile profile = EconomyManager.get(player);
                assert profile != null;

                if (!profile.take(30000)) {
                    player.sendMessage(Help.color("&cBrak srodkow na zakup dzialek."));
                    player.sendMessage(Help.color("&cKoszt dzialki to &e30000TC."));
                    return true;
                }

                PlotId start = PlotId.fromString("-1;0");
                PlotArea area = PS.get().getPlotAreaByString("plotworld");
                Location loc = autoClaim(area, start, plotPlayer);

                player.sendMessage(Help.color("&aZajeto dzialke."));
                new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), loc);
                player.sendMessage(Help.color("&7Teleportacja nastapi za &e5 sekund."));

            }

        }

        return true;
    }

    private Location autoClaim(PlotArea plotArea, PlotId start, PlotPlayer plotPlayer) {
        Plot plot = plotArea.getNextFreePlot(plotPlayer, start);
        plot.claim(plotPlayer, false, null);
        return BukkitUtil.getLocation(plot.getCenter());
    }

}
