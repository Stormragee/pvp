package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.inventory.ItemStack;

public interface DropRule {

    boolean check(ItemStack itemStack);

}
