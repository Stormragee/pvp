package pl.trollcraft.pvp.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.pvp.kits.kituser.KitUserManager;

public class KitListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!KitUserManager.load(player)){

            if (player.hasPermission("pvp.svip"))
                KitsManager.getSvipDefault().give(player, true);
            else if (player.hasPermission("pvp.vip"))
                KitsManager.getVipDefault().give(player, true);
            else
                KitsManager.getPlayerDefault().give(player, true);

        }
    }

}
