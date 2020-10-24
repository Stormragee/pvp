package pl.trollcraft.pvp.warehouses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.ChatUtils;

public class Warehouse {

    private static final long WEEK = 1000 * 60 * 60 * 24 * 7;

    private int id;
    private String name;
    private long expires;
    private Inventory inventory;

    public Warehouse(int id, String name, int slots, long expires) {
        this.id = id;
        this.name = name;
        this.expires = expires;
        inventory = Bukkit.createInventory(null, slots, "Twoj magazyn");
    }

    public void open(Player player) {
        player.openInventory(inventory);
        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aOtworzono magazyn."));
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public long getExpires() { return expires; }
    public int getSlots() { return inventory.getSize(); }
    public ItemStack[] getItems() { return inventory.getContents(); }
    public Inventory getInventory() { return inventory; }

    public double getPrice() { return inventory.getSize() * WarehousesManager.getSlotPrice(); }

    public boolean hasExpired() { return System.currentTimeMillis() >= expires;  }

    public String getExpiresFormatted() {
        long time = expires - System.currentTimeMillis();
        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return days + " dni, " + hours % 24 + " godzin, " + minutes % 60 + " minut";
    }

    public void rent() {
        expires = System.currentTimeMillis() + WEEK;
    }

}
