package pl.trollcraft.pvp.incognito;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.pvp.death.DeathEvent;

public class IncognitoListener implements Listener {

    @EventHandler
    public void onDeath (DeathEvent event){
        Player player = event.getVictim();
        if (Incognito.isIncognito(player))
            Incognito.randomize(player);
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (Incognito.isIncognito(player))
            Incognito.randomize(player);
    }

}
