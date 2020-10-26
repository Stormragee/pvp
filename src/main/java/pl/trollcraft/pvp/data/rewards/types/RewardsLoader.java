package pl.trollcraft.pvp.data.rewards.types;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.items.ItemSerializer;

public class RewardsLoader  {

    public static RewardType load(String name, YamlConfiguration conf, String section) {
        if (name.equals("money"))
            return loadMoney(conf, section);

        else if (name.equals("items"))
            return loadItems(conf, section);

        throw new IllegalArgumentException("Unknown name of reward type.");
    }

    private static RewardType loadMoney(YamlConfiguration conf, String section) {
        double money = conf.getDouble(section + ".money");
        return new MoneyReward(money);
    }

    private static RewardType loadItems(YamlConfiguration conf, String section) {
        ItemStack itemStack = new ItemSerializer(conf, section + ".itemStack").getItemStack();
        String name = conf.getString(section + ".name");
        return new ItemsReward(name, itemStack);
    }

}
