package pl.trollcraft.pvp.help;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderchestFixer implements Listener {

    @EventHandler
    public void onEnderchesInteract (PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Material type = event.getClickedBlock().getType();
        if (type == Material.ENDER_CHEST) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            player.openInventory(player.getEnderChest());
        }

    }

}
