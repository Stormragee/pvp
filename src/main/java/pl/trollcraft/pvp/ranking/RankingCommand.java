package pl.trollcraft.pvp.ranking;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.ranking.core.Position;
import pl.trollcraft.pvp.ranking.holoranking.HoloRanking;
import pl.trollcraft.pvp.ranking.holoranking.HoloRankingsManager;
import pl.trollcraft.pvp.ranking.kills.KillsPosition;
import pl.trollcraft.pvp.ranking.kills.KillsRanking;

public class RankingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 1){

            Player player = (Player) sender;

            if (args[0].equalsIgnoreCase("addkill")) {
                PVP.getPlugin().getKillsRanking().get(player).addKill();

                int pos = PVP.getPlugin().getKillsRanking().promoteSwap(player);
                if (pos > -1)
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&aAwansujesz w rankingu na pozycje &e&l" + pos));

            }
            else if (args[0].equalsIgnoreCase("delkill")) {
                PVP.getPlugin().getKillsRanking().get(player).delKill();

                int pos = PVP.getPlugin().getKillsRanking().demoteSwap(player);
                if (pos > -1)
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cSpadasz w rankingu na pozycje &e&l" + pos));

            }
            else if (args[0].equalsIgnoreCase("me")) {

                KillsPosition pos = PVP.getPlugin().getKillsRanking().get(player);

                Position next = pos.getNext();
                Position prev = pos.getPrevious();

                ChatUtils.sendMessage(player, "next: " + next.toString(1));
                ChatUtils.sendMessage(player, "curr: ja " + pos.getKills());
                ChatUtils.sendMessage(player, "prev: " + prev.toString(2));

            }
            return true;
        }

        if (args.length != 2) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/ranking <ranking> <pozycje>"));
            return true;
        }

        Ranking ranking = RankingManager.get(args[0]);
        if (ranking == null) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cRanking nie istnieje."));
            return true;
        }

        int amount = GeneralUtils.parseInt(args[1]);
        if (amount <= 0) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cZla liczba."));
            return true;
        }

        for (String line : ranking.getPositions(amount))
            sender.sendMessage(line);

        return true;
    }
}
