package pl.trollcraft.pvp.help.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.function.Consumer;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick (InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        GUI gui = GUI.getOpened(player);

        if (gui == null)
            return;

        Consumer<InventoryClickEvent> click = gui.getClick(event.getSlot());

        if (click != null)
            click.accept(event);
    }

    @EventHandler
    public void onClose (InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        GUI gui = GUI.getOpened(player);

        if (gui != null && gui.hasAutoClose()) gui.close(player);
    }

}
