package pl.trollcraft.pvp.boosters.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.boosters.GlobalBooster;
import pl.trollcraft.pvp.boosters.PlayerBooster;
import pl.trollcraft.pvp.help.ChatUtils;

public class BoosterAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("prison.admin") || sender instanceof ConsoleCommandSender){

            if (args.length == 0) {

            }
            else if (args[0].equalsIgnoreCase("add")) {

                if (args.length != 4) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Uzycie: /boosteradmin add <gracz> <bonus> <sekundy>"));
                    return true;
                }

                Player boosted = Bukkit.getPlayer(args[1]);
                double bonus = Double.parseDouble(args[2]);
                int seconds = Integer.parseInt(args[3]);

                if (boosted != null && boosted.isOnline()) {

                    PlayerBooster active = PlayerBooster.getBooster(boosted);
                    if (active != null){
                        bonus = Math.max(bonus, active.getBonus());

                        if (active.getSeconds() > 0)
                            seconds += active.getSeconds();

                        active.setBonus(bonus);
                        active.setSeconds(seconds);

                        ChatUtils.sendMessage(boosted, ChatUtils.fixColor("&aTwoj &eBOOSTER zostal przedluzony!\n&7(bonus &ex" + bonus + " &7przez &e" + seconds + " sekund)"));
                    }
                    else{
                        new PlayerBooster(seconds, bonus, boosted);
                        ChatUtils.sendMessage(boosted, ChatUtils.fixColor("&aOtrzymujesz &eBOOSTER!\n&7(bonus &ex" + bonus + " &7przez &e" + seconds + " sekund)"));
                    }

                }
                else
                    PlayerBooster.setOfflineBooster(args[1], bonus, seconds);

            }

            else if (args[0].equalsIgnoreCase("global")) {

                if (args.length != 3) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Uzycie: /boosteradmin global <bonus> <sekundy>"));
                    return true;
                }

                double bonus = Double.parseDouble(args[1]);
                int seconds = Integer.parseInt(args[2]);

                new GlobalBooster(seconds, bonus);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&aDodano &e&lBOOSTER GLOBALNY &ex" + bonus + " &ana &e" + seconds + " sekund."));
                }

            }

        }
        else
            ChatUtils.sendMessage(sender, "&cBrak uprawnien!");

        return true;
    }
}
