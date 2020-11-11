package pl.trollcraft.pvp.clans.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.clans.Clan;

public class ClanMembersDamagePreventEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ---- ---- ---- ----

    private Clan clan;
    private Player damager;
    private Player victim;

    // ---- ---- ---- ----

    public ClanMembersDamagePreventEvent(Clan clan, Player damager, Player victim) {
        this.clan = clan;
        this.damager = damager;
        this.victim = victim;
    }

    // ---- ---- ---- ----

    public Clan getClan() {
        return clan;
    }

    public Player getDamager() {
        return damager;
    }

    public Player getVictim() {
        return victim;
    }

    // ---- ---- ---- ----

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
