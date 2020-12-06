package pl.trollcraft.pvp.ranking.economy;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.core.Position;

import java.util.Collections;

public class EconomyRanking extends Ranking {

    public EconomyRanking() {
        super("economy", ChatUtils.fixColor("&aRanking &e&lBOGACZY"));
        prepare();
    }

    @Override
    public void prepare() {
        YamlConfiguration conf = Configs.load("economy.yml");
        conf.getConfigurationSection("economy").getKeys(false).forEach( name -> {
            double money = conf.getDouble("economy." + name + ".money");
            positions.add(new EconomyPosition(name, money));
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

    @Override
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
        position = new EconomyPosition(name, 0);
        insertNew(player, position);
        playersPositions.put(player, position);
        return positions.size();
    }
}
