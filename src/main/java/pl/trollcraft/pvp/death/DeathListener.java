package pl.trollcraft.pvp.death;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.dropping.Drop;
import pl.trollcraft.pvp.kits.KitsManager;
import pl.trollcraft.pvp.scoreboard.ScoreboardHandler;

public class DeathListener implements Listener {

    @EventHandler
    public void onKillAttempt (EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (victim.getType() != EntityType.PLAYER) return;

        Player victimPlayer = (Player) victim, damagerPlayer;

        if (damager.getType() == EntityType.PLAYER) {
            damagerPlayer = (Player) damager;

            if (death(victimPlayer, event.getFinalDamage())) {
                event.setCancelled(true);
                victimPlayer.sendTitle(ChatUtils.fixColor("&cUmierasz"),
                        ChatUtils.fixColor("&e" + damagerPlayer.getName() + " &czabija Cie"));

                Bukkit.getServer().getPluginManager().callEvent(
                        new DeathEvent(victimPlayer, damagerPlayer));

                ScoreboardHandler.update(damagerPlayer);
                ScoreboardHandler.update(victimPlayer);
            }
            else KillsManager.registerDamage(victimPlayer, damagerPlayer);

        }
        else if (damager.getType() == EntityType.ARROW) {
            if (damager == null) return;

            damagerPlayer = (Player) ((Arrow)damager).getShooter();

            if (death(victimPlayer, event.getFinalDamage())) {
                event.setCancelled(true);
                victimPlayer.sendTitle(ChatUtils.fixColor("&cUmierasz"),
                        ChatUtils.fixColor("&e" + damagerPlayer.getName() + " &czabija Cie"));

                Bukkit.getServer().getPluginManager().callEvent(
                        new DeathEvent(victimPlayer, damagerPlayer));

                ScoreboardHandler.update(damagerPlayer);
                ScoreboardHandler.update(victimPlayer);
            }
            else KillsManager.registerDamage(victimPlayer, damagerPlayer);

        }

    }

    @EventHandler (ignoreCancelled = true)
    public void onDamage (EntityDamageEvent event) {

        if (event.getEntity().getType() != EntityType.PLAYER) return;

        Player victim = (Player) event.getEntity();

        if (death(victim, event.getFinalDamage())) {

            event.setCancelled(true);
            Player damager = KillsManager.getLastDamager(victim);

            if (damager != null){
                victim.sendTitle(ChatUtils.fixColor("&cUmierasz"),
                        ChatUtils.fixColor("&e" + damager.getName() + " &czabija Cie"));

                Bukkit.getServer().getPluginManager().callEvent(
                        new DeathEvent(victim, damager));
                ScoreboardHandler.update(damager);
                ScoreboardHandler.update(victim);
            }
            else {
                victim.sendTitle(ChatUtils.fixColor("&cUmierasz"),
                        ChatUtils.fixColor("&cSmuteczek ;("));

                Bukkit.getServer().getPluginManager().callEvent(
                        new DeathEvent(victim, null));
                ScoreboardHandler.update(victim);
            }

        }

    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        KillsManager.clear(player);
    }

    public static boolean death(Player player, double finalDamage) {
        if (player.getHealth() - finalDamage <= 0) {

            kill(player);
            return true;

        }
        else return false;
    }

    public static void kill(Player player) {
        ScoreboardHandler.update(player);

        player.closeInventory();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        Drop.drop(player);
        player.setExp(0);
        player.setLevel(0);

        player.setFireTicks(0);
        new BukkitRunnable() {

            @Override
            public void run() {
                player.setFireTicks(0);
            }

        }.runTaskLater(PVP.getPlugin(), 10);

        for(PotionEffect effect:player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        player.teleport(player.getLocation().getWorld().getSpawnLocation());
        giveKit(player);
    }

    private static void giveKit(Player player) {
        if (player.hasPermission("pvp.hunter"))
            KitsManager.getHunterDefault().give(player, true);
        else if (player.hasPermission("pvp.svip"))
            KitsManager.getSvipDefault().give(player, true);
        else if (player.hasPermission("pvp.vip"))
            KitsManager.getVipDefault().give(player, true);
        else
            KitsManager.getPlayerDefault().give(player, true);
    }

}
