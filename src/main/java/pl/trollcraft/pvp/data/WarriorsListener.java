package pl.trollcraft.pvp.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.boosters.Booster;
import pl.trollcraft.pvp.data.events.NewHighestKillStreakEvent;
import pl.trollcraft.pvp.death.DeathEvent;
import pl.trollcraft.pvp.death.KillsManager;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;

import java.util.logging.Level;

public class WarriorsListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        WarriorsManager.load(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onQuit (PlayerQuitEvent event) {
        Warrior warrior = WarriorsManager.get(event.getPlayer());
        if (warrior == null) return;

        //TODO warriors not saving?
        WarriorsManager.save(warrior);

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
                assert victimWarrior != null;
                victimWarrior.addDeath();
                return;
            }

        }

        KillsManager.clear(victim);

        Warrior killerWarrior = WarriorsManager.get(killer);
        Warrior victimWarrior = WarriorsManager.get(victim);

        if (killer.getAddress().getHostString().equals(victim.getAddress().getHostString())) return;

        assert killerWarrior != null;
        assert victimWarrior != null;

        killerWarrior.addKill();
        victimWarrior.addDeath();

        double rew = 10d;
        if (killer.hasPermission("pvp.hunter")) rew = 25d;
        else if (killer.hasPermission("pvp.svip")) rew = 20d;
        else if (killer.hasPermission("pvp.vip")) rew = 15d;

        double bonus = Booster.getBonus(killer);
        if (bonus != 1) {

            double rewBonus = rew * bonus;
            EconomyProfile profile = EconomyManager.get(killer);

            if (profile == null) {
                Bukkit.getLogger().log(Level.INFO, "Killer economy profile is null: " + killer.getName());
            }
            else{
                EconomyManager.get(killer).give(rewBonus);
                killer.sendMessage(ChatUtils.fixColor("&a&l" + victim.getName() + " +" + rew + " * " + bonus + " = &e&l"  + rewBonus + " TC"));
            }

        }
        else {
            EconomyManager.get(killer).give(rew);
            killer.sendMessage(ChatUtils.fixColor("&a&l" + victim.getName() + " +" + rew + "TC"));
        }

        if (killerWarrior.tryPromote())
            killer.sendTitle(ChatUtils.fixColor("&a&lAwans!"), ChatUtils.fixColor("&aWitaj na &epoziomie " + killerWarrior.getLevel()));
    }

    @EventHandler
    public void onNewKillStreak (NewHighestKillStreakEvent event) {

        Warrior warrior = event.getWarrior();
        Player player = warrior.getPlayer();

        player.sendMessage(Help.color("&aNowa najlepsza seria zabojstw! &e&l" + event.getNewHighestKillStreak() + "!"));
        //TODO PVP.getPlugin().getKillStreakRanking().promoteSwap(player);

    }

}
