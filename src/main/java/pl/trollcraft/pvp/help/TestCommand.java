package pl.trollcraft.pvp.help;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.auctions.tasks.OfflineItemsGiver;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;

        player.kickPlayer("Wypierdalaj");

        OfflineItemsGiver giver = new OfflineItemsGiver(player.getName(), new ItemStack(Material.DIAMOND, 32));
        giver.save();

        return true;
    }
}
