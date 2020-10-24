package pl.trollcraft.pvp.kits.kituser;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.kits.Kit;

import java.util.HashMap;

public class KitUser {

    private Player player;
    private HashMap<Kit, Long> kits;

    public KitUser (Player player, HashMap<Kit, Long> kits) {
        this.player = player;
        this.kits = kits;
    }

    public Player getPlayer() { return player; }
    public HashMap<Kit, Long> getKits() { return kits; }

    public long getCooldown(Kit kit) {
        if (kits.containsKey(kit)) return kits.get(kit) - System.currentTimeMillis();
        else return 0;
    }

    public void addCooldown(Kit kit) {
        if (kits.containsKey(kit)) kits.replace(kit, System.currentTimeMillis() + kit.getCooldown());
        else kits.put(kit, System.currentTimeMillis() + kit.getCooldown());
    }

}
