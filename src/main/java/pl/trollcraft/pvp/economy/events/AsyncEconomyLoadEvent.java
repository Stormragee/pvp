package pl.trollcraft.pvp.economy.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.economy.EconomyProfile;

public class AsyncEconomyLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private EconomyProfile economyProfile;

    public AsyncEconomyLoadEvent(EconomyProfile economyProfile) {
        this.economyProfile = economyProfile;
    }

    public EconomyProfile getEconomyProfile() {
        return economyProfile;
    }
}
