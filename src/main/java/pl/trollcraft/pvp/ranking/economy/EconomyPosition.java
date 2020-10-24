package pl.trollcraft.pvp.ranking.economy;

import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.ranking.core.Position;

public class EconomyPosition extends Position {

    double money;

    public EconomyPosition (String name, double money){
        super(name);
        this.money = money;
    }

    public void setMoney(double money) { this.money = money; }

    @Override
    public String toString(int ind) {
        return ChatUtils.fixColor("&e&l" + ind + ". &a&l" + this.name + " - &e&l" + GeneralUtils.format(money) + " TC");
    }

    @Override
    public int compareTo(Position position) {
        EconomyPosition economyPosition = (EconomyPosition) position;
        if (economyPosition.money > money) return 1;
        else if (economyPosition.money < money) return -1;
        return 0;
    }

}
