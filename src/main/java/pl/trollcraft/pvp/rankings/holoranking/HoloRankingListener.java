package pl.trollcraft.pvp.rankings.holoranking;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.pvp.rankings.event.RankingRefreshEvent;
import pl.trollcraft.pvp.rankings.event.RankingUpdateEvent;

public class HoloRankingListener implements Listener {

    @EventHandler
    public void onRankingUpdate (RankingUpdateEvent event) {
        String name = event.getRanking().getName();
        for (HoloRanking holoRanking : HoloRankingsManager.getHoloRankings()){
            if (holoRanking.getRanking().getName().equals(name)) holoRanking.update();
        }
    }

    @EventHandler
    public void onRankingUpdate (RankingRefreshEvent event) {
        String name = event.getRanking().getName();
        for (HoloRanking holoRanking : HoloRankingsManager.getHoloRankings()){
            if (holoRanking.getRanking().getName().equals(name)) holoRanking.update();
        }
    }

}
