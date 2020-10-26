package pl.trollcraft.pvp.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.data.events.WarriorLevelUpEvent;
import pl.trollcraft.pvp.data.levels.Level;
import pl.trollcraft.pvp.data.levels.LevelsManager;

import java.text.DecimalFormat;

public class Warrior {

    private static DecimalFormat format = new DecimalFormat("0.00");

    private Player player;
    private int level;
    private Level nextLevel;
    private int kills;
    private int killStreak;
    private int deaths;
    private double kdr;

    public Warrior(Player player, int level, int kills, int killStreak, int deaths) {
        this.player = player;
        this.level = level;
        nextLevel = LevelsManager.get(level + 1);
        this.kills = kills;
        this.killStreak = killStreak;
        this.deaths = deaths;

        if (deaths == 0) kdr = kills;
        else kdr = (double) kills/deaths;
    }

    public Player getPlayer() { return player; }
    public int getLevel() { return level; }
    public int getKills() { return kills; }
    public int getKillStreak() { return killStreak; }
    public int getDeaths() { return deaths; }

    public double getKdr() { return kdr; }
    public String getKdrFormatted() { return format.format(kdr); }

    public int getKillsToPromotion() {
        if (nextLevel == null) return 0;
        return nextLevel.getRequiredKills() - kills;
    }

    private void updateKdr() { kdr = (double) kills/deaths; }

    public void addKill() { kills++; killStreak++; updateKdr(); }
    public void addDeath() { deaths++; killStreak = 0; updateKdr(); }

    public void reset() {
        kills = 0;
        deaths = 0;
    }

    public boolean canReset() {
        return kills > 0 && deaths > 0;
    }

    public boolean tryPromote() {
        if (nextLevel == null) return false;
        if (kills >= nextLevel.getRequiredKills()){
            level++;
            nextLevel = LevelsManager.get(level + 1);
            Bukkit.getPluginManager().callEvent(new WarriorLevelUpEvent(this, level));
            return true;
        }
        return false;
    }

    /**
     * Debug only
     * @return boolean
     */
    public boolean forcePromote() {
        level++;
        nextLevel = LevelsManager.get(level + 1);
        Bukkit.getPluginManager().callEvent(new WarriorLevelUpEvent(this, level));
        return true;
    }

    public void resetLevel() {
        level = 0;
    }

}
