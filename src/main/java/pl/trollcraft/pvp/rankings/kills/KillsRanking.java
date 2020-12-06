package pl.trollcraft.pvp.rankings.kills;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.rankings.Ranking;

import java.util.Collections;

/**
 * Ranking of the points of the players.
 */
public class KillsRanking extends Ranking {

    /**
     * The name of the ranking.
     */
    private static final String NAME = "kills";

    /**
     * The title of the ranking.
     */
    private static final String TITLE = "&cRanking &eZABOJCOW";

    /**
     * Basic ranking constructor.
     */
    public KillsRanking() {
        super(NAME, TITLE);
    }

    /**
     * Defines executes the first sorting of
     * the positions of players in the
     * positions map.
     */
    @Override
    public void calculate() {

        YamlConfiguration conf = Configs.load("warriors.yml");
        assert conf != null;

        ConfigurationSection sec = conf.getConfigurationSection("warriors");
        sec.getKeys(false).forEach( name -> {

            KillsPosition pos;
            int points;

            if (conf.contains(String.format("warriors.%s.killPoints", name)))
                points = conf.getInt(String.format("warriors.%s.killPoints", name));
            else
                points = 2 * conf.getInt(String.format("warriors.%s.kills", name));

            pos = new KillsPosition(name, points);
            getPositions().add(pos);

        } );

        Collections.sort(getPositions());

    }

}
