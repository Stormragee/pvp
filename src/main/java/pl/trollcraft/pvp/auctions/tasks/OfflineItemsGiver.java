package pl.trollcraft.pvp.auctions.tasks;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.tasks.OfflineTask;

public class OfflineItemsGiver extends OfflineTask {

    private ItemStack itemStack;

    public OfflineItemsGiver() { super(); }

    public OfflineItemsGiver(String playerName, ItemStack itemStack) {
        super(playerName);
        this.itemStack = itemStack;
    }

    @Override
    public boolean execute(Player player) {
        return player.getInventory().addItem(itemStack).isEmpty();
    }

    @Override
    public void failure(Player player) {
        player.sendMessage("");
        player.sendMessage(Help.color("&c&lTwoj ekwipunek jest pelny. Zwolnij miejce i przeloguj sie."));
        player.sendMessage("");
    }

    public OfflineItemsGiver(String id, String playerName) {
        super(id, playerName);
    }

    @Override
    public void save() {
        YamlConfiguration conf = Configs.load("tasks.yml");
        String id = getId();
        conf.set("tasks." + id + ".class", getClass().getName());
        conf.set("tasks." + id + ".playerName", getPlayerName());
        conf.set("tasks." + id + ".itemStack", itemStack);
        Configs.save(conf, "tasks.yml");
    }

    @Override
    public void load(String id) {
        YamlConfiguration conf = Configs.load("tasks.yml");
        setId(id);
        setPlayerName(conf.getString("tasks." + id + ".playerName"));
        itemStack = conf.getItemStack("tasks." + id + ".itemStack");
    }

}
