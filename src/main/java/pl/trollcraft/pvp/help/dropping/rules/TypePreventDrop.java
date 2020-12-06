package pl.trollcraft.pvp.help.dropping.rules;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TypePreventDrop implements PreventDrop {

    private Material type;
    private boolean preventEnchanted;

    /**
     * Creates a new prevent item type
     * prevent system.
     *
     * @param type - item type to prevent drop of,
     * @param preventEnchanted - if item is enchanted, prevent drop despite.
     */
    public TypePreventDrop(Material type, boolean preventEnchanted) {
        this.type = type;
        this.preventEnchanted = preventEnchanted;
    }

    public TypePreventDrop(Material type) {
        this.type = type;
        this.preventEnchanted = true;
    }

    @Override
    public boolean prevent(ItemStack itemStack) {

        if (itemStack.getType() == type) {

            if (itemStack.getEnchantments().isEmpty())
                return true;

            return !preventEnchanted;

        }

        return false;
    }

}
