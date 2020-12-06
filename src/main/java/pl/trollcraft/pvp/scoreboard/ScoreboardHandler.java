package pl.trollcraft.pvp.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.data.Warrior;
import pl.trollcraft.pvp.data.WarriorsManager;
import pl.trollcraft.pvp.rankings.Ranking;

import java.util.logging.Level;

public class ScoreboardHandler {

    public static void setScoreboard(Player player) {

        Warrior warrior = WarriorsManager.get(player);

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective health = board.registerNewObjective("health", "health");
        health.setDisplayName(ChatColor.RED + "❤");
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);

        Objective obj = board.registerNewObjective("§4§lPVP", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score empty1 = obj.getScore(ChatColor.WHITE + "");
        empty1.setScore(15);

        Score killsName = obj.getScore("§7§lZab./Smi.");
        killsName.setScore(14);

        Team kills = board.registerNewTeam("kills");
        kills.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        kills.setPrefix("   §c§l" + warrior.getKills() + "/" + warrior.getDeaths());
        kills.setDisplayName(ChatColor.DARK_GREEN + "        " + ChatColor.RED);
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(13);

        Score empty2 = obj.getScore(ChatColor.BLACK + "");
        empty2.setScore(12);

        Score kdrName = obj.getScore("§7§lKDR");
        kdrName.setScore(11);

        Team kdr = board.registerNewTeam("kdr");
        kdr.addEntry(ChatColor.GREEN + "" + ChatColor.RED);
        kdr.setPrefix("   §c§l" + warrior.getKdrFormatted());
        obj.getScore(ChatColor.GREEN + "" + ChatColor.RED).setScore(10);

        Score empty3 = obj.getScore(ChatColor.RED + "");
        empty3.setScore(9);

        Score killStreakName = obj.getScore("§7§lSeria");
        killStreakName.setScore(8);

        Team killStreak = board.registerNewTeam("killStreak");
        killStreak.addEntry(ChatColor.YELLOW + "" + ChatColor.RED);
        killStreak.setPrefix("   §c§l" + warrior.getKillStreak());
        obj.getScore(ChatColor.YELLOW + "" + ChatColor.RED).setScore(7);

        Score empty4 = obj.getScore(ChatColor.YELLOW + "");
        empty4.setScore(6);

        Score toPromotionName = obj.getScore("§7§lDo awansu");
        toPromotionName.setScore(5);

        Team toPromotion = board.registerNewTeam("toPromotion");
        toPromotion.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.RED);
        toPromotion.setPrefix("   §c§l" + warrior.getKillsToPromotion());
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.RED).setScore(4);

        Score empty5 = obj.getScore(ChatColor.GRAY + "");
        empty5.setScore(3);

        Score rankingName = obj.getScore("§7§lRanking");
        rankingName.setScore(2);

        Ranking r = (Ranking) PVP.getPlugin().getRankingsManager().getRanking("kills").get();
        int ind = r.get(player.getName()).get().getIndex();

        Team ranking = board.registerNewTeam("ranking");
        ranking.addEntry(ChatColor.GRAY + "" + ChatColor.RED);
        ranking.setPrefix("   §c§l" + (ind+1));
        obj.getScore(ChatColor.GRAY + "" + ChatColor.RED).setScore(1);

        player.setScoreboard(board);
    }

    public static void update(Player player) {
        Warrior warrior = WarriorsManager.get(player);
        if (warrior == null) {
            Bukkit.getLogger().log(Level.INFO, player.getName() + " warrior is null!");
            return;
        }

        Scoreboard board = player.getScoreboard();

        board.getTeam("kills").setPrefix("   §c§l" + warrior.getKills() + " / " + warrior.getDeaths());
        board.getTeam("kdr").setPrefix("   §c§l" + warrior.getKdrFormatted());
        board.getTeam("killStreak").setPrefix("   §c§l" + warrior.getKillStreak());
        board.getTeam("toPromotion").setPrefix("   §c§l" + warrior.getKillsToPromotion());

        Ranking r = (Ranking) PVP.getPlugin().getRankingsManager().getRanking("kills").get();
        int ind = r.get(player.getName()).get().getIndex();

        board.getTeam("ranking").setPrefix("   §c§l" + (ind+1));
    }

}
