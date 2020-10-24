package pl.trollcraft.pvp.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy.");
            return true;
        }

        if (args.length != 2) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/pay <gracz> <wartosc>"));
            return true;
        }

        Player player = (Player) sender;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()){
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak gracza."));
            return true;
        }

        double money = GeneralUtils.parseDouble(args[1]);
        if (money <= 0) {
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cZla liczba."));
            return true;
        }

        money = GeneralUtils.round(money, 2);

        EconomyProfile playerProfile = EconomyManager.get(player);
        EconomyProfile targetProfile = EconomyManager.get(target);

        if (playerProfile.take(money)) {
            targetProfile.give(money);
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&aWyslano pieniadze do gracza. &e(" + money + " TC do " + target.getName() + ")"));
            ChatUtils.sendMessage(target, ChatUtils.fixColor("&aOtrzymano pieniadze od gracza. &e(" + money + " TC od " + player.getName() + ")"));
        }
        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak srodkow."));

        return true;
    }
}
