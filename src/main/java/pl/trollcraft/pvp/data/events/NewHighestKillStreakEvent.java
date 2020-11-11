package pl.trollcraft.pvp.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.data.Warrior;

public class NewHighestKillStreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    // ----

    private final Warrior warrior;
    private final int newHighestKillStreak;

    public NewHighestKillStreakEvent(Warrior warrior, int newHighestKillStreak) {
        this.warrior = warrior;
        this.newHighestKillStreak = newHighestKillStreak;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public int getNewHighestKillStreak() {
        return newHighestKillStreak;
    }
}
