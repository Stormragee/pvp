package pl.trollcraft.pvp.signshop;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;

import java.util.Random;
import java.util.Set;

public class SignShopCommand implements CommandExecutor {

    private final Random RAND = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy online.");
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eSignShop:\n" +
                    "&e/signshop new <nazwa> &7- tworzy nowy sklep." +
                    "&7Musisz patrzyc sie na tabliczke i trzymac przedmiot w ilosci, jaka ma byc sprzedawana."));
            return true;
        }

        if (args[0].equalsIgnoreCase("new")) {

            if (args.length < 3) {
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/signshop new <cena> <nazwa item'u>"));
                return true;
            }

            Player player = (Player) sender;
            Block block = player.getTargetBlock( (Set<Material>) null, 5 );

            if (block == null || block.getType() == Material.AIR) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cTo nie tabliczka."));
                return true;
            }

            Material type = block.getType();

            if (!(type == Material.SIGN || type == Material.WALL_SIGN || type == Material.SIGN_POST)) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cTo nie tabliczka."));
                return true;
            }

            Sign sign = (Sign) block.getState();

            ItemStack itemStack = player.getItemInHand();
            if (itemStack == null || itemStack.getType() == Material.AIR){
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cZly przedmiot."));
                return true;
            }

            double price = Double.parseDouble(args[1]);

            StringBuilder name = new StringBuilder();
            for (int i = 2 ; i < args.length ; i++){
                name.append(args[i]);
                name.append(' ');
            }

            SignShop signShop = new SignShop(RAND.nextInt(100000), sign, itemStack, name.toString(), price);
            SignShopsManager.register(signShop);
            signShop.updateSign();
            SignShopsManager.save(signShop);

            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aUtworzono sklep."));
            return true;
        }

        else if (args[0].equalsIgnoreCase("remove")) {

            Player player = (Player) sender;
            Block block = player.getTargetBlock( (Set<Material>) null, 5 );

            if (block == null || block.getType() == Material.AIR) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cTo nie tabliczka."));
                return true;
            }

            Material type = block.getType();

            if (!(type == Material.SIGN || type == Material.WALL_SIGN || type == Material.SIGN_POST)) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cTo nie tabliczka."));
                return true;
            }

            SignShop signShop = SignShopsManager.get(block.getLocation());

            if (signShop == null){
                player.sendMessage(Help.color("&cBrak sklepu."));
                return true;
            }

            SignShopsManager.remove(signShop);

        }

        return true;
    }
}
