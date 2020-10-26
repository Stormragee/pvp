package pl.trollcraft.pvp.help.flying;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.Help;

/**
 * Listens for players who are
 * flying without permission.
 */
public class FlyingListener {

    public static void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach( player -> {

                    if (FlyCommand.isFlying(player) || player.getGameMode() == GameMode.CREATIVE) return;

                    if (player.isFlying()) {
                        player.setFlying(false);
                        player.sendMessage(Help.color("&cNie wolno latac."));
                    }

                } );

            }

        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
