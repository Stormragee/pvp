package pl.trollcraft.pvp.ranking.holoranking;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.pvp.ranking.RankingUpdateEvent;

public class HoloRankingListener implements Listener {

    @EventHandler
    public void onRankingUpdate (RankingUpdateEvent event) {
        String name = event.getRanking().getName();
        for (HoloRanking holoRanking : HoloRankingsManager.getHoloRankings()){
            if (holoRanking.getRanking().getName().equals(name)) holoRanking.update();
        }
    }

}
