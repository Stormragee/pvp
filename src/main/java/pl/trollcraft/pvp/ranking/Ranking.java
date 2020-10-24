package pl.trollcraft.pvp.ranking;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.ranking.core.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class Ranking {

    private String name;
    private String title;

    protected ArrayList<Position> positions;
    protected HashMap<Player, Position> playersPositions;

    // ------------------------------------------------

    public Ranking(String name, String title) {
        this.name = name;
        this.title = title;
        positions = new ArrayList<>();
        playersPositions = new HashMap<>();
    }

    // ------------------------------------------------

    public abstract void prepare();
    public abstract int load(Player player);

    public String getName() { return name; }
    public String getTitle() { return title; }

    private void update(Position position) {
        int i = positions.indexOf(position);

        if (i == 0) {
            position.setNext(null);
            position.setPrevious(positions.get(1));
        }
        else if (i == positions.size()-1){
            position.setNext(positions.get(positions.size() - 2));
            position.setPrevious(null);
        }
        else{
            position.setNext(positions.get(i-1));
            position.setPrevious(positions.get(i+1));
        }

    }

    public void swap(Position a, Position b) {
        Collections.swap(positions, positions.indexOf(a), positions.indexOf(b));
        update(a);
        update(b);
    }

    public int promoteSwap(Player player) {
        Position position = get(player);
        if (position == null){
            load(player);
            return -1;
        }

        int pos = -1;
        while (position.getNext() != null && position.getNext().compareTo(position) == 1){
            swap(position, position.getNext());
            pos = positions.indexOf(position)+1;
        }
        Bukkit.getServer().getPluginManager().callEvent(new RankingUpdateEvent(this));
        return pos;
    }

    public int demoteSwap(Player player) {
        Position position = get(player);
        if (position == null){
            load(player);
            return -1;
        }

        int pos = -1;
        while (position.getPrevious() != null && position.getPrevious().compareTo(position) == -1){
            Bukkit.getConsoleSender().sendMessage(position.getName());
            swap(position, position.getPrevious());
            pos = positions.indexOf(position)+1;
        }
        Bukkit.getServer().getPluginManager().callEvent(new RankingUpdateEvent(this));
        return pos;
    }

    public void insertNew(Position position) {
        int size = positions.size();
        positions.add(position);
        positions.get(size-2).setPrevious(positions.get(size-1));
        positions.get(size-1).setNext(positions.get(size-2));
        Bukkit.getServer().getPluginManager().callEvent(new RankingUpdateEvent(this));
    }

    public List<String> getPositions(int amount) {
        ArrayList<String> positions = new ArrayList<>();
        Position p;
        for (int i = 0 ; i < amount ; i++) {
            p = this.positions.get(i);
            positions.add(p.toString(i+1));
        }
        return positions;
    }

    public Position get(Player player) {
        if (playersPositions.containsKey(player)) return playersPositions.get(player);
        return null;
    }

}
