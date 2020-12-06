package pl.trollcraft.pvp.ranking.killstreak;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.core.Position;
import pl.trollcraft.pvp.ranking.kills.KillsPosition;

import java.util.Collections;

public class KillStreakRanking extends Ranking {

    public KillStreakRanking() {
        super("killstreak", ChatUtils.fixColor("&cRanking &e&lSERII ZABOJSTW"));
        prepare();
    }

    @Override
    public void prepare() {
        YamlConfiguration conf = Configs.load("warriors.yml");
        assert conf != null;

        conf.getConfigurationSection("warriors").getKeys(false).forEach( name -> {

            int killStreak;
            if (conf.contains("warriors." + name + ".killStreak"))
                killStreak = conf.getInt("warriors." + name + ".killStreak");
            else
                killStreak = 0;

            positions.add(new KillStreakPosition(name, killStreak));
        } );

        Collections.sort(positions);

        Position position;
        for (int i = 1 ; i < positions.size() - 1 ; i++){
            position = positions.get(i);
            position.setPrevious(positions.get(i+1));
            position.setNext(positions.get(i-1));
        }
        
        if (positions.isEmpty())
            return;

        positions.get(0).setPrevious(positions.get(1));
        positions.get(positions.size()-1).setNext(positions.get(positions.size()-2));
    }

    public int load(Player player) {
        String name = player.getName();
        Position position;
        for (int i = 0 ; i < positions.size() ; i++) {
            position = positions.get(i);
            if (position.getName().equals(name)) {
                playersPositions.put(player, position);
                return i+1;
            }
        }
        position = new KillStreakPosition(name, 0);
        insertNew(player, position);
        playersPositions.put(player, position);
        return positions.size();
    }

    public KillStreakPosition get(Player player) {
        if (playersPositions.containsKey(player)) return (KillStreakPosition) playersPositions.get(player);
        return null;
    }

}
