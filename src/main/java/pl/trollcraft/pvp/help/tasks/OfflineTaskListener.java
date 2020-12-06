package pl.trollcraft.pvp.help.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.economy.events.AsyncEconomyLoadEvent;

import java.util.ArrayList;
import java.util.List;

public class OfflineTaskListener implements Listener {

    @EventHandler
    public void onJoin (AsyncEconomyLoadEvent event) {

        EconomyProfile profile = event.getEconomyProfile();
        Player player = profile.getPlayer();

        OfflineTask.getOfflineTasks().removeIf(task -> {

            if (task.getPlayerName().equals(player.getName())) {

                if (task.execute(player)) {
                    task.dispose();
                    return true;
                }
                else
                    task.failure(player);

            }
            return false;

        });


    }

}
