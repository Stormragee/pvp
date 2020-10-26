package pl.trollcraft.pvp.data.rewards;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.data.rewards.types.RewardType;
import pl.trollcraft.pvp.data.rewards.types.RewardsLoader;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.logging.Level;

public class RewardsManager {

    private static ArrayList<Reward> rewards = new ArrayList<>();

    public static void register(Reward reward) {
        rewards.add(reward);
    }

    public static Reward find(int level) {
        return rewards.stream()
                .filter( rew -> rew.getLevel() == level )
                .findAny()
                .orElse(null);
    }

    public static void load() {
        rewards = new ArrayList<>();

        YamlConfiguration conf = Configs.load("rewards.yml");
        assert conf != null;

        conf.getConfigurationSection("rewards").getKeys(false).forEach(lvlStr -> {
            int level = Integer.parseInt(lvlStr);
            String typeName = conf.getString("rewards." + lvlStr + ".type");
            RewardType type = RewardsLoader.load(typeName, conf,"rewards." + lvlStr + ".data");
            register(new Reward(level, type));

            Bukkit.getLogger().log(Level.INFO, "Loaded reward for level " + level);
        } );

    }

}
