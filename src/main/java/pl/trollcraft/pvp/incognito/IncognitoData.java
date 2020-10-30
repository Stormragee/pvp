package pl.trollcraft.pvp.incognito;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IncognitoData {

    private static List<String> nicknames;
    private static List<String> nicksInUse;

    public static void load() {
        YamlConfiguration conf = Configs.load("incognito.yml");
        assert conf != null;
        nicknames = conf.getStringList("nicknames");
        nicksInUse = new ArrayList<>();
    }

    public static String randomNickname() {
        if (nicknames.size() == nicksInUse.size())
            return null;

        Random r = new Random();

        int i;
        String nick;

        do {
            i = r.nextInt(nicknames.size());
            nick = nicknames.get(i);
        } while (nicksInUse.contains(nick));

        return nick;
    }

    public static void setInUse(String nickname) {
        nicksInUse.add(nickname);
    }

    public static void setNotInUse(String nickname) {
        nicksInUse.remove(nickname);
    }

    public static List<String> getNicknames() {
        return nicknames;
    }

    public static List<String> getNicksInUse() {
        return nicksInUse;
    }
}
