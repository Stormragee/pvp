package pl.trollcraft.pvp.rankings;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages rankings.
 */
public class RankingsManager {

    /**
     * List of rankings.
     */
    private final ArrayList<Ranking> rankings;

    /**
     * Creates the rankings manager.
     */
    public RankingsManager() {
        this.rankings = new ArrayList<>();
    }

    /**
     * Registers a new ranking.
     *
     * @param ranking - ranking to register.
     */
    public void register(Ranking ranking) {
        ranking.calculate();
        rankings.add(ranking);
    }

    /**
     * Gets the ranking
     *
     * @param name - name of the ranking.
     * @param <T> - type of the ranking
     *
     * @return option of ranking.
     */
    public<T> Optional<T> getRanking(String name) {
        return rankings
                .stream()
                .filter( ranking -> ranking.getName().equals(name))
                .map( ranking -> (T) ranking )
                .findFirst();
    }

    /**
     * Get all rankings.
     *
     * @return all rankings.
     */
    public ArrayList<Ranking> getRankings() {
        return rankings;
    }
}
