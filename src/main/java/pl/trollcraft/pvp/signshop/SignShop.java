package pl.trollcraft.pvp.signshop;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.ChatUtils;

public class SignShop {

    private int id;
    private Sign sign;
    private ItemStack itemStack;
    private String name;
    private double price;

    public SignShop (int id, Sign sign, ItemStack itemStack, String name, double price) {
        this.id = id;
        this.sign = sign;
        this.itemStack = itemStack.clone();
        this.name = name.trim();
        this.price = price;
    }

    public void updateSign() {
        sign.setLine(0, ChatUtils.fixColor("&2&lSKLEP"));
        sign.setLine(1, ChatUtils.fixColor(name));
        sign.setLine(2, ChatUtils.fixColor("x" + itemStack.getAmount()));
        sign.setLine(3, ChatUtils.fixColor("Cena: " + price + "TC"));
        sign.update();
    }

    public int getId() { return id; }
    public Location getSignLocation() { return sign.getLocation(); }
    public ItemStack getItemStack() { return itemStack; }
    public String getName() { return name; }
    public double getPrice() { return price; }

}
