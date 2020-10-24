package pl.trollcraft.pvp.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.warp.DelayedWarp;
import pl.trollcraft.pvp.warp.Warp;

public class WarpCommand implements CommandExecutor {

    private final String WARP_FORB_WORL = "warp_forbidden_worlds";

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda dostepna jedynie dla gracza online."));
            return true;
        }

        Player player = (Player) sender;
        String world = player.getWorld().getName();

        if (!player.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cNa trybie PVP warpy nie sa dostepne."));
            return true;
        }

        if (GeneralUtils.isInArray(world, PVP.getPlugin().getConfig().getStringList(WARP_FORB_WORL))) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cTutaj WARPy sa zablokowane. Aby opuscic ten swiat, udaj sie na &e/spawn."));
            return true;
        }

        if (DelayedWarp.hasAwaitingWarp(player)){
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&cMasz juz oczekujaca teleportacje."));
            return true;
        }

        if (args.length != 1) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cUzycie: /warp <nazwa>"));
            return true;
        }

        String name = args[0].toLowerCase();
        Warp warp = Warp.get(name);

        if (Warp.get(name) == null){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cNie ma takiego warpa."));
            return true;
        }

        warp.teleport(player);

        return true;
    }

}
