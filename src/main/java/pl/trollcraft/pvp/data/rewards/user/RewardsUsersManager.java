package pl.trollcraft.pvp.data.rewards.user;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.List;

public class RewardsUsersManager {

    private static List<RewardsUser> rewardsUsers = new ArrayList<>();

    public static void register(RewardsUser rewardsUser) {
        rewardsUsers.add(rewardsUser);
    }

    public static void unregister(RewardsUser rewardsUser) {
        rewardsUsers.remove(rewardsUser);
    }

    public static RewardsUser find(Player player) {
        String name = player.getName();
        return rewardsUsers
                .stream()
                .filter(u -> u.getPlayerName().equals(name))
                .findAny()
                .orElse(null);
    }

    public static void save(RewardsUser rewardsUser) {
        YamlConfiguration conf = Configs.load("rewardsusers.yml");
        assert conf != null;
        conf.set("rewardsusers." + rewardsUser.getPlayerName() + ".rewards", rewardsUser.getRewards());
        Configs.save(conf, "rewardsusers.yml");
    }

    public static void save() {
        YamlConfiguration conf = Configs.load("rewardsusers.yml");
        assert conf != null;

        rewardsUsers.forEach( rewardsUser ->
                conf.set("rewardsusers." + rewardsUser.getPlayerName() + ".rewards", rewardsUser.getRewards()) );

        Configs.save(conf, "rewardsusers.yml");
    }

    public static RewardsUser load(Player player) {

        YamlConfiguration conf = Configs.load("rewardsusers.yml");
        assert conf != null;

        if (conf.contains("rewardsusers." + player.getName())){

            List<Integer> rewards = conf.getIntegerList("rewardsusers." + player.getName() + ".rewards");
            RewardsUser rewardsUser = new RewardsUser(player.getName(), rewards);
            register(rewardsUser);
            return rewardsUser;

        }
        else {
            RewardsUser rewardsUser = new RewardsUser(player.getName(), new ArrayList<>());
            register(rewardsUser);
            return rewardsUser;
        }
    }

}
