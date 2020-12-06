package pl.trollcraft.pvp.data.rewards;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.pvp.data.events.WarriorLevelUpEvent;
import pl.trollcraft.pvp.data.rewards.user.RewardsUser;
import pl.trollcraft.pvp.data.rewards.user.RewardsUsersManager;
import pl.trollcraft.pvp.help.Help;

public class RewardsListener implements Listener {

    @EventHandler
    public void onLevelUp (WarriorLevelUpEvent event) {

        int lvl = event.getNewLevel();
        Reward rew = RewardsManager.find(lvl);

        if (rew == null) return;

        Player player = event
                .getWarrior()
                .getPlayer();

        RewardsUser rewardsUser = RewardsUsersManager.find(player);
        rewardsUser.getRewards().add(rew.getLevel());

        player.sendMessage(Help.color("&aDostepna jest nagroda &eza awans!"));
        player.sendMessage(Help.color("&aOdbierz ja uzywajac komendy &e/nagrody"));
        player.sendMessage(Help.color("&aW tej chwili czeka na Ciebie &e" + rewardsUser.getRewards().size() + "."));

    }

}
