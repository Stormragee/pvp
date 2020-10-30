package pl.trollcraft.pvp.incognito;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.death.DeathEvent;
import pl.trollcraft.pvp.help.Help;

public class IncognitoListener implements Listener {

    @EventHandler
    public void onDeath (DeathEvent event){
        Player player = event.getVictim();
        if (Incognito.isIncognito(player))
            Incognito.on(player);
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (Incognito.isIncognito(player))

            new BukkitRunnable() {

                @Override
                public void run() {
                    Incognito.randomize(player);
                }

            }.runTaskLater(PVP.getPlugin(), 5);


    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Incognito.isIncognito(player))
            Incognito.reset(player);
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (Incognito.isIncognito(player)) {
            event.setCancelled(true);
            player.sendMessage(Help.color("&cW trybie incognito nie mozesz pisac na chat'cie."));
        }
    }

}
