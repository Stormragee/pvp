package pl.trollcraft.pvp.death;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.rankings.event.RankingRefreshEvent;
import pl.trollcraft.pvp.rankings.kills.KillsRanking;

import java.util.logging.Level;

public class DeathEvent extends Event {

    private static final KillsRanking killsRanking =
            (KillsRanking) PVP.getPlugin().getRankingsManager().getRanking("kills").get();

    private final Player victim;
    private final Player killer;

    public DeathEvent (Player victim, Player killer) {
        this.victim = victim;
        this.killer = killer;

        if (killer != null){

            killsRanking.get(killer.getName()).ifPresent(dynPos -> {
                int v = (int) dynPos.getPosition().val();

                Bukkit.getLogger().log(Level.INFO, "Tried to promote.");

                dynPos.getPosition().set(v + 1);

                RankingRefreshEvent event = new RankingRefreshEvent(killer.getName(), killsRanking);
                Bukkit.getPluginManager().callEvent(event);

                killsRanking.tryPromote(dynPos.getPosition());
            } );

        }
    }

    public Player getVictim() { return victim; }
    public Player getKiller() { return killer; }

    // ---- ---- ---- ----

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

}
