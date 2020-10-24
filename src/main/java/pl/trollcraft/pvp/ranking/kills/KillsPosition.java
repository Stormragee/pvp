package pl.trollcraft.pvp.ranking.kills;

import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.ranking.core.Position;

public class KillsPosition extends Position {

    private int kills;

    public KillsPosition(String name, int kills) {
        super(name);
        this.kills = kills;
    }

    public int getKills() { return kills; }
    public void addKill() { kills++; }
    public void delKill() { kills--; }

    @Override
    public String toString(int ind) {
        return ChatUtils.fixColor("&e&l" + ind + ". &c&l" + this.name + " - &e&l" + kills + " zabojstw");
    }

    @Override
    public int compareTo(Position position) {
        KillsPosition killsPosition = (KillsPosition) position;
        if (killsPosition.kills > kills) return 1;
        else if (killsPosition.kills < kills) return -1;
        return 0;
    }
}

