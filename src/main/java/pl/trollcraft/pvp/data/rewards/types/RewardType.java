package pl.trollcraft.pvp.data.rewards.types;

import org.bukkit.entity.Player;

public interface RewardType {

    String name();

    void message(Player player);

    /**
     * Gives the reward provided
     * the player is able to get it.
     *
     * @param player to reward.
     * @return if reward was given.
     */
    boolean reward(Player player);

}
