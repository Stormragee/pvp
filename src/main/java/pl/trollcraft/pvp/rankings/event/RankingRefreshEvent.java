package pl.trollcraft.pvp.rankings.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.rankings.Ranking;

public class RankingRefreshEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    // ---- ---- ---- ----

    private final String player;
    private final Ranking ranking;

    public RankingRefreshEvent(String player, Ranking ranking) {
        this.player = player;
        this.ranking = ranking;
    }

    public String getPlayer() {
        return player;
    }

    public Ranking getRanking() {
        return ranking;
    }

}
