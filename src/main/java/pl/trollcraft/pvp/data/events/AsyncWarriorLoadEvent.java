package pl.trollcraft.pvp.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.data.Warrior;

public final class AsyncWarriorLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Warrior warrior;

    public AsyncWarriorLoadEvent(Warrior warrior) {
        this.warrior = warrior;
    }

    public Warrior getWarrior() {
        return warrior;
    }
}
