package pl.trollcraft.pvp.auctions.items;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;
import java.util.List;

public class AuctionItem {

    private String id;
    private String owner;
    private ItemStack itemStack;
    private ItemStack iconItemStack;
    private double price;
    private boolean sold;
    
    private int toDisappear;

    public AuctionItem (String owner, ItemStack itemStack, double price, int toDisappear) {
        id = Help.randomString(32);
        this.owner = owner;
        this.itemStack = itemStack.clone();
        iconItemStack = itemStack.clone();
        this.price = price;
        this.toDisappear = toDisappear;
        sold = false;
        createIcon();
    }

    public AuctionItem (String id, String owner, ItemStack itemStack, double price, int toDisappear) {
        this.id = id;
        this.owner = owner;
        this.itemStack = itemStack.clone();
        iconItemStack = itemStack.clone();
        this.price = price;
        this.toDisappear = toDisappear;
        sold = false;
        createIcon();
    }

    private void createIcon() {
        ItemMeta itemMeta = iconItemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        if (lore == null)
            lore = new ArrayList<>();

        lore.add("");
        lore.add(Help.color("&aWystawil: &e" + owner));
        lore.add(Help.color("&aCena: &e" + price + " TC"));
        lore.add("");

        itemMeta.setLore(lore);
        iconItemStack.setItemMeta(itemMeta);
    }

    public ItemStack getPanelIcon() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        if (lore == null)
            lore = new ArrayList<>();

        lore.add("");
        lore.add(Help.color("&aCena: &e" + price + " TC"));
        lore.add(Help.color("&aZniknie za: &e" + formatToDisappear()));
        lore.add("");
        lore.add(Help.color("&7Kliknij, by usunac"));
        lore.add(Help.color("&7z aukcji i odzyskac."));
        lore.add("");

        itemMeta.setLore(lore);

        ItemStack icon = itemStack.clone();
        icon.setItemMeta(itemMeta);

        return icon;
    }

    private String formatToDisappear() {
        int hours = toDisappear / 3600;
        int remainder = (int) toDisappear - hours * 3600;
        int minutes = remainder / 60;
        return hours + "h. i " + minutes + "m.";
    }

    public void give(Player player) {
        player.getInventory().addItem(itemStack);
    }

    public String getId() { return id; }
    public Player getPlayer() { return Bukkit.getPlayer(owner); }
    public String getOwner() { return owner; }
    public ItemStack getItemStack() { return itemStack; }
    public ItemStack getIconItemStack() { return iconItemStack; }
    public double getPrice() { return price; }
    public int getToDisappear() { return toDisappear; }
    public boolean isSold() { return sold; }

    public void setSold(boolean sold) { this.sold = sold; }

    public void tick() { toDisappear--; }
    public boolean shouldDisappear() { return toDisappear <= 0; }

    public void dispose() {
        YamlConfiguration conf = Configs.load("auction.yml");
        conf.set("auction." + id, null);
        Configs.save(conf, "auction.yml");
    }

    @Override
    public boolean equals(Object o) {
        AuctionItem item = (AuctionItem) o;
        return (id.equals(item.getId()));
    }
}
