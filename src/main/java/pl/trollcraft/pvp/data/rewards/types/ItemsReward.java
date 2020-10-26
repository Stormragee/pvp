package pl.trollcraft.pvp.data.rewards.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.Help;

public class ItemsReward implements RewardType {

    private String itemName;
    private ItemStack itemStack;

    public ItemsReward(String itemName, ItemStack itemStack) {
        this.itemName = itemName;
        this.itemStack = itemStack;
    }

    @Override
    public String name() {
        return "items";
    }

    @Override
    public void message(Player player) {
        player.sendMessage(Help.color("&aNagrodzono Cie przedmiotem &e" + itemName + "!"));
    }

    @Override
    public void reward(Player player) {
        player.getInventory().addItem(itemStack);
    }
}
