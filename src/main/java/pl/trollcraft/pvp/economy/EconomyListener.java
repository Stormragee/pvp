package pl.trollcraft.pvp.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EconomyListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EconomyManager.load(player);
    }

    @EventHandler
    public void onLeave (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        EconomyProfile profile = EconomyManager.get(player);
        if (profile == null) return;
        EconomyManager.unregister(profile);
    }

}
