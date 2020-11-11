package pl.trollcraft.pvp.ranking;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.scoreboard.ScoreboardHandler;

public class RankingListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int killsPos = PVP.getPlugin().getKillsRanking().load(player);
        int ecoPos = PVP.getPlugin().getEconomyRanking().load(player);
        int killStreakPos = PVP.getPlugin().getKillStreakRanking().load(player);

        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aTwoj pozycja w rankingu zabojcow to &e&l" + killsPos + " miejsce."));
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aTwoj pozycja w rankingu bogaczy to &e&l" + ecoPos + " miejsce."));
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aTwoj pozycja w rankingu serii zabojstw to &e&l" + killStreakPos + " miejsce."));
    }

    @EventHandler
    public void onRankingUpdate(RankingUpdateEvent event) {
        Player player = event.getPlayer();
        ScoreboardHandler.update(player);
    }

}
