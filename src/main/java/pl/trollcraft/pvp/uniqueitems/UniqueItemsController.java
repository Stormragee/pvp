package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class UniqueItemsController {

    public static ArrayList<UniqueItem> uniqueItems = new ArrayList<>();

    public static int newInstance(PotionEffectType type, int ampl, ItemStack itemStack) {
        int id = new Random().nextInt(1000);

        UniqueItem uniqueItem = new UniqueItem(id, new PotionEffect(type, 60, ampl), itemStack);

        uniqueItems.add(uniqueItem);
        save(uniqueItem);

        return id;
    }

    public static Optional<UniqueItem> find(int id) {
        return uniqueItems.stream()
                   .filter(ui -> ui.getId() == id)
                   .findAny();
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("uniqueitems.yml");
        assert conf != null;

        conf.getConfigurationSection("uniqueitems").getKeys(false).forEach(idStr -> {

            int id = Integer.parseInt(idStr);
            PotionEffectType potionEffectType = PotionEffectType.getByName(conf.getString("uniqueitems." + idStr + ".effect.type"));
            int ampl = conf.getInt("uniqueitems." + idStr + ".effect.ampl");
            ItemStack itemStack = conf.getItemStack("uniqueitems." + idStr + ".itemStack");

            uniqueItems.add(new UniqueItem(id, new PotionEffect(potionEffectType, 60, ampl), itemStack));
        });

    }

    private static void save(UniqueItem item) {
        YamlConfiguration conf = Configs.load("uniqueitems.yml");
        assert conf != null;

        conf.set("uniqueitems." + item.getId() + ".effect.type", item.getPotionEffect().getType().getName());
        conf.set("uniqueitems." + item.getId() + ".effect.ampl", item.getPotionEffect().getAmplifier());
        conf.set("uniqueitems." + item.getId() + ".itemStack", item.getItemStack());

        Configs.save(conf, "uniqueitems.yml");
    }

}
