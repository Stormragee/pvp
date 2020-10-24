package pl.trollcraft.pvp.chat.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MessagesRegister {

    private static HashMap<String, String> rRegister = new HashMap<>();

    public static void register(String recipient, String sender) {
        if (rRegister.containsKey(recipient)) rRegister.replace(recipient, sender);
        else rRegister.put(recipient, sender);
    }

    public static Player getSender(String recipient) {
        if (rRegister.containsKey(recipient)) {
            Player player = Bukkit.getPlayer(rRegister.get(recipient));
            return player;
        }
        return null;
    }

}
