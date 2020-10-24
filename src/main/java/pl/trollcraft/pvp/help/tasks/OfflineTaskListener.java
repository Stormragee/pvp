package pl.trollcraft.pvp.help.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class OfflineTaskListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        List<OfflineTask> disposal = new ArrayList<>();

        OfflineTask.get(playerName).forEach( task -> {

            if (task.execute(player))
                disposal.add(task);
            else
                task.failure(player);

        } );

        disposal.forEach( OfflineTask::dispose );
    }

}
