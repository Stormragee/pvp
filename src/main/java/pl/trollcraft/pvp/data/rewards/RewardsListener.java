package pl.trollcraft.pvp.data.rewards;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.pvp.data.events.WarriorLevelUpEvent;

public class RewardsListener implements Listener {

    @EventHandler
    public void onLevelUp (WarriorLevelUpEvent event) {

        int lvl = event.getNewLevel();
        Reward rew = RewardsManager.find(lvl);

        if (rew == null) return;

        Player player = event
                .getWarrior()
                .getPlayer();

        rew.reward(player);

    }

}
