package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;

public class UniqueItemsListener implements Listener {

    public UniqueItemsListener() {
        listen();
    }

    private void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach( p -> {

                    ItemStack i = p.getInventory().getItemInHand();
                    if (i != null) {

                        int l = i.getEnchantmentLevel(EnchantRegister.UNIQUE_ENCHANTMENT);
                        if (l > 0)
                            UniqueItemsController.find(l).ifPresent(ui -> ui.affect(p));

                    }

                } );



            }

        }.runTaskTimer(PVP.getPlugin(), 10, 10);
    }


    @EventHandler
    public void onItemHeld (PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        int l = itemStack.getEnchantmentLevel(EnchantRegister.UNIQUE_ENCHANTMENT);
        if (l > 0)
            UniqueItemsController.find(l).ifPresent(ui -> ui.stopAffect(player));

    }

}
