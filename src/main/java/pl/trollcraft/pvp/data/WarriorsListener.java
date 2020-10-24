package pl.trollcraft.pvp.data;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.pvp.boosters.Booster;
import pl.trollcraft.pvp.death.DeathEvent;
import pl.trollcraft.pvp.death.KillsManager;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.help.ChatUtils;

public class WarriorsListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        WarriorsManager.load(event.getPlayer());
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Warrior warrior = WarriorsManager.get(event.getPlayer());
        if (warrior == null) return;
        //WarriorsManager.save(warrior);
        WarriorsManager.unregister(warrior);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill (DeathEvent event) {

        Player killer = event.getKiller();
        Player victim = event.getVictim();

        if (killer == null) {

            killer = KillsManager.getLastDamager(victim);

            if (killer == null){
                Warrior victimWarrior = WarriorsManager.get(victim);
                victimWarrior.addDeath();
                return;
            }

        }

        KillsManager.clear(victim);

        Warrior killerWarrior = WarriorsManager.get(killer);
        Warrior victimWarrior = WarriorsManager.get(victim);

        if (killer.getAddress().getHostString().equals(victim.getAddress().getHostString())) return;

        killerWarrior.addKill();
        victimWarrior.addDeath();

        double rew = 10d;
        if (killer.hasPermission("pvp.svip")) rew = 20d;
        else if (killer.hasPermission("pvp.vip")) rew = 15d;

        double bonus = Booster.getBonus(killer);
        if (bonus != 1) {
            double rewBonus = rew * bonus;
            EconomyManager.get(killer).give(rewBonus);
            killer.sendMessage(ChatUtils.fixColor("&a&l" + victim.getName() + " +" + rew + " * " + bonus + " = &e&l"  + rewBonus + " TC"));
        }
        else {
            EconomyManager.get(killer).give(rew);
            killer.sendMessage(ChatUtils.fixColor("&a&l" + victim.getName() + " +" + rew + "TC"));
        }

        if (killerWarrior.tryPromote())
            killer.sendTitle(ChatUtils.fixColor("&a&lAwans!"), ChatUtils.fixColor("&aWitaj na &epoziomie " + killerWarrior.getLevel()));
    }

}
