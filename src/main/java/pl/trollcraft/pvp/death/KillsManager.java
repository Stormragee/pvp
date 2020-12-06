package pl.trollcraft.pvp.death;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;

import java.util.HashMap;

public class KillsManager {

    private static final long INTERVAL = 20 * 60 * 5;

    private final static HashMap<Player, Player> lastDamagers = new HashMap<>();

    public static Player getLastDamager(Player victim) {
        if (lastDamagers.containsKey(victim)) return lastDamagers.get(victim);
        return null;
    }

    public static void registerDamage(Player victim, Player damager) {
        if (lastDamagers.containsKey(victim)) lastDamagers.replace(victim, damager);
        else lastDamagers.put(victim, damager);
    }

    public static void clear(Player victim) {
        lastDamagers.remove(victim);
    }

    public static void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {
                lastDamagers.clear();
            }

        }.runTaskTimer(PVP.getPlugin(), INTERVAL, INTERVAL);

    }

}
