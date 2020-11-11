package pl.trollcraft.pvp.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.pvp.clans.Clan;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.data.Warrior;
import pl.trollcraft.pvp.data.WarriorsManager;
import pl.trollcraft.pvp.help.ChatUtils;

public class ChatManager implements Listener {

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ChatProcessor.Response res = ChatProcessor.process(player, message);
        if (res != ChatProcessor.Response.OK){
            event.setCancelled(true);
            player.sendMessage(res.getMessage());
            return;
        }

        event.getRecipients().removeAll(ChatProfile.getChatOff());

        Warrior warrior = WarriorsManager.get(player);
        Clan clan = ClansManager.get(player);

        String clanName = "";
        if (clan != null) clanName = "[" + clan.getName() + "] ";

        String prefix = ChatUtils.fixColor("&7&l[" + warrior.getLevel() + "] &e" + clanName);
        String suffix = PlaceholderAPI.setBracketPlaceholders(player, "{luckperms_suffix}");
        String name = player.getName();

        String format = ChatColor.translateAlternateColorCodes('&', prefix + "&r" + suffix + " " + name + ": "  + "%2$s");

        if (player.hasPermission("pvp.vip")) {
            if (!player.hasPermission("pvp.admin"))
                message = message.replaceAll("&c", "").replaceAll("&4", "");
            event.setMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        else
            event.setMessage(message);

        event.setFormat(format);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChatProfile.load(event.getPlayer());
    }

}
