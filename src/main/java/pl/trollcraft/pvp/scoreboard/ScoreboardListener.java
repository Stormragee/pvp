package pl.trollcraft.pvp.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.data.events.AsyncWarriorLoadEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onJoin (final AsyncWarriorLoadEvent event) {

        new BukkitRunnable() {

            @Override
            public void run() {
                Player player = event.getWarrior().getPlayer();
                ScoreboardHandler.setScoreboard(player);

            }

        }.runTask(PVP.getPlugin());

    }

}
