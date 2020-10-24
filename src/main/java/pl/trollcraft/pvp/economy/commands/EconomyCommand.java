package pl.trollcraft.pvp.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;

public class EconomyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length != 3) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/economy give/take/set <gracz> <wartosc>"));
            return true;
        }

        EconomyProfile profile = EconomyManager.get(args[1]);
        if (profile == null) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak gracza."));
            return true;
        }

        double money = Double.parseDouble(args[2]);

        if (args[0].equalsIgnoreCase("give")) {
            profile.give(money);
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aPrzekazano srodki graczowi."));
        }
        else if (args[0].equalsIgnoreCase("take")) {
            profile.take(money);
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aOdebrano srodki graczowi."));
        }
        else if (args[0].equalsIgnoreCase("set")) {
            profile.set(money);
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aZmieniono srodki graczowi."));
        }

        return false;
    }
}