package pl.trollcraft.pvp.help.dropping;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.dropping.rules.PreventDrop;
import pl.trollcraft.pvp.help.dropping.rules.TypePreventDrop;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles dropping items from
 * players inventory.
 */
public class Drop {

    private static List<PreventDrop> preventDrops;

    public static void init() {
        preventDrops = new ArrayList<>();

        preventDrops.add(new TypePreventDrop(Material.DIAMOND_SWORD, true));
        preventDrops.add(new TypePreventDrop(Material.BOW, true));
        preventDrops.add(new TypePreventDrop(Material.COOKED_FISH));
        preventDrops.add(new TypePreventDrop(Material.GRILLED_PORK));
        preventDrops.add(new TypePreventDrop(Material.IRON_HELMET, true));
        preventDrops.add(new TypePreventDrop(Material.IRON_CHESTPLATE, true));
        preventDrops.add(new TypePreventDrop(Material.IRON_LEGGINGS, true));
        preventDrops.add(new TypePreventDrop(Material.IRON_BOOTS, true));
        preventDrops.add(new TypePreventDrop(Material.ARROW));

    }

    private static boolean prevent(ItemStack itemStack) {
        return preventDrops.stream()
                .anyMatch( rule -> rule.prevent(itemStack) );
    }

    public static void drop(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();

        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (prevent(itemStack)) continue;

            world.dropItem(loc, itemStack);
        }

        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            if (prevent(itemStack)) continue;

            world.dropItem(loc, itemStack);
        }

        player.getInventory().clear();

        player.getInventory().setArmorContents(new ItemStack[] { new ItemStack(Material.AIR), new ItemStack(Material.AIR) ,new ItemStack(Material.AIR), new ItemStack(Material.AIR) });
    }

}
