package pl.trollcraft.pvp.rankings.kills;

import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.rankings.Position;

/**
 * Position in kills ranking.
 *
 * @see KillsRanking
 * @author Jakub Zelmanowicz
 */
public class KillsPosition implements Position {

    /**
     * Player who owns the position.
     */
    private String player;

    /**
     * Amount of kills.
     */
    private int points;

    /**
     * @param player - owner of the position,
     * @param points - points of position.
     */
    public KillsPosition(String player, int points) {
        this.player = player;
        this.points = points;
    }

    /**
     * Gets the player.
     *
     * @return positioned player.
     */
    @Override
    public String player() {
        return player;
    }

    /**
     * Describes the position.
     *
     * @return description of the position.
     */
    @Override
    public String desc() {
        return String.format(Help.color("&c%s - &e%d zabojstw"), player, points);
    }

    /**
     * Changes the value of the position.
     *
     * @param val - new value.
     */
    @Override
    public void set(Object val) {
        this.points = (int) val;
    }

    /**
     * Gets the value of the position.
     *
     * @return value
     */
    @Override
    public Object val() {
        return points;
    }

    /**
     * Compares this position with
     * another one.
     *
     * @param other - compared position.
     * @return -1 if this position is worse,
     *          0 if positions are equal,
     *          1 if this position is better.
     */
    @Override
    public int compareTo(Position other) {

        if (other instanceof KillsPosition) {

            KillsPosition o = (KillsPosition) other;

            if (o.points < points)
                return -1;
            else if (o.points > points)
                return 1;

            return 0;

        }

        throw new IllegalStateException("Cannot compare PointsPosition to non-KillsPosition object.");
    }
}
