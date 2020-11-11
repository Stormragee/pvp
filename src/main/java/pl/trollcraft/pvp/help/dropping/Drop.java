package pl.trollcraft.pvp.help.dropping;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.dropping.rules.DropRule;
import pl.trollcraft.pvp.help.dropping.rules.HasEnchantsDropRule;
import pl.trollcraft.pvp.help.dropping.rules.TypeDropRule;
import pl.trollcraft.pvp.help.dropping.rules.TypeNameLikeDropRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles dropping items from
 * players inventory.
 */
public class Drop {

    private static List<DropRule> dropRules;

    public static void init() {
        dropRules = new ArrayList<>();
        dropRules.add(new HasEnchantsDropRule());
    }

    private static boolean check(ItemStack itemStack) {
        return dropRules.stream()
                .anyMatch( rule -> rule.check(itemStack) );
    }

    public static void drop(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();

        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (!check(itemStack)) continue;

            world.dropItem(loc, itemStack);
        }

        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (!check(itemStack)) continue;

            world.dropItem(loc, itemStack);
        }

        player.getInventory().clear();
    }

}
