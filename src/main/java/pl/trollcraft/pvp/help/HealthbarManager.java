package pl.trollcraft.pvp.help;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class HealthbarManager implements Listener {

    private Scoreboard scoreboard;

    public HealthbarManager() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        registerHealthbar();
        registerNametag();
    }

    public void registerHealthbar() {
        if (scoreboard.getObjective("health") != null)
            scoreboard.getObjective("health").unregister();

        Objective objective = scoreboard.registerNewObjective("health", "health");
        objective.setDisplayName(ChatColor.RED + "❤");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public void registerNametag() {
        if (scoreboard.getTeam("blue") != null)
            scoreboard.getTeam("blue").unregister();
        Team t = scoreboard.registerNewTeam("blue");
    }

    /*@EventHandler
    public void onJoin (PlayerJoinEvent event) {
        scoreboard.getTeam("blue").addPlayer(event.getPlayer());
    }*/

}
