package pl.trollcraft.pvp.data.rewards.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.pvp.help.Help;

public class RewardsUserListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RewardsUser rewardsUser = RewardsUsersManager.load(player);

        int rewards = rewardsUser.getRewards().size();
        if (rewards > 0)
            player.sendMessage(Help.color("&aMasz &e" + rewards + " nagrod &ado &eodebrania.\n&aWpisz komende &e/nagrody, &aby je odebrac."));
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RewardsUser rewardsUser = RewardsUsersManager.find(player);
        RewardsUsersManager.save(rewardsUser);
        RewardsUsersManager.unregister(rewardsUser);
    }

}
