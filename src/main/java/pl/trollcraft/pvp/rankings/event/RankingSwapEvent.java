package pl.trollcraft.pvp.rankings.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.rankings.Ranking;

public class RankingSwapEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

    // ---- ---- ---- ----

    private final String player;
    private final Ranking ranking;
    private final int newPos;
    private final SwapType type;

    public RankingSwapEvent(String player, Ranking ranking, int newPos, SwapType type) {
        this.player = player;
        this.ranking = ranking;
        this.newPos = newPos;
        this.type = type;
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

    public SwapType getType() {
        return type;
    }


}
