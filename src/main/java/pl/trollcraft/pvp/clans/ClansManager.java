package pl.trollcraft.pvp.clans;

import com.google.common.collect.ArrayListMultimap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClansManager {

    private static int clanMaxPlayers;
    private static double clanMoney;
    private static int clanKills;

    private static final ArrayList<Clan> clans = new ArrayList<>();
    private static final HashMap<Player, Clan> playerClans = new HashMap<>();

    private static final ArrayListMultimap<Player, Clan> playerInvitations = ArrayListMultimap.create();

    private static final ArrayListMultimap<Clan, Clan> capitulations = ArrayListMultimap.create();

    // ---- ---- ---- ----

    public static void register(Clan clan) { clans.add(clan); }
    public static void register(Player player, Clan clan) { playerClans.put(player, clan); }

    public static void delete(Clan clan) {
        clans.remove(clan);
        YamlConfiguration conf = Configs.load("clans.yml");
        conf.set("clans." + clan.getId(), null);
        Configs.save(conf, "clans.yml");
    }

    public static void save(Clan clan) {
        YamlConfiguration conf = Configs.load("clans.yml");
        conf.set("clans." + clan.getId() + ".name", clan.getName());
        conf.set("clans." + clan.getId() + ".owner", clan.getOwner());
        conf.set("clans." + clan.getId() + ".players", clan.getPlayers());
        conf.set("clans." + clan.getId() + ".kills", clan.getKills());
        conf.set("clans." + clan.getId() + ".deaths", clan.getDeaths());
        conf.set("clans." + clan.getId() + ".level", clan.getLevel());
        conf.set("clans." + clan.getId() + ".war", clan.getWar());
        Configs.save(conf, "clans.yml");
    }

    public static void globalSave() {
        YamlConfiguration conf = Configs.load("clans.yml");
        for (Clan clan : clans) {
            conf.set("clans." + clan.getId() + ".name", clan.getName());
            conf.set("clans." + clan.getId() + ".owner", clan.getOwner());
            conf.set("clans." + clan.getId() + ".players", clan.getPlayers());
            conf.set("clans." + clan.getId() + ".kills", clan.getKills());
            conf.set("clans." + clan.getId() + ".deaths", clan.getDeaths());
            conf.set("clans." + clan.getId() + ".level", clan.getLevel());
            conf.set("clans." + clan.getId() + ".war", clan.getWar());
        }
        Configs.save(conf, "clans.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("clans.yml");
        assert conf != null;

        clanMaxPlayers = conf.getInt("clan.max");
        clanMoney = conf.getDouble("clan.required.money");
        clanKills = conf.getInt("clan.required.kills");

        conf.getConfigurationSection("clans").getKeys(false).forEach( id -> {
            String name = conf.getString("clans." + id + ".name");
            String owner = conf.getString("clans." + id + ".owner");
            List<String> players = conf.getStringList("clans." + id + ".players");
            int kills = conf.getInt("clans." + id + ".kills");
            int death = conf.getInt("clans." + id + ".death");

            int level;
            if (conf.contains("clans." + id + ".level"))
                level = conf.getInt("clans." + id + ".level");
            else
                level = 1;

            List<Integer> war;
            if (conf.contains("clans." + id + ".war"))
                war = conf.getIntegerList("clans." + id + ".war");
            else
                war = new ArrayList<>();

            register(new Clan(Integer.parseInt(id), name, owner, players, kills, death, level, war));
        } );

    }

    public static Clan get(int id) {
        return clans.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);
    }

    public static Clan get(Player player) {
        if (playerClans.containsKey(player)) return playerClans.get(player);
        return null;
    }

    public static Clan get(String name) {
        for (Clan clan : clans)
            if (clan.getName().equals(name)) return clan;
        return null;
    }

    public static void load(Player player) {
        String name = player.getName();
        for (Clan clan : clans)
            if (clan.getOwner().equals(name) || clan.getPlayers().contains(name)) {
                playerClans.put(player, clan);
                return;
            }
    }

    public static void unload(Player player) {
        playerClans.remove(player);
    }

    public static void remove(Clan clan) {
        clans.remove(clan);
        Player player;
        for (String name : clan.getPlayers()) {
            player = Bukkit.getPlayer(name);
            if (player == null) continue;
            if (playerClans.containsKey(player))
                playerClans.remove(player);
        }

        player = Bukkit.getPlayer(clan.getOwner());
        if (player == null) return;
        playerClans.remove(player);

        YamlConfiguration conf = Configs.load("clans.yml");
        assert conf != null;

        conf.set("clans." + clan.getId(), null);
        Configs.save(conf, "clans.yml");
    }

    // Kebs -> /klan pokoj TC
    // Data: <TC: <Kebs>>
    // asking: kebs
    // clan: tc
    public static boolean peace(Clan asking, Clan clan) {
        if (capitulations.containsEntry(asking, clan)) {
            capitulations.remove(clan, asking);
            capitulations.remove(asking, clan);

            clan.getWar().remove(Integer.valueOf(asking.getId()));
            asking.getWar().remove(Integer.valueOf(clan.getId()));

            return true;
        }

        capitulations.put(clan, asking);

        return false;
    }

    public static boolean invite(Player player, Clan clan) {
        if (playerInvitations.get(player).contains(clan)) return false;
        playerInvitations.put(player, clan);
        return true;
    }

    public static boolean acceptInvitation(Player player, Clan clan) {
        if (playerInvitations.get(player).contains(clan)){
            playerInvitations.remove(player, clan);
            return true;
        }
        return false;
    }

    public static boolean belongToClan(Player a, Player b) {
        String aName = a.getName();
        String bName = b.getName();
        for (Clan clan : clans)
            if (clan.getOwner().equals(aName) || clan.getPlayers().contains(aName)){
                if (clan.getOwner().equals(bName) || clan.getPlayers().contains(bName)) return true;
                return false;
            }
        return false;
    }

    public static int getClanMaxPlayers() { return clanMaxPlayers; }
    public static double getClanMoney() { return clanMoney; }
    public static int getClanKills() { return clanKills; }

}
