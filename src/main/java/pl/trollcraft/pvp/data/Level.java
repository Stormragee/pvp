package pl.trollcraft.pvp.data;

public class Level {

    private int value;
    private int requiredKills;

    public Level (int value, int requiredKills) {
        this.value = value;
        this.requiredKills = requiredKills;
    }

    public int getValue() { return value; }
    public int getRequiredKills() { return requiredKills; }

}
