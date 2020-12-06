package pl.trollcraft.pvp.rankings;

import org.bukkit.Bukkit;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.rankings.event.RankingSwapEvent;
import pl.trollcraft.pvp.rankings.event.RankingUpdateEvent;
import pl.trollcraft.pvp.rankings.event.SwapType;

import java.util.*;
import java.util.logging.Level;

/**
 * Represents a ranking in
 * certain domain
 *
 * @author Jakub Zelmanowiczs
 */
public abstract class Ranking {

    /**
     * Name of the ranking.
     */
    private final String name;

    /**
     * Title displayed on hologram.
     * @see pl.trollcraft.pvp.rankings.holoranking.HoloRanking
     */
    private final String title;

    /**
     * LinkedList of positions.
     */
    private final LinkedList<Position> positions;

    /**
     * Basic ranking constructor.
     */
    public Ranking(String name, String title) {
        this.name = name;
        this.title = Help.color(title);
        this.positions = new LinkedList<>();
    }

    /**
     * Gets all positions in the ranking.
     *
     * @return list of positions in the ranking.
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Gets all positions in the ranking.
     *
     * @param amount - amount of positions to get.
     * @return list of positions in the ranking.
     */
    public List<Position> getPositions(int amount) {
        return positions.subList(0, amount);
    }

    /**
     * Defines executes the first sorting of
     * the positions of players in the
     * positions map.
     */
    public abstract void calculate();

    public void tryPromote(Position position) {

        int currIndex = positions.indexOf(position);
        ListIterator<Position> it = positions.listIterator(currIndex);

        Position other = null;
        int newIndex = -1;

        // Looking for possible swap.
        while (it.hasPrevious()) {

            other = it.previous();

            if (position.compareTo(other) > 0) {
                newIndex = it.previousIndex() + 1;
                break;
            }
            else {
                // Swapping elements
                Collections.swap(positions, currIndex, it.previousIndex() + 1);
                currIndex = it.previousIndex() + 1;

                RankingSwapEvent swapEvent = new RankingSwapEvent(other.player(), this, currIndex + 1, SwapType.DEMOTE);
                Bukkit.getPluginManager().callEvent(swapEvent);

                if (it.hasPrevious())
                    newIndex = 0;

            }
        }

        Bukkit.getLogger().log(Level.INFO, "newIndex " + newIndex);

        // Swap did not occur.
        if (newIndex == -1)
            return;

        Bukkit.getLogger().log(Level.INFO, "Pushing event....");

        RankingSwapEvent swapEvent = new RankingSwapEvent(position.player(), this, newIndex + 2, SwapType.PROMOTE);
        RankingUpdateEvent updateEvent = new RankingUpdateEvent(position.player(), this, newIndex + 2);

        Bukkit.getPluginManager().callEvent(swapEvent);
        Bukkit.getPluginManager().callEvent(updateEvent);

    }

    public void tryDemote(Position position) {

        int currIndex = positions.indexOf(position);
        ListIterator<Position> it = positions.listIterator(currIndex);

        Position other;
        int newIndex = -1;

        // Looking for possible swap.
        while (it.hasNext()) {

            other = it.next();

            if (position.compareTo(other) < 0) {
                newIndex = it.nextIndex() + 1;
                break;
            }
            else {
                // Swapping elements
                Collections.swap(positions, currIndex, it.nextIndex() - 1);
                currIndex = it.nextIndex() - 1;

                RankingSwapEvent swapEvent = new RankingSwapEvent(other.player(), this, it.nextIndex() + 1, SwapType.PROMOTE);
                Bukkit.getPluginManager().callEvent(swapEvent);

                if (it.hasNext())
                    newIndex = it.nextIndex() + 1;

            }
        }

        // Swap did not occur.
        if (newIndex == -1)
            return;

        RankingSwapEvent swapEvent = new RankingSwapEvent(position.player(), this, newIndex, SwapType.DEMOTE);
        RankingUpdateEvent updateEvent = new RankingUpdateEvent(position.player(), this, newIndex);

        Bukkit.getPluginManager().callEvent(swapEvent);
        Bukkit.getPluginManager().callEvent(updateEvent);

    }

    /**
     * Finds the index and pos data of
     * the player.
     *
     * @param player - name of the player.
     * @return index and position data.
     */
    public Optional<DynamicPosition> get(String player) {
        return getPositions()
                .stream()
                .filter(pos -> pos.player().equals(player))
                .map(pos -> new DynamicPosition(getPositions().indexOf(pos), pos))
                .findFirst();
    }

    /**
     * @return the name of the ranking.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the title displayed on hologram.
     * @see pl.trollcraft.pvp.rankings.holoranking.HoloRanking
     */
    public String getTitle() {
        return title;
    }
}
