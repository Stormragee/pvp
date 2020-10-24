package pl.trollcraft.pvp.help;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;

public class RepairCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack is = ((Player) sender).getItemInHand();

        if (is.getType().name().contains("SWORD") || is.getType().name().contains("HELMET") ||
            is.getType().name().contains("CHESTPLATE") || is.getType().name().contains("LEGGINGS") ||
            is.getType().name().contains("AXE") || is.getType().name().contains("SHOVEL") ||
            is.getType().name().contains("BOW") || is.getType().name().contains("BOOTS")){

            if (is.getDurability() == 0) {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cPrzedmiot jest juz naprawiony."));
                return true;
            }

            EconomyProfile profile = EconomyManager.get(player);
            if (!profile.take(500)){
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cKoszt naprawy to &e500TC."));
                return true;
            }

            is.setDurability( (short) 0 );
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aNaprawiono."));
            return true;

        }
        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNie mozesz tego naprawic."));

        return true;
    }
}
