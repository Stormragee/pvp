package pl.trollcraft.pvp.ranking;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankingUpdateEvent extends Event {

    private Player player;
    private Ranking ranking;

    public RankingUpdateEvent (Player player, Ranking ranking) {
        this.player = player;
        this.ranking = ranking;
    }

    public Player getPlayer() { return player; }

    public Ranking getRanking() { return ranking; }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

}
