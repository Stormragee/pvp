package pl.trollcraft.pvp.auctions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;;
import pl.trollcraft.pvp.auctions.items.AuctionItem;
import pl.trollcraft.pvp.auctions.view.AuctionView;

import java.util.List;
import java.util.stream.Collectors;

public class Auction {

    public enum Response {
        OK, NO_SUCH_PAGE;
    }

    private List<AuctionItem> items;

    public Auction (List<AuctionItem> items) {
        this.items = items;
    }

    public void forceAdd(Player player, ItemStack itemStack, double price) {
        AuctionItem item = new AuctionItem(player.getName(), itemStack, price, 60);
        items.add(item);
    }

    public boolean add(Player player, ItemStack itemStack, double price) {

        AuctionItem item = new AuctionItem(player.getName(), itemStack, price, 12 * 3600);

        long put = items.stream()
                .filter( it -> it.getOwner().equalsIgnoreCase(player.getName()) )
                .count();

        if (put > 5) return false;

        items.add(item);
        AuctionView.getInstance().update();

        return true;
    }

    public boolean remove(AuctionItem item) {
        return items.removeIf( it -> it.equals(item) );
    }

    public List<AuctionItem> getItems(Player player) {
        return items.stream()
            .filter( it -> it.getOwner().equals(player.getName()) )
            .collect(Collectors.toList());
    }

    public List<AuctionItem> getItems() { return items; }

}
