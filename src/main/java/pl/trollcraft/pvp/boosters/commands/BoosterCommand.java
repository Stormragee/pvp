package pl.trollcraft.pvp.boosters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.boosters.GlobalBooster;
import pl.trollcraft.pvp.boosters.PlayerBooster;
import pl.trollcraft.pvp.help.ChatUtils;

public class BoosterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jest jedynie dla graczy."));
            return true;
        }

        Player player = (Player) sender;
        PlayerBooster playerBooster = PlayerBooster.getBooster(player);

        ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Aktywne BOOSTER'Y:"));

        if (playerBooster != null)
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Posiadasz &eBOOSTER!\n&7(Bonus &ex" + playerBooster.getBonus() + " &7aktywny jeszcze przez &e" + playerBooster.getSeconds() + " sekund)"));
        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak BOOSTER'A prywatnego."));

        if (!GlobalBooster.getGlobalBoosters().isEmpty())
            for (GlobalBooster booster : GlobalBooster.getGlobalBoosters())
                ChatUtils.sendMessage(player, "&e&l   + &7BOOSTER GLOBALNY &ex" + booster.getBonus() + "&7 na &e" + booster.getSeconds() + " sekund.");
        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak BOOSTER'OW globalnych."));

        return true;
    }

}