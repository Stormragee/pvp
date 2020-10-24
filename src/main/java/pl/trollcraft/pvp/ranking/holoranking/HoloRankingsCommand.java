package pl.trollcraft.pvp.ranking.holoranking;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.RankingManager;

public class HoloRankingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy.");
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/holoranking new|delete"));
            return true;
        }
        else if (args[0].equalsIgnoreCase("new")) {

            if (args.length != 3) {
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/holoranking new <ranking> <pozycje>"));
                return true;
            }

            Ranking ranking = RankingManager.get(args[1]);
            if (ranking == null) {
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak rankingu."));
                return true;
            }

            int positions = GeneralUtils.parseInt(args[2]);
            if (positions <= 0){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cZla liczba."));
                return true;
            }

            Player player = (Player) sender;
            Location location = player.getLocation();

            HoloRanking holoRanking = new HoloRanking(location, ranking, positions);
            HoloRankingsManager.register(holoRanking);
            HoloRankingsManager.save(holoRanking);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aRanking holograficzny zostal utworzony."));

            return true;

        }

        return true;
    }
}
