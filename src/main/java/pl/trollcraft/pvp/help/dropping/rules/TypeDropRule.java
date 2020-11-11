package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TypeDropRule implements DropRule {

    private final Material type;

    public TypeDropRule(Material type) {
        this.type = type;
    }

    @Override
    public boolean check(ItemStack itemStack) {
        return itemStack.getType() == type;
    }
}
