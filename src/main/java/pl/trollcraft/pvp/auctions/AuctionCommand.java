package pl.trollcraft.pvp.auctions;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.auctions.view.AuctionView;
import pl.trollcraft.pvp.help.Help;

public class AuctionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            AuctionView.getInstance().show(player, 0);
        }

        else if (args[0].equalsIgnoreCase("debug")) {

            int n = Integer.parseInt(args[1]);

            Auction auction = AuctionManager.getAuction();
            ItemStack itemStack = new ItemStack(Material.BONE, 3);

            while (n > 0) {
                auction.forceAdd(player, itemStack.clone(), 10);
                n--;
            }

        }

        else if (args[0].equalsIgnoreCase("wystaw")) {

            if (args.length != 2) {
                player.sendMessage(Help.color("&eUzycie: &7/aukcja wystaw <cena>\n" +
                        "&7trzymajac w dloni przedmiot, ktory chcesz wystawic."));
                return true;
            }

            double price = Double.parseDouble(args[1]);

            if (price <= 0) {
                player.sendMessage(Help.color("&cZla cena."));
                return true;
            }

            Auction auction = AuctionManager.getAuction();
            if (auction.add(player, player.getItemInHand(), price)) {
                player.setItemInHand(null);
                player.sendMessage(Help.color("&aWystawiono przedmiot na aukcji."));
            }
            else
                player.sendMessage(Help.color("&cMax. mozesz wystawic 5 przedmiotow jednoczenie."));

            return true;
        }

        return true;
    }
}
