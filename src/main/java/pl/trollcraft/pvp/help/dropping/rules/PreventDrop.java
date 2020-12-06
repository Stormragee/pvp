package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.inventory.ItemStack;

public interface PreventDrop {

    boolean prevent(ItemStack itemStack);

}
