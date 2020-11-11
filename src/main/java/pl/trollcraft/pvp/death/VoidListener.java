package pl.trollcraft.pvp.death;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.antylogout.AntyLogoutData;

public class VoidListener {

    public static void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(p -> AntyLogoutData.shouldCheck(p.getWorld()))
                        .forEach(p -> {

                            if (p.getLocation().getBlockY() < 0)
                                DeathListener.kill(p);


                        });

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
