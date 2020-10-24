package pl.trollcraft.pvp.auctions.tasks;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.tasks.OfflineTask;

public class OfflineTransaction extends OfflineTask {

    private String buyer;
    private double money;

    public OfflineTransaction() { super(); }

    public OfflineTransaction(String playerName, String buyer, double money) {
        super(playerName);
        this.buyer = buyer;
        this.money = money;
    }

    @Override
    public boolean execute(Player player) {
        EconomyProfile profile = EconomyManager.get(player);
        assert profile != null;

        profile.give(money);
        player.sendMessage(Help.color(
                "&aGracz &e" + buyer + " &azakupil Twoj przedmiot na aukcji."));
        return true;
    }

    @Override
    public void failure(Player player) {}

    @Override
    public void save() {
        YamlConfiguration conf = Configs.load("tasks.yml");
        String id = getId();
        conf.set("tasks." + id + ".class", getClass().getName());
        conf.set("tasks." + id + ".playerName", getPlayerName());
        conf.set("tasks." + id + ".buyer", buyer);
        conf.set("tasks." + id + ".money", money);
        Configs.save(conf, "tasks.yml");
    }

    @Override
    public void load(String id) {
        YamlConfiguration conf = Configs.load("tasks.yml");
        setId(id);
        setPlayerName(conf.getString("tasks." + id + ".playerName"));
        this.buyer = conf.getString("tasks." + id + ".buyer");
        this.money = conf.getDouble("tasks." + id + ".money");
    }

}
