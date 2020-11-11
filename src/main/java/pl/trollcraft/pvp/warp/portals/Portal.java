package pl.trollcraft.pvp.warp.portals;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.warp.Warp;

public class Portal {

    private String name;
    private Warp warp;
    private Location a;
    private Location b;

    public Portal(String name, Warp warp, Location a, Location b) {
        this.name = name;
        this.warp = warp;
        this.a = a;
        this.b = b;
    }

    public boolean isInPortal(Location location) {
        if (!location.getWorld().getName().equals(a.getWorld().getName()))
            return false;

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        int ax = a.getBlockX();
        int ay = a.getBlockY();
        int az = a.getBlockZ();
        int bx = b.getBlockX();
        int by = b.getBlockY();
        int bz = b.getBlockZ();
        if ( (x >= ax && x <= bx) || (x >= bx && x <= ax) ) {
            if ( (y >= ay && y <= by) || (y >= by && y <= ay) ) {
                if ( (z >= az && z <= bz) || (z >= bz && z <= az) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public void teleport(Player player){
        warp.teleport(player);
    }

    public String getName() { return name; }
    public Warp getWarp() { return warp; }
    public Location getA() { return a; }
    public Location getB() { return b; }

    public void setA(Location a) { this.a = a; }
    public void setB(Location b) { this.b = b; }

}
