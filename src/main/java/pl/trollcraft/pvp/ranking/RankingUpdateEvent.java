package pl.trollcraft.pvp.ranking;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankingUpdateEvent extends Event {

    private Ranking ranking;

    public RankingUpdateEvent (Ranking ranking) {
        this.ranking = ranking;
    }

    public Ranking getRanking() { return ranking; }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

}
