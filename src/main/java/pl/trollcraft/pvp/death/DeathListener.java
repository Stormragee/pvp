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
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
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

    @EventHandler
    public void onDamage (EntityDamageEvent event) {
        if (event.isCancelled()) return;

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

    private boolean death(Player player, double finalDamage) {
        if (player.getHealth() - finalDamage <= 0) {

            ScoreboardHandler.update(player);

            player.closeInventory();
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            drop(player);
            player.setFireTicks(1);
            player.setExp(0);
            player.setLevel(0);

            for(PotionEffect effect:player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());

            player.teleport(player.getLocation().getWorld().getSpawnLocation());
            giveKit(player);

            return true;

        }
        else return false;
    }

    private void drop(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (itemStack.getType().name().contains("IRON")) continue;
            if (itemStack.getType() == Material.ARROW) continue;
            world.dropItem(loc, itemStack);
        }
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (itemStack.getType().name().contains("IRON")) continue;
            world.dropItem(loc, itemStack);
        }
        player.getInventory().clear();
    }

    private void giveKit(Player player) {
        if (player.hasPermission("pvp.svip"))
            KitsManager.getSvipDefault().give(player, true);
        else if (player.hasPermission("pvp.vip"))
            KitsManager.getVipDefault().give(player, true);
        else
            KitsManager.getPlayerDefault().give(player, true);
    }

}
