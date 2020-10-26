package pl.trollcraft.pvp.data;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.Help;

public class ResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla administracji.");
            return true;
        }

        Player player = (Player) sender;

        Warrior warrior = WarriorsManager.get(player);
        assert warrior != null;

        if (!warrior.canReset()) {
            player.sendMessage(Help.color("&cNie mozesz zresetowac zabojstw i smierci."));
            return true;
        }

        EconomyProfile profile = EconomyManager.get(player);
        assert profile != null;

        double price = ConfigData.getResetPrice();

        if (profile.take(price)) {

            warrior.reset();
            player.sendMessage(Help.color("&aZresetowane zabojstwa i zgony."));

        }
        else
            player.sendMessage(Help.color("&cBrak srodkow. Reset kosztuje &e" + price + "TC."));

        return true;
    }
}
