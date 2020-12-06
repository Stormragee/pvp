package pl.trollcraft.pvp.rankings.holoranking;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.rankings.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class HoloRankingsManager {

    private static final long INTERVAL = 20 * 60 * 5;

    private static final List<HoloRanking> holoRankings = new ArrayList<>();

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

            Optional<Ranking> rankingToBe = PVP.getPlugin().getRankingsManager().getRanking(conf.getString("holorankings." + id + ".ranking"));
            if (!rankingToBe.isPresent()) {
                Bukkit.getLogger().log(Level.SEVERE, "Cannot load ranking");
                return;
            }

            int positions = conf.getInt("holorankings." + id + ".positions");
            register(new HoloRanking(Integer.parseInt(id), location, rankingToBe.get(), positions));
        } );

        new BukkitRunnable() {

            @Override
            public void run() {
                holoRankings.forEach(HoloRanking::update);
            }

        }.runTaskTimer(PVP.getPlugin(), INTERVAL, INTERVAL);
    }

    public static List<HoloRanking> getHoloRankings() { return holoRankings; }

    public static void delete (HoloRanking holoRanking) {
        holoRankings.remove(holoRanking);
        holoRanking.delete();
        YamlConfiguration conf = Configs.load("holorankings.yml");
        conf.set("holorankings." + holoRanking.getId(), null);
        Configs.save(conf, "holorankings.yml");
    }

}
