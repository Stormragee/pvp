package pl.trollcraft.pvp.death;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.ScoreboardManager;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.ranking.kills.KillsPosition;
import pl.trollcraft.pvp.scoreboard.ScoreboardHandler;

public class DeathEvent extends Event {

    private Player victim;
    private Player killer;

    public DeathEvent (Player victim, Player killer) {
        this.victim = victim;
        this.killer = killer;

        int pos = PVP.getPlugin().getKillsRanking().demoteSwap(victim);
        if (pos > -1)
            ChatUtils.sendMessage(killer, ChatUtils.fixColor("&cSpadasz w rankingu na pozycje &e&l" + pos));

        if (killer != null){

            PVP.getPlugin().getKillsRanking().get(killer).addKill();
            pos = PVP.getPlugin().getKillsRanking().promoteSwap(killer);
            if (pos > -1)
                ChatUtils.sendMessage(killer, ChatUtils.fixColor("&aAwansujesz w rankingu na pozycje &e&l" + pos));

        }
    }

    public Player getVictim() { return victim; }
    public Player getKiller() { return killer; }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; }

}
