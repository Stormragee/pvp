package pl.trollcraft.pvp.clans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clan {

    private static final Random RAND = new Random();

    private int id;
    private String name;
    private String owner;
    private List<String> players;
    private int kills;
    private int deaths;

    public Clan (int id, String name, String owner, List<String> players, int kills, int deaths) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.players = players;
        this.kills = kills;
        this.deaths = deaths;
    }

    public Clan (String name, String owner) {
        this.id = RAND.nextInt(100000);
        this.name = name;
        this.owner = owner;
        this.players = new ArrayList<>();
        this.kills = 0;
        this.deaths = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public List<String> getPlayers() { return players; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getMembers() { return players.size() + 1; }

    public void addKill() { kills++; }
    public void addDeath() { deaths++; }

    public void addPlayer(Player player) { players.add(player.getName()); }
    public void removePlayer(Player player) { players.remove(player.getName()); }

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
