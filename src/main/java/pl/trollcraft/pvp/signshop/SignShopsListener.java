package pl.trollcraft.pvp.signshop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;

public class SignShopsListener implements Listener {

    @EventHandler
    public void onSignInteract (PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        Material type = event.getClickedBlock().getType();

        if (type == Material.AIR) return;
        if (type == Material.SIGN || type == Material.WALL_SIGN || type == Material.SIGN_POST){

            Location location = event.getClickedBlock().getLocation();
            SignShop signShop = SignShopsManager.get(location);
            if (signShop == null) return;

            event.setCancelled(true);

            Player player = event.getPlayer();

            EconomyProfile profile = EconomyManager.get(player);
            double price = signShop.getPrice();

            if (profile.take(price)){
                player.getInventory().addItem(signShop.getItemStack().clone());
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&aZakupiono &e" + signShop.getName() + " x" + signShop.getItemStack().getAmount()));
            }
            else
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak srodkow na zakup."));

        }

    }

}
