package pl.trollcraft.pvp.warp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.move.MoveDetect;

import java.util.ArrayList;
import java.util.Iterator;

public class DelayedWarp {

    private static boolean listening = false;
    private static ArrayList<DelayedWarp> delayedWarps = new ArrayList<>();

    private Player player;
    private long time;
    private Location location;

    public DelayedWarp(Player player, long time, Location location) {
        this.player = player;
        this.time = System.currentTimeMillis() + time;
        this.location = location;
        MoveDetect.addPlayer(player);
        delayedWarps.add(this);
    }

    public static void cancel(Player player) {
        Iterator<DelayedWarp> w = delayedWarps.iterator();
        MoveDetect.removePlayer(player);
        while (w.hasNext()){
            if (w.next().player.getEntityId() == player.getEntityId()) w.remove();
        }
    }

    public static boolean hasAwaitingWarp(Player player) {
        for (DelayedWarp d : delayedWarps){
            if (d.player.getEntityId() == player.getEntityId()) return true;
        }
        return false;
    }

    public static void listen() {
        if (listening) throw new RuntimeException("DelayedTask listener already set.");
        listening = false;

        new BukkitRunnable() {
            @Override
            public void run() {

                long now = System.currentTimeMillis();
                Iterator<DelayedWarp> it = delayedWarps.iterator();
                DelayedWarp t;

                while (it.hasNext()){
                    t = it.next();
                    if (now >= t.time){
                        t.player.teleport(t.location);
                        MoveDetect.removePlayer(t.player);
                        it.remove();
                    }
                }

            }
        }.runTaskTimer(PVP.getPlugin(), 20, 20);

    }

}
