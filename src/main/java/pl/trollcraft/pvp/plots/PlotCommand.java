package pl.trollcraft.pvp.plots;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;
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
        PlotPlayer plotPlayer = PlotPlayer.wrap(player), target;

        if (args.length == 1){

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if (offlinePlayer == null) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNie ma takiego gracza."));
                return true;
            }

            target =  PlotPlayer.wrap(offlinePlayer);
            for (Plot plot : target.getPlots()){
                new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), GeneralUtils.getHome(plot));
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Teleportacja na dzialke nastapi za &e5 sekund.\n&7Nie ruszaj sie!"));
                return true;
            }

            return true;
        }

        if (plotPlayer.getPlots().size() > 0) {

            for (Plot plot : plotPlayer.getPlots()){
                new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), GeneralUtils.getHome(plot));
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Teleportacja na dzialke nastapi za &e5 sekund.\n&7Nie ruszaj sie!"));
                return true;
            }

        }

        EconomyProfile profile = EconomyManager.get(player);
        if (!profile.take(50000)) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cDzialka kosztuje &e50000TC."));
            return true;
        }

        PlotArea plotArea = plotPlayer.getApplicablePlotArea();
        PlotId start = plotArea.getMeta("lastPlot", new PlotId(0, 0)).getNextId(1);
        Plot plot = plotArea.getNextFreePlot(plotPlayer, start);
        plot.claim(plotPlayer, true, null);

        return true;
    }
}
