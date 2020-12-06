package pl.trollcraft.pvp.rankings.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.rankings.DynamicPosition;
import pl.trollcraft.pvp.rankings.Position;
import pl.trollcraft.pvp.rankings.Ranking;
import pl.trollcraft.pvp.rankings.RankingsManager;

import java.util.List;
import java.util.Optional;

public class RankingDebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("pvp.admin")) {
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Help.color("&7/nranking <show|set> <ranking> (data)"));
            return true;
        }

        if (args[0].equalsIgnoreCase("show")) {

            if (args.length != 3) {
                sender.sendMessage(Help.color("&eUzycie: &7/nranking show <ranking> <pozycje>"));
                return true;
            }

            RankingsManager rankingsManager = PVP.getPlugin().getRankingsManager();
            Optional<Ranking> ranking =  rankingsManager.getRanking(args[1]);

            if (ranking.isPresent()) {

                int posAm = Integer.parseInt(args[2]);
                List<Position> positions = ranking.get().getPositions();

                for (int i = 0 ; i < posAm ; i++)
                    sender.sendMessage(positions.get(i).desc());

            }

        }
        else if (args[0].equalsIgnoreCase("set")) {

            if (args.length != 5) {
                sender.sendMessage(Help.color("&eUzycie: &7/nranking set <ranking> <gracz> <wartosc> <p/d>"));
                return true;
            }

            RankingsManager rankingsManager = PVP.getPlugin().getRankingsManager();

            Optional<Ranking> rankingOpt = rankingsManager.getRanking(args[1]);

            if (rankingOpt.isPresent()) {

                Ranking ranking = rankingOpt.get();
                Optional<DynamicPosition> posOpt = ranking.get(args[2]);

                if (posOpt.isPresent()) {

                    DynamicPosition pos = posOpt.get();

                    sender.sendMessage(Help.color("&aAktualna pozycja: &e" + pos.getIndex()));
                    pos.getPosition().set(Integer.parseInt(args[3]));

                    if (args[4].equalsIgnoreCase("p"))
                        ranking.tryPromote(pos.getPosition());
                    else
                        ranking.tryDemote(pos.getPosition());

                    posOpt = ranking.get(args[2]);
                    sender.sendMessage(Help.color("&aAktualna pozycja: &e" + posOpt.get().getIndex()));

                }


            }

        }

        return true;
    }
}
