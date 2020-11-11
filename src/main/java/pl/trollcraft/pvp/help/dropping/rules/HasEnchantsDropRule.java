package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.inventory.ItemStack;

public class HasEnchantsDropRule implements DropRule {

    @Override
    public boolean check(ItemStack itemStack) {
        return itemStack.getEnchantments().size() > 0;
    }

}
