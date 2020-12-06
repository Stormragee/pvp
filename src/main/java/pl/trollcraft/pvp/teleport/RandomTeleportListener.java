package pl.trollcraft.pvp.teleport;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.teleport.random.RandomTeleportsManager;

public class RandomTeleportListener {

    public static void listen() {

        final RandomTeleportsManager manager = PVP.getPlugin()
                .getRandomTeleportsManager();

        new BukkitRunnable() {

            @Override
            public void run() {

                manager.getRandomTeleports().forEach( rt -> {

                    rt.addInterval();
                    if (rt.getCurrentInterval() >= rt.getTeleportInterval()) {

                        rt.resetInterval();
                        rt.grabPlayers();
                        rt.teleport();

                    }

                } );

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
