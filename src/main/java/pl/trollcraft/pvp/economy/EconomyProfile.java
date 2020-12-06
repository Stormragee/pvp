package pl.trollcraft.pvp.economy;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.ranking.economy.EconomyPosition;

public class EconomyProfile {

    private Player player;
    private double money;

    public EconomyProfile (Player player, double money) {
        this.player = player;
        this.money = money;
    }

    public void set(double money) { this.money = money; }
    public boolean has(double money) { return this.money >= money; }

    public void give(double money) {
        this.money += money;
        //((EconomyPosition) PVP.getPlugin().getEconomyRanking().get(player)).setMoney(this.money);
        //PVP.getPlugin().getEconomyRanking().promoteSwap(player);
    }

    public boolean take(double money) {
        if (has(money)) {
            this.money -= money;
            //((EconomyPosition) PVP.getPlugin().getEconomyRanking().get(player)).setMoney(this.money);
            //PVP.getPlugin().getEconomyRanking().demoteSwap(player);
            return true;
        }
        return false;
    }

    public Player getPlayer() { return player; }
    public double getMoney() { return GeneralUtils.round(money, 2); }

}
