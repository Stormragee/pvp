package pl.trollcraft.pvp.plots;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;

public class PlotInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        PlotPlayer plotPlayer = PlotPlayer.wrap(player);

        PlotArea plotArea = plotPlayer.getApplicablePlotArea();
        Plot plot = plotArea.getPlot(plotPlayer.getLocation());

        if (plot == null)
            player.sendMessage(Help.color("&cBlad."));

        else {

            player.sendMessage(Help.color("&aDzialka " + plot.getId().x + "x" + plot.getId().y + ":"));
            player.sendMessage("");

            ArrayList<String> players = new ArrayList<>();

            plot.getOwners().forEach( o ->{
                OfflinePlayer op = Bukkit.getOfflinePlayer(o);
                if (op != null) players.add(op.getName());
            } );

            player.sendMessage(Help.color("&aWlasciciele:"));
            if (players.isEmpty()) player.sendMessage(Help.color("  &e-"));
            else players.forEach( p -> player.sendMessage(Help.color("  &e- " + p)) );

            player.sendMessage("");

            plot.getMembers().forEach( o ->{
                OfflinePlayer op = Bukkit.getOfflinePlayer(o);
                if (op != null) players.add(op.getName());
            } );

            player.sendMessage(Help.color("&aCzlonkowie:"));
            if (players.isEmpty()) player.sendMessage(Help.color("  &e-"));
            else players.forEach( p -> player.sendMessage(Help.color("  &e- " + p)) );

            player.sendMessage("");

            plot.getTrusted().forEach( o ->{
                OfflinePlayer op = Bukkit.getOfflinePlayer(o);
                if (op != null) players.add(op.getName());
            } );

            player.sendMessage("");

            player.sendMessage(Help.color("&aZaufani:"));
            if (players.isEmpty()) player.sendMessage(Help.color("  &e-"));
            else players.forEach( p -> player.sendMessage(Help.color("  &e- " + p)) );
        }

        return true;
    }

}
