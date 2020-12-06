package pl.trollcraft.pvp.rankings.event;

public enum SwapType {

    PROMOTE,
    DEMOTE;

    public SwapType opposite() {
        if (this == PROMOTE)
            return DEMOTE;
        return PROMOTE;
    }

}
