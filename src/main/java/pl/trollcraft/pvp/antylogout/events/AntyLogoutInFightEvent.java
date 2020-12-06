package pl.trollcraft.pvp.antylogout.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AntyLogoutInFightEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ---- ---- ---- ----

    private Player player;
    private long millis;

    public AntyLogoutInFightEvent(Player player, long millis) {
        this.player = player;
        this.millis = millis;
    }

    public Player getPlayer() {
        return player;
    }

    public long getMillis() {
        return millis;
    }
}
