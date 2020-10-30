package pl.trollcraft.pvp.incognito;

import com.nametagedit.plugin.NametagEdit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.clans.Clan;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.help.Help;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.ArrayList;
import java.util.HashMap;

public class Incognito {

    private static final ArrayList<String> incognitoPlayers = new ArrayList<>();

    private static final HashMap<String, String> incognitoNames = new HashMap<>();

    private static void set(Player player, String name) {

        if (incognitoNames.containsKey(player)) {
            String oldNick = incognitoNames.get(player);
            IncognitoData.setNotInUse(oldNick);
            incognitoNames.replace(name, player.getName());
        }
        else
            incognitoNames.put(name, player.getName());

        NickAPI.nick(player, name);
        NickAPI.setSkin(player, name);
        NickAPI.setUniqueId(player, name);
        NickAPI.setGameProfileName(player, name);
        NickAPI.refreshPlayer(player);

        IncognitoData.setInUse(name);
    }

    public static boolean isIncognito(Player player){
        return incognitoPlayers.contains(player.getName());
    }

    public static void on(Player player){
        incognitoPlayers.add(player.getName());
        randomize(player);
    }

    public static void randomize(Player player) {
        String nick = IncognitoData.randomNickname();
        if (nick == null)
            return;

        set(player, nick);
        player.sendMessage(Help.color("&7Od teraz jestes postrzegany jako &e" + nick + "."));
    }

    public static void off(Player player){
        incognitoPlayers.remove(player.getName());
        reset(player);

        String nick = incognitoNames.get(player);
        IncognitoData.setNotInUse(nick);
        incognitoNames.remove(player);

        Clan clan = ClansManager.get(player);
        if (clan != null)
            NametagEdit.getApi().setPrefix(player, "&e[" + clan.getName() + "] &f");
    }

    public static void reset(Player player){
        NickAPI.resetNick( player );
        NickAPI.resetSkin( player );
        NickAPI.resetUniqueId( player );
        NickAPI.resetGameProfileName( player );
        NickAPI.refreshPlayer( player );
    }

    public static ArrayList<String> getIncognitoPlayers() {
        return incognitoPlayers;
    }

    public static HashMap<String, String> getIncognitoNames() {
        return incognitoNames;
    }
}
