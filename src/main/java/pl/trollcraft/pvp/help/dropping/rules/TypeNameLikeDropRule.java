package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.inventory.ItemStack;

public class TypeNameLikeDropRule implements DropRule {

    private final String name;

    public TypeNameLikeDropRule(String name) {
        this.name = name;
    }

    @Override
    public boolean check(ItemStack itemStack) {
        return itemStack.getType().name().toLowerCase().contains(name);
    }
}
