package pl.trollcraft.pvp.data.rewards.user;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.data.rewards.Reward;
import pl.trollcraft.pvp.data.rewards.RewardsManager;
import pl.trollcraft.pvp.help.Help;

public class RewardsUserCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        RewardsUser rewardsUser = RewardsUsersManager.find(player);

        if (rewardsUser.getRewards().size() == 0){
            player.sendMessage(Help.color("&cBrak nagrod do odebrania."));
            return true;
        }

        rewardsUser.getRewards().removeIf( lvl -> {

            Reward reward = RewardsManager.find(lvl);
            return reward.reward(player);

        } );

        int rewards = rewardsUser.getRewards().size();
        if (rewards > 0) {
            player.sendMessage(Help.color("&7Masz do odebrania jeszcze &e" + rewards + " nagrod."));
            player.sendMessage(Help.color("&7Aby je odebrac zwolnij miejsce w ekwipunku."));
        }

        return true;
    }
}
