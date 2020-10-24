package pl.trollcraft.pvp.ranking.holoranking;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.RankingManager;

import java.util.ArrayList;

public class HoloRankingsManager {

    private static final long INTERVAL = 20 * 60 * 5;

    private static ArrayList<HoloRanking> holoRankings = new ArrayList<>();

    public static void register(HoloRanking holoRanking) { holoRankings.add(holoRanking); }

    public static void save(HoloRanking holoRanking) {
        YamlConfiguration conf = Configs.load("holorankings.yml");
        conf.set("holorankings." + holoRanking.getId() + ".ranking", holoRanking.getRanking().getName());
        conf.set("holorankings." + holoRanking.getId() + ".location", ChatUtils.locToString(holoRanking.getLocation()));
        conf.set("holorankings." + holoRanking.getId() + ".positions", holoRanking.getPositions());
        Configs.save(conf, "holorankings.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("holorankings.yml");
        conf.getConfigurationSection("holorankings").getKeys(false).forEach( id -> {
            Location location = ChatUtils.locFromString(conf.getString("holorankings." + id + ".location"));
            Ranking ranking = RankingManager.get(conf.getString("holorankings." + id + ".ranking"));
            int positions = conf.getInt("holorankings." + id + ".positions");
            register(new HoloRanking(Integer.parseInt(id), location, ranking, positions));
        } );

        new BukkitRunnable() {

            @Override
            public void run() {
                holoRankings.forEach( holoRanking -> holoRanking.update() );
            }

        }.runTaskTimer(PVP.getPlugin(), INTERVAL, INTERVAL);
    }

    public static ArrayList<HoloRanking> getHoloRankings() { return holoRankings; }

    public static void delete (HoloRanking holoRanking) {
        holoRankings.remove(holoRanking);
        holoRanking.delete();
        YamlConfiguration conf = Configs.load("holorankings.yml");
        conf.set("holorankings." + holoRanking.getId(), null);
        Configs.save(conf, "holorankings.yml");
    }

}
