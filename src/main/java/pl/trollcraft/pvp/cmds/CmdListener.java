package pl.trollcraft.pvp.cmds;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.trollcraft.pvp.help.ChatUtils;

public class CmdListener implements Listener {

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        if (player.hasPermission(CmdManager.getBypassPermission())) return;

        String cmd = event.getMessage();
        if (CmdManager.contains(cmd)) {
            event.setCancelled(true);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cKomenda zablokowana."));
        }

    }

}
