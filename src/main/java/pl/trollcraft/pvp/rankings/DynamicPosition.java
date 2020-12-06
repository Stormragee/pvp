package pl.trollcraft.pvp.rankings;

/**
 * A dynamic positions containg
 * information about position's value
 * as well as ranking index.
 *
 * @author Jakub Zelmanowicz
 */
public class DynamicPosition {

    /**
     * Current position in ranking.
     */
    private int index;

    /**
     * Current position data.
     */
    private Position position;

    /**
     * @param index - index in ranking,
     * @param position - position data.
     */
    public DynamicPosition(int index, Position position) {
        this.index = index;
        this.position = position;
    }

    /**
     * @return position in ranking.
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return position data.
     */
    public Position getPosition() {
        return position;
    }
}
