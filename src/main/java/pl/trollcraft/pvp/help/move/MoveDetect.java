package pl.trollcraft.pvp.help.move;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.GeneralUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MoveDetect {

    private static boolean listening = false;

    private static HashMap<Player, Integer> movements = new HashMap<>();
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Player> toRemove = new ArrayList<>();

    public static void listen() {

        if (listening) throw new RuntimeException("MoveDetect already listening.");
        listening = true;

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<Player> p = players.iterator();
                Player pl;

                Iterator<Map.Entry<Player, Integer>> en = movements.entrySet().iterator();

                while (en.hasNext()){
                    Map.Entry<Player, Integer> entry = en.next();
                    if (!players.contains(entry.getKey())) en.remove();
                }

                Iterator<Player> play = players.iterator();
                while (play.hasNext()){
                    if (toRemove.contains(play.next()))
                        play.remove();
                }

                toRemove.clear();

                while (p.hasNext()) {

                    pl = p.next();

                    if (movements.containsKey(pl)){

                        int loc = GeneralUtils.calcMove(pl.getLocation());

                        if (movements.get(pl) != loc)
                            Bukkit.getPluginManager().callEvent(new PlayerMoveDetectedEvent(pl));

                        movements.replace(pl, loc);

                    }
                    else movements.put(pl, GeneralUtils.calcMove(pl.getLocation()));

                }

            }

        }.runTaskTimer(PVP.getPlugin(), 5, 5);

    }

    public static void addPlayer(Player p) {
        players.add(p);
    }

    public static void removePlayer(Player p) {
        toRemove.add(p);
    }

}
