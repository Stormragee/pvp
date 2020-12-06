package pl.trollcraft.pvp.rankings;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.pvp.help.Help;

public class RankingListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.sendMessage(Help.color("&cWitamy na &e&lChestPVP"));
        player.sendMessage("");
        player.sendMessage(Help.color("&cAktualnie testujemy nowy ranking."));
        player.sendMessage(Help.color("&cZapraszamy do walki!"));
        player.sendMessage(Help.color("&cPozostale rankingi zostana dodane na dniach."));
        player.sendMessage("");

    }

}
