package pl.trollcraft.pvp.antylogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.Help;

public class AntyLogoutListener implements Listener {

    private AntyLogout antyLogout = AntyLogout.getInstance();

    @EventHandler
    public void onDamage (EntityDamageByEntityEvent event) {

        if (!(event.getDamager().getType() == EntityType.PLAYER && event.getEntity().getType() == EntityType.PLAYER))
            return;

        Player entity = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (antyLogout.inCombat(entity) == AntyLogout.Response.ADDED)
            entity.sendMessage(Help.color("&7UWAGA! Jestes teraz &ew walce. Nie wylogowywuj sie!"));

        if (antyLogout.inCombat(damager) == AntyLogout.Response.ADDED)
            damager.sendMessage(Help.color("&7UWAGA! Jestes teraz &ew walce. Nie wylogowywuj sie!"));



    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        if (antyLogout.logout(player)) {
            Bukkit.getOnlinePlayers().forEach( p -> p.sendMessage(Help.color("&7Gracz " + player.getName() + " wylogowal sie podczas walki!")) );
            player.getInventory().clear();
        }

    }

    public void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                long now = System.currentTimeMillis();
                antyLogout.getCombats().entrySet().removeIf( ent -> {

                    if ( now >= ent.getValue()){
                        ent.getKey().sendMessage(Help.color("&aNie jestes juz w walce - &emozesz sie wylogowac."));
                        return true;
                    }
                    return false;

                });

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
