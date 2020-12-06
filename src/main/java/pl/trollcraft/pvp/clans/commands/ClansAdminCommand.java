package pl.trollcraft.pvp.clans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.trollcraft.pvp.clans.Clan;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;

public class ClansAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Klany admin:"));
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/klanadmin delete <klan>"));
            return true;
        }
        else {

            if (args[0].equalsIgnoreCase("delete")){

                if (args.length != 2) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/clanadmin delete <klan>"));
                    return true;
                }

                Clan clan = ClansManager.get(args[1]);
                if (clan == null) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKlan nie istnieje."));
                    return true;
                }

                ClansManager.remove(clan);
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUsunieto klan."));

                return true;
            }

            else if (args[0].equalsIgnoreCase("addkills")) {

                if (args.length != 3) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/clanadmin addkills <klan> <n>"));
                    return true;
                }

                Clan clan = ClansManager.get(args[1]);
                if (clan == null) {
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKlan nie istnieje."));
                    return true;
                }

                int kills = Integer.parseInt(args[2]);
                while (kills > 0) {
                    clan.addKill();
                    kills--;
                }

                sender.sendMessage(Help.color("&aDodano zabojstwa."));

            }

        }

        return true;
    }
}
