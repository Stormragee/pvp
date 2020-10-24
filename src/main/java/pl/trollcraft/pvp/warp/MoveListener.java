package pl.trollcraft.pvp.warp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.move.PlayerMoveDetectedEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMoveDetect (PlayerMoveDetectedEvent event) {

        Player player = event.getPlayer();

        if (DelayedWarp.hasAwaitingWarp(player)){
            DelayedWarp.cancel(player);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cPoruszyles sie. Teleportacja anulowana."));
        }

    }

}
