package pl.trollcraft.pvp.auctions;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.pvp.auctions.items.AuctionItem;
import pl.trollcraft.pvp.auctions.view.AuctionView;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class AuctionManager {

    private static Auction auction;

    public static void save() {
        YamlConfiguration conf = Configs.load("auction.yml");
        auction.getItems().forEach( item -> {

            conf.set("auction." + item.getId() + ".owner", item.getOwner());
            conf.set("auction." + item.getId() + ".price", item.getPrice());
            conf.set("auction." + item.getId() + ".itemStack", item.getItemStack());
            conf.set("auction." + item.getId() + ".toDisappear", item.getToDisappear());

        } );
        Configs.save(conf, "auction.yml");
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("auction.yml");

        ArrayList<AuctionItem> items = new ArrayList<>();

        conf.getConfigurationSection("auction").getKeys(false).forEach(id -> {

            String owner = conf.getString("auction." + id + ".owner");
            double price = conf.getDouble("auction." + id + ".price");
            ItemStack itemStack = conf.getItemStack("auction." + id + ".itemStack");
            int toDisappear = conf.getInt("auction." + id + ".toDisappear");

            items.add(new AuctionItem(id, owner, itemStack, price, toDisappear));

        });

        auction = new Auction(items);
        new AuctionView(auction);
    }

    public static Auction getAuction() { return auction; }

}
