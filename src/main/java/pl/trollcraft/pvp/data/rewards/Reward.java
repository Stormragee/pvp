package pl.trollcraft.pvp.data.rewards;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.data.rewards.types.RewardType;

public class Reward {

    /**
     * Level when warrior should be
     * rewarded.
     */
    private int level;

    /**
     * Type of reward, which should be given.
     */
    private RewardType type;

    public Reward(int level, RewardType type) {
        this.level = level;
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Gets the type of the reward.
     *
     * @return type of the reward.
     */
    public RewardType getType() {
        return type;
    }

    /**
     * Takes care of rewarding process.
     *
     * @param player player to reward.
     */
    public boolean reward(Player player) {
        if (type.reward(player)) {
            type.message(player);
            return true;
        }
        return false;
    }

}
