package pl.trollcraft.pvp.incognito;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.GeneralUtils;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.ArrayList;

public class Incognito {

    private static ArrayList<String> incognitoPlayers = new ArrayList<>();

    private static void set(Player player, String name) {

        NickAPI.nick(player, name);
        NickAPI.setSkin(player, "Notch");
        NickAPI.setUniqueId(player, name);
        NickAPI.setGameProfileName(player, name);
        NickAPI.refreshPlayer(player);
        player.sendMessage("Done");

    }

    public static boolean isIncognito(Player player){
        return incognitoPlayers.contains(player.getName());
    }

    public static void on(Player player){
        incognitoPlayers.add(player.getName());
        randomize(player);
    }

    public static void randomize(Player player){
        set(player, "Profesor_Kation");
    }

    public static void off(Player player){
        incognitoPlayers.remove(player.getName());
        NickAPI.resetNick( player );
        NickAPI.resetSkin( player );
        NickAPI.resetUniqueId( player );
        NickAPI.resetGameProfileName( player );
        NickAPI.refreshPlayer( player );
    }

}
