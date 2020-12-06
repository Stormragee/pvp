package pl.trollcraft.pvp.data.rewards.user;

import java.util.List;

public class RewardsUser {

    private String playerName;
    private List<Integer> rewards;

    // ---- ---- ---- ----

    public RewardsUser(String playerName, List<Integer> rewards) {
        this.playerName = playerName;
        this.rewards = rewards;
    }

    // ---- ---- ---- ----

    public String getPlayerName() {
        return playerName;
    }

    public List<Integer> getRewards() {
        return rewards;
    }
}
