package pl.trollcraft.pvp.plots;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        PlotPlayer plotPlayer = PlotPlayer.wrap(player);

        if (plotPlayer.getPlotCount() == 0) {
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNie posiadasz zadnej dzialki."));
            return true;
        }

        OfflinePlayer added;
        if (args.length == 1){
            added = Bukkit.getOfflinePlayer(args[0]);
            if (added == null) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak takiego gracza."));
                return true;
            }
        }
        else {
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/dodaj <gracz>"));
            return true;
        }

        for (Plot plot : plotPlayer.getPlots()){
            plot.addMember(added.getUniqueId());
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aDodano gracza &e" + added.getName() + " &ado dzialki\n" +
                    "&7Aby usunac gracza z dzialki uzyj &e/usun <gracz>."));
            return true;
        }

        return true;
    }
}
