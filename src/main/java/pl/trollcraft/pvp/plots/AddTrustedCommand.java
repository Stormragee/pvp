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

public class AddTrustedCommand implements CommandExecutor {

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

        OfflinePlayer trusted;
        if (args.length == 1){
            trusted = Bukkit.getOfflinePlayer(args[0]);
            if (trusted == null) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak takiego gracza."));
                return true;
            }
        }
        else {
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/zaufaj <gracz>"));
            return true;
        }

        for (Plot plot : plotPlayer.getPlots()){
            plot.addTrusted(trusted.getUniqueId());
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aDodano gracza &e" + trusted.getName() + " &ado zaufanych dzialki\n" +
                    "&7Aby usunac gracza z listy zaufanych uzyj &e/nieufaj <gracz>."));
            return true;
        }

        return true;
    }

}
