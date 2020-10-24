package pl.trollcraft.pvp.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.trollcraft.pvp.help.ChatUtils;

public class CmdsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/cmd show/add/remove <komenda>"));
            return true;
        }
        else {

            if (args[0].equalsIgnoreCase("show")){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Zablokowane komendy:"));
                ChatUtils.sendMessage(sender, CmdManager.getCmds());
                return true;
            }
            else if (args[0].equalsIgnoreCase("add")){

                if (args.length < 2){
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/cmd add <komenda>"));
                    return true;
                }

                StringBuilder cmd;
                if (args[1].startsWith("/")) cmd = new StringBuilder();
                else cmd = new StringBuilder('/');

                for (int i = 1 ; i < args.length ; i++)
                    cmd.append(args[i] + " ");

                boolean add = CmdManager.add(cmd.toString().trim());
                if (!add)
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jest juz zablokowana."));
                else
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aKomenda zostala zablokowana."));

                return true;

            }
            else if (args[0].equalsIgnoreCase("remove")) {

                if (args.length < 2){
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/cmd add <komenda>"));
                    return true;
                }

                StringBuilder cmd;
                if (args[1].startsWith("/")) cmd = new StringBuilder();
                else cmd = new StringBuilder('/');

                for (int i = 1 ; i < args.length ; i++)
                    cmd.append(args[i] + " ");

                boolean remove = CmdManager.remove(cmd.toString().trim());
                if (!remove)
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda nie jest zablokowana."));
                else
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aKomenda zostala odblokowana."));

                return true;

            }

        }

        return true;
    }
}
