package pl.trollcraft.pvp.kits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.kits.Kit;
import pl.trollcraft.pvp.kits.KitsManager;
import pl.trollcraft.pvp.kits.kituser.KitUser;
import pl.trollcraft.pvp.kits.kituser.KitUserManager;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jedynie dla graczy online."));
            return true;
        }

        if (args.length != 1){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/kit <nazwa>"));
            return true;
        }

        String name = args[0];
        Kit kit = KitsManager.getKit(name);
        if (kit == null){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cTak kit nie istnieje."));
            return true;
        }

        Player player = (Player) sender;

        if (!kit.hasPermission(player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien do kitu."));
            return true;
        }

        KitUser kitUser = KitUserManager.get(player);
        if (kitUser == null) KitUserManager.load(player);

        long cooldown = kitUser.getCooldown(kit);
        if (cooldown > 0) {
            cooldown /= 1000;
            long seconds = cooldown % 60;
            long minutes = (cooldown / 60) % 60;
            long hours = (cooldown / (60 * 60)) % 24;

            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cTen kit bedziesz mogl wziac ponownie, za" +
                    " &e" + hours + " godzin, " + minutes + " minut i " + seconds + "sekund."));

            return true;
        }

        kit.give(player, false);
        kitUser.addCooldown(kit);
        KitUserManager.save(kitUser);
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aPrzyznano kit &e" + kit.getName()));

        return true;
    }
}
