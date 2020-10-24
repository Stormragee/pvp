package pl.trollcraft.pvp.warp.portals;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;

public class PortalsHandler {

    public static void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach( player -> {
                    Location loc = player.getLocation();
                    Portal portal = PortalsManager.getPortal(loc);
                    if (portal != null) portal.teleport(player);
                } );

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
