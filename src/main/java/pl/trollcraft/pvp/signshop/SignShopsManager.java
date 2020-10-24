package pl.trollcraft.pvp.signshop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.ItemSerializer;

import java.util.ArrayList;

public class SignShopsManager {

    private static ArrayList<SignShop> signShops = new ArrayList<>();

    public static void register(SignShop signShop) { signShops.add(signShop); }

    public static void remove(SignShop signShop) {
        signShops.remove(signShop);
        YamlConfiguration conf = Configs.load("signshops.yml");
        conf.set("signshops." + signShop.getId(), null);
        Configs.save(conf, "signshops.yml");
    }

    public static void save(SignShop signShop) {
        YamlConfiguration conf = Configs.load("signshops.yml");

        Location loc = signShop.getSignLocation();

        conf.set("signshops." + signShop.getId() + ".name", signShop.getName());
        conf.set("signshops." + signShop.getId() + ".location.world", loc.getWorld().getName());
        conf.set("signshops." + signShop.getId() + ".location.x", loc.getX());
        conf.set("signshops." + signShop.getId() + ".location.y", loc.getY());
        conf.set("signshops." + signShop.getId() + ".location.z", loc.getZ());

        conf.set("signshops." + signShop.getId() + ".itemStack", signShop.getItemStack());
        //ItemSerializer.serialize(signShop.getItemStack(), conf, "signshops." + signShop.getId() + ".itemStack");

        conf.set("signshops." + signShop.getId() + ".price", signShop.getPrice());

        Configs.save(conf, "signshops.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("signshops.yml");
        conf.getConfigurationSection("signshops").getKeys(false).forEach( id -> {
            String name = conf.getString("signshops." + id + ".name");

            World world;
            double x, y, z;
            world = Bukkit.getWorld(conf.getString("signshops." + id + ".location.world"));
            x = conf.getDouble("signshops." + id + ".location.x");
            y = conf.getDouble("signshops." + id + ".location.y");
            z = conf.getDouble("signshops." + id + ".location.z");

            Location location = new Location(world, x, y, z);

            if (!(location.getBlock().getState() instanceof Sign)) return;

            Sign sign = (Sign) location.getBlock().getState();
            ItemStack itemStack = conf.getItemStack("signshops." + id + ".itemStack");
            //ItemSerializer.deserialize(conf, "signshops." + id + ".itemStack");
            double price = conf.getDouble("signshops." + id + ".price");
            register(new SignShop(Integer.parseInt(id), sign, itemStack, name, price));
        } );
    }

    public static SignShop get(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Location loc;
        for (SignShop signShop : signShops) {
            loc = signShop.getSignLocation();
            if (loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z) return signShop;
        }

        return null;
    }

}
