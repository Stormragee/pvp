package pl.trollcraft.pvp.clans.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.clans.Clan;

public class ClanLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ---- ---- ---- ----

    private Clan clan;

    public ClanLevelUpEvent(Clan clan) {
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

}
