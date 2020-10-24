package pl.trollcraft.pvp.ranking.holoranking;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.ranking.Ranking;

import java.util.Random;

public class HoloRanking {

    private static final Random RAND = new Random();

    private int id;
    private Location location;
    private Hologram hologram;
    private Ranking ranking;
    private int positions;

    public HoloRanking (int id, Location location, Ranking ranking, int positions) {
        this.id = id;
        this.location = location;
        this.ranking = ranking;
        this.positions = positions;
        update();
    }

    public HoloRanking (Location location, Ranking ranking, int positions) {
        this.id = RAND.nextInt(100000);
        this.location = location;
        this.ranking = ranking;
        this.positions = positions;
        update();
    }

    public void update() {
        if (hologram == null)
            hologram = HologramsAPI.createHologram(PVP.getPlugin(), location.clone().add(0, positions / 2 + 2, 0));

        hologram.clearLines();
        hologram.appendTextLine(ranking.getTitle());
        hologram.appendTextLine("");
        ranking.getPositions(positions).forEach( line -> hologram.appendTextLine(line) );
    }

    public void delete() { hologram.delete(); }

    public int getId() { return id; }
    public Location getLocation() { return location; }
    public Ranking getRanking() { return ranking; }
    public int getPositions() { return positions; }
}
