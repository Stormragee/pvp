package pl.trollcraft.pvp.rankings.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.rankings.Ranking;

public class RankingUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    // ---- ---- ---- ----

    private final String player;
    private final Ranking ranking;
    private final int newPos;

    public RankingUpdateEvent(String player, Ranking ranking, int newPos) {
        this.player = player;
        this.ranking = ranking;
        this.newPos = newPos;
    }

    public String getPlayer() {
        return player;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public int getNewPos() {
        return newPos;
    }

}
