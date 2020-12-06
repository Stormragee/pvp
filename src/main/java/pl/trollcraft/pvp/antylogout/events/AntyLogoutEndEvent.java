package pl.trollcraft.pvp.antylogout.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AntyLogoutEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ---- ---- ---- ----

    private Player player;

    public AntyLogoutEndEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
