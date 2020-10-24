package pl.trollcraft.pvp.auctions.view;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class AuctionViewListener implements Listener {

    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Viewer viewer = AuctionView.getInstance().getViewer(player);

        if (viewer == null) return;

        AuctionView.getInstance().close(viewer);
    }

}
