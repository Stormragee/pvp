package pl.trollcraft.pvp.rankings;

/**
 * A position of player
 * in the ranking.
 *
 * @author Jakub Zelmanowicz
 */
public interface Position extends Comparable<Position> {

    /**
     * Gets the player.
     *
     * @return positioned player.
     */
    String player();

    /**
     * Describes the position.
     *
     * @return description of the position.
     */
    String desc();

    /**
     * Changes the value of the position.
     *
     * @param val - new value.
     */
    void set(Object val);

    /**
     * Gets the value of the position.
     *
     * @return value
     */
    Object val();

}
