package pl.trollcraft.pvp.data.rewards.types;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.Help;

public class MoneyReward implements RewardType {

    private double money;

    public MoneyReward(double money) {
        this.money = money;
    }

    @Override
    public String name() {
        return "money";
    }

    @Override
    public void message(Player player) {
        player.sendMessage(Help.color("&aNagrodzono Cie suma &e" + money + "TC."));
    }

    @Override
    public void reward(Player player) {
        EconomyProfile profile = EconomyManager.get(player);
        assert profile != null;
        profile.give(money);
    }
}
