package pl.trollcraft.pvp.clans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.clans.event.ClanLevelUpEvent;
import pl.trollcraft.pvp.help.ChatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clan {

    private static final Random RAND = new Random();

    private static final int MEM_PER_LVL = 1;
    private static final int KILLS_TO_LVL_UP = 500;

    private final int id;
    private final String name;
    private String owner;
    private final List<String> players;
    private int kills;
    private int deaths;

    private int level;

    private final List<Integer> war;

    public Clan (int id, String name, String owner, List<String> players, int kills, int deaths, int level, List<Integer> war) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.players = players;
        this.kills = kills;
        this.deaths = deaths;
        this.level = level;
        this.war = war;
    }

    public Clan (String name, String owner) {
        this.id = RAND.nextInt(100000);
        this.name = name;
        this.owner = owner;
        this.players = new ArrayList<>();
        this.kills = 0;
        this.deaths = 0;
        this.level = 1;
        this.war = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public List<String> getPlayers() { return players; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getMembers() { return players.size() + 1; }
    public List<Integer> getWar() { return war; }

    public void addKill() {
        kills++;
        if (canLevelUp()) {
            level++;
            Bukkit.getPluginManager().callEvent(new ClanLevelUpEvent(this));
        }
    }

    public void addDeath() { deaths++; }

    public void addKills(int kills) { this.kills += kills; }

    public void addPlayer(Player player) { players.add(player.getName()); }
    public void removePlayer(Player player) { players.remove(player.getName()); }

    public int getLevel() {
        return level;
    }

    public boolean canLevelUp() {
        return (kills % KILLS_TO_LVL_UP == 0);
    }

    public boolean declareWar(Clan clan) {
        if (war.contains(clan.id))
            return false;
        war.add(clan.id);
        return true;
    }

    public int getMaxMembers() {
        return level > 0 ? 3 + level*MEM_PER_LVL : 3;
    }

    public void setOwner(String owner) { this.owner = owner; }
    public boolean isOwner(Player player) {
        return owner.equals(player.getName());
    }

    public boolean kick(String player) {
        if (players.contains(player)){
            players.remove(player);
            return true;
        }
        return false;
    }

    public void announce(String message, boolean toOwner) {
        Player player;
        String msg = ChatUtils.fixColor(message);
        for (String name : players) {

            player = Bukkit.getPlayer(name);
            if (player == null || !player.isOnline()) continue;
            player.sendMessage(msg);

        }

        if (toOwner){
            player = Bukkit.getPlayer(owner);
            if (player == null || !player.isOnline()) return;
            player.sendMessage(msg);
        }
    }

}
