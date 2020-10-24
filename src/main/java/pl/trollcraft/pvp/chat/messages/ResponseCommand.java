package pl.trollcraft.pvp.chat.messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class ResponseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Uzycie: /" + label + " <wiadomosc>"));
            return true;
        }

        Player r = MessagesRegister.getSender(sender.getName());
        if (r == null){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak nadawcy."));
            return true;
        }

        StringBuilder msg = new StringBuilder();
        for (int i = 0 ; i < args.length ; i++){
            msg.append(args[i]);
            msg.append(' ');
        }

        String message = msg.toString();
        ChatUtils.sendMessage(sender, ChatUtils.fixColor("&6[ Ja >>> " + r.getName() + " ] &f" + message));
        ChatUtils.sendMessage(r, ChatUtils.fixColor("&6[ " + sender.getName() + " >>> Ja ] &f" + message));
        MessagesRegister.register(r.getName(), sender.getName());
        return true;
    }


}
