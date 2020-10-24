package pl.trollcraft.pvp.chat.messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

import java.util.HashMap;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Uzycie: /" + label + " <gracz> <wiadomosc>"));
            return true;
        }

        if (args[0].equals(sender.getName())){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cNie mozesz wyslac wiadomosci do samego siebie."));
            return true;
        }

        Player r = Bukkit.getPlayer(args[0]);

        if (r == null || !r.isOnline()){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cNie ma gracza na serwerze PVP."));
            return true;
        }

        StringBuilder msg = new StringBuilder();
        for (int i = 1 ; i < args.length ; i++){
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
