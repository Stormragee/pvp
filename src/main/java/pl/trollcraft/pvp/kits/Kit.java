package pl.trollcraft.pvp.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class Kit {

    private String name;
    private String permission;
    private boolean clearInventory;
    private long cooldown;
    private ItemStack[] armor;
    private ArrayList<ItemStack> items;

    public Kit(String name, String permission, boolean clearInventory, long cooldown, ItemStack[] armor, ArrayList<ItemStack> items){
        this.name = name;
        this.permission = permission;
        this.clearInventory = clearInventory;
        this.cooldown = cooldown;
        this.armor = armor;
        this.items = items;
    }

    // ------------------------------------------------------------------------------

    public boolean hasPermission(Player player) { return player.hasPermission(permission); }

    public void give(Player player, boolean wear) {
        PlayerInventory inv = player.getInventory();
        //if (clearInventory) inv.clear();

        if (wear)
            inv.setArmorContents(armor);
        else{
            for (ItemStack is : armor) {
                if (is == null || is.getType() == Material.AIR) continue;
                player.getInventory().addItem(is);
            }
        }

        items.forEach( is -> {
            inv.addItem(is);
        } );
        return;
    }

    // ------------------------------------------------------------------------------

    public String getName() { return name; }
    public String getPermission() { return permission; }
    public boolean clearsInventory() { return clearInventory; }
    public long getCooldown() { return cooldown; }
    public ItemStack[] getArmor() { return armor; }
    public ArrayList<ItemStack> getItems() { return items; }

}
