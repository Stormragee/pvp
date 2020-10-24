package pl.trollcraft.pvp.clans;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.pvp.death.DeathEvent;

public class ClansListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ClansManager.load(player);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClansManager.unload(player);
    }

    @EventHandler
    public void onDamage (EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.PLAYER) return;
        if (event.getDamager().getType() != EntityType.PLAYER && event.getDamager().getType() != EntityType.ARROW) return;

        Player victim = (Player) event.getEntity();
        Player damager;

        if (event.getDamager().getType() == EntityType.PLAYER)
            damager = (Player) event.getDamager();
        else
            damager = (Player) ((Arrow) event.getDamager()).getShooter();

        if (victim.getEntityId() == damager.getEntityId()) return;
        if (ClansManager.belongToClan(victim, damager)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill (DeathEvent event) {

        Player killer = event.getKiller();
        Player victim = event.getVictim();

        if (killer == null) return;
        if (killer.getAddress().getHostString().equals(victim.getAddress().getHostString())) return;

        Clan kClan = ClansManager.get(killer);
        Clan vClan = ClansManager.get(victim);

        if (kClan != null) {
            kClan.addKill();
            if (kClan.getKills() % 10 == 0) ClansManager.save(kClan);
        }

        if (vClan != null) {
            vClan.addDeath();
            if (vClan.getDeaths() % 10 == 0) ClansManager.save(vClan);
        }

    }

}
