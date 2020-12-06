package pl.trollcraft.pvp.antylogout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.antylogout.events.AntyLogoutEndEvent;
import pl.trollcraft.pvp.data.WarriorsManager;
import pl.trollcraft.pvp.death.DeathEvent;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.dropping.Drop;
import pl.trollcraft.pvp.help.packets.WrapperActionBar;

import java.util.Objects;

public class AntyLogoutListener implements Listener {

    private AntyLogout antyLogout = AntyLogout.getInstance();

    @EventHandler(ignoreCancelled = true)
    public void onDamage (EntityDamageByEntityEvent event) {

        Entity victim = event.getEntity();
        Entity damager = event.getDamager();
        handleDamage(victim, damager);

    }

    private void handleDamage(Entity victimEntity, Entity damagerEntity) {

        if (victimEntity.getType() != EntityType.PLAYER)
            return;

        Player entity = (Player) victimEntity;
        Player damager;

        if (damagerEntity.getType() == EntityType.PLAYER)
            damager = (Player) damagerEntity;

        else if (damagerEntity.getType() == EntityType.ARROW)
            damager = (Player) ((Arrow) damagerEntity).getShooter();

        else
            return;

        if (!AntyLogoutData.shouldCheck(damager.getWorld()))
            return;

        if (antyLogout.inCombat(entity) == AntyLogout.Response.ADDED)
            entity.sendMessage(Help.color("&7UWAGA! Jestes teraz &ew walce. Nie wylogowywuj sie!"));

        if (antyLogout.inCombat(damager) == AntyLogout.Response.ADDED)
            damager.sendMessage(Help.color("&7UWAGA! Jestes teraz &ew walce. Nie wylogowywuj sie!"));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onQuit (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        if (antyLogout.logout(player)) {
            Bukkit.getOnlinePlayers().forEach( p -> p.sendMessage(Help.color("&7Gracz " + player.getName() + " wylogowal sie podczas walki!")) );

            Drop.drop(player);

            Objects.requireNonNull(WarriorsManager.get(player)).addDeath();
        }

    }

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (AntyLogout.getInstance().logout(player)) {

            if (AntyLogoutData.commandAccepted(event.getMessage()))
                return;

            event.setCancelled(true);
            player.sendMessage(Help.color("&cPodczas walki nie mozesz uzywac komend."));
        }
    }

    @EventHandler
    public void onDeath (DeathEvent event) {

        Player victim = event.getVictim();

        if (AntyLogout.getInstance().outCombat(victim) == AntyLogout.Response.REMOVED) {
            victim.sendMessage(Help.color("&aNie jestes juz w walce - &emozesz sie wylogowac."));
            Bukkit.getPluginManager().callEvent(new AntyLogoutEndEvent(victim));
        }

    }

    @EventHandler
    public void onAntyLogoutStop (AntyLogoutEndEvent event) {
        Player player = event.getPlayer();

        WrapperActionBar a = new WrapperActionBar();
        a.setText("&a&lMOZESZ SIE WYLOGOWAC!");

        a.sendPacket(player);
    }

    public void listenForMessage() {

        new BukkitRunnable() {

            @Override
            public void run() {

                AntyLogout
                        .getInstance()
                        .getCombats()
                        .forEach( (player, time)  -> {

                            long left = time - System.currentTimeMillis();

                            long sec = (left / 1000) % 60;

                            String s = "";
                            if (sec < 10)
                                s += "0";

                            s += sec;

                            WrapperActionBar a = new WrapperActionBar();
                            a.setText("&c&l► &4&lJestes podczas walki jeszcze przez (&c&lx " + s + ")&4&l! &c&l◄");

                            a.sendPacket(player);

                        } );

            }

        }.runTaskTimerAsynchronously(PVP.getPlugin(), 2, 2);

    }

    public void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                long now = System.currentTimeMillis();
                antyLogout.getCombats().entrySet().removeIf( ent -> {

                    if ( now >= ent.getValue()){
                        ent.getKey().sendMessage(Help.color("&aNie jestes juz w walce - &emozesz sie wylogowac."));
                        Bukkit.getPluginManager().callEvent(new AntyLogoutEndEvent(ent.getKey()));
                        return true;
                    }
                    return false;

                });

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }


}
