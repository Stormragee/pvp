package pl.trollcraft.pvp.warp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.warp.DelayedWarp;
import pl.trollcraft.pvp.warp.SpawnListener;

import java.util.concurrent.TimeUnit;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, "Komenda jedynie dla graczy.");
            return true;
        }

        Player player = (Player) sender;

        new DelayedWarp(player, TimeUnit.SECONDS.toMillis(5), SpawnListener.SPAWN.getLocation());
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Teleportacja nastapi za &e5 sekund. &7Nie ruszaj sie."));

        return true;
    }
}
