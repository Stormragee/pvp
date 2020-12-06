package pl.trollcraft.pvp.teleport.random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTeleport {

    private final World world;

    private final Point min;
    private final Point max;

    private final int minOffset;
    private final int maxOffset;

    private final Location teleportCenter;
    private final int teleportRange;

    private final int teleportInterval;
    private int currentInterval;

    /**
     * Players to teleport
     */
    private final List<Player> players;

    // ---- ---- ---- ----

    public RandomTeleport(World world, Point min, Point max, int minOffset, int maxOffset, Location teleportCenter, int teleportRange, int teleportInterval) {
        this.world = world;
        this.min = min;
        this.max = max;
        this.minOffset = minOffset;
        this.maxOffset = maxOffset;
        this.teleportCenter = teleportCenter;
        this.teleportRange = teleportRange;
        this.teleportInterval = teleportInterval;
        currentInterval = 0;
        players = new ArrayList<>();
    }

    // ---- ---- ---- ----

    public int grabPlayers() {

        players.clear();

        Collection<Entity> e = teleportCenter.getWorld().getNearbyEntities(teleportCenter, teleportRange, teleportRange, teleportRange);
        for (Entity en : e)
            if (en.getType() == EntityType.PLAYER)
                players.add((Player) en);

        return players.size();
    }

    private Location move(int x, int z, ThreadLocalRandom r) {

        int offsetX = r.nextInt(minOffset, maxOffset + 1);
        int offsetZ = r.nextInt(minOffset, maxOffset + 1);

        return world.getHighestBlockAt(x + offsetX, z + offsetZ).getLocation();

    }

    public void teleport() {

        ThreadLocalRandom r = ThreadLocalRandom.current();

        // Center
        int x = r.nextInt(min.getX(), max.getX() + 1);
        int z = r.nextInt(min.getZ(), max.getZ() + 1);

        players.forEach(p -> {

            p.teleport(move(x, z, r));
            p.sendMessage(Help.color("&eTeleportowano na losowe koordynaty wraz z innymi graczami!"));

        });

    }

    // ---- ---- ---- ----

    public void addInterval() {
        currentInterval++;
    }

    public void resetInterval() {
        currentInterval = 0;
    }

    public int getCurrentInterval() {
        return currentInterval;
    }

    public int getTeleportInterval() {
        return teleportInterval;
    }
}
