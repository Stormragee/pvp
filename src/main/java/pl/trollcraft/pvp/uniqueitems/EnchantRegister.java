package pl.trollcraft.pvp.uniqueitems;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;

public class EnchantRegister {

    public static final UniqueEnchantment UNIQUE_ENCHANTMENT = new UniqueEnchantment();

    public static void register() {

        try {

            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);

            Enchantment.registerEnchantment(UNIQUE_ENCHANTMENT);
        }
        catch (IllegalAccessException | IllegalStateException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

}
