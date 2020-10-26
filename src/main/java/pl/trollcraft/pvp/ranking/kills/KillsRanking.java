package pl.trollcraft.pvp.ranking.kills;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.core.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KillsRanking extends Ranking {

    public KillsRanking() {
        super("kills", ChatUtils.fixColor("&cRanking &e&lZABOJCOW"));
        prepare();
    }

    @Override
    public void prepare() {
        YamlConfiguration conf = Configs.load("warriors.yml");
        conf.getConfigurationSection("warriors").getKeys(false).forEach( name -> {
            int kills = conf.getInt("warriors." + name + ".kills");
            positions.add(new KillsPosition(name, kills));
        } );

        Collections.sort(positions);

        Position position;
        for (int i = 1 ; i < positions.size() - 1 ; i++){
            position = positions.get(i);
            position.setPrevious(positions.get(i+1));
            position.setNext(positions.get(i-1));
        }

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
        position = new KillsPosition(name, 0);
        insertNew(player, position);
        playersPositions.put(player, position);
        return positions.size();
    }

    public KillsPosition get(Player player) {
        if (playersPositions.containsKey(player)) return (KillsPosition) playersPositions.get(player);
        return null;
    }

}
