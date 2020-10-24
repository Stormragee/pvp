package pl.trollcraft.pvp.ranking;

import java.util.ArrayList;

public class RankingManager {

    private static ArrayList<Ranking> rankings = new ArrayList<>();

    public static void register (Ranking ranking) { rankings.add(ranking); }

    public static ArrayList<Ranking> getRankings() { return rankings; }

    public static Ranking get(String name) {
        for (Ranking r : rankings)
            if (r.getName().equals(name)) return r;
        return null;
    }

}
