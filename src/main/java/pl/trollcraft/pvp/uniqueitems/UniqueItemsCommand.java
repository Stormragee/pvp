package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import pl.trollcraft.pvp.help.Help;

import java.util.Optional;

public class UniqueItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("new")) {

                if (args.length != 3) {
                    return true;
                }

                Player player = (Player) sender;
                PotionEffectType type = PotionEffectType.getByName(args[1]);

                if (type == null){
                    player.sendMessage(Help.color("&cNiewlasciwy typ!"));
                    return true;
                }

                int ampl = Integer.parseInt(args[2]);
                ItemStack is = player.getInventory().getItemInHand();

                int id = UniqueItemsController.newInstance(type, ampl, is);
                player.sendMessage(Help.color("&aStworzono i zapisano."));
                player.sendMessage(Help.color("&aID przedmiotu: " + id));

            }

            else if (args[0].equalsIgnoreCase("get")) {

                if (args.length != 2) {
                    return true;
                }

                Player player = (Player) sender;
                int id = Integer.parseInt(args[1]);

                Optional<UniqueItem> opt = UniqueItemsController.find(id);

                if (opt.isPresent())
                    opt.get().give(player);

                else
                    player.sendMessage(Help.color("&cBrak przedmiotu."));

            }

        }

        return true;
    }
}
