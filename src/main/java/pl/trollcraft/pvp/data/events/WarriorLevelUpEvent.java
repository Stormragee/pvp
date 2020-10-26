package pl.trollcraft.pvp.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.data.Warrior;

public class WarriorLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ----

    private Warrior warrior;
    private int newLevel;

    public WarriorLevelUpEvent(Warrior warrior, int newLevel) {
        this.warrior = warrior;
        this.newLevel = newLevel;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public int getNewLevel() {
        return newLevel;
    }
}
