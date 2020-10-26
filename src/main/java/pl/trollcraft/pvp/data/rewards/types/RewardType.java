package pl.trollcraft.pvp.data.rewards.types;

import org.bukkit.entity.Player;

public interface RewardType {

    String name();

    void message(Player player);
    void reward(Player player);

}
