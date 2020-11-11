package pl.trollcraft.pvp.ranking.killstreak;

import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.ranking.core.Position;

public class KillStreakPosition extends Position {

    private int killStreak;

    public KillStreakPosition (String name, int killStreak){
        super(name);
        this.killStreak = killStreak;
    }

    public void setKillStreak(int killStreak) { this.killStreak = killStreak; }

    @Override
    public String toString(int ind) {
        return ChatUtils.fixColor("&e&l" + ind + ". &c&l" + this.name + " - &e&l" + killStreak);
    }

    @Override
    public int compareTo(Position position) {
        KillStreakPosition economyPosition = (KillStreakPosition) position;
        if (economyPosition.killStreak > killStreak) return 1;
        else if (economyPosition.killStreak < killStreak) return -1;
        return 0;
    }

}
