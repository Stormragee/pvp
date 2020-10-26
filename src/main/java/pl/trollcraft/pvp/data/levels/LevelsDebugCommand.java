package pl.trollcraft.pvp.data.levels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.data.Warrior;
import pl.trollcraft.pvp.data.WarriorsManager;

public class LevelsDebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("pvp.admin"))
            return true;

        Player player = (Player) sender;
        Warrior warrior = WarriorsManager.get(player);
        assert warrior != null;

        if (args.length == 0) {
            warrior.forcePromote();
            player.sendMessage("Promoted");
        }
        else if (args[0].equalsIgnoreCase("reset")) {
            warrior.resetLevel();
            player.sendMessage("Lvl reset");
        }

        return true;
    }
}
