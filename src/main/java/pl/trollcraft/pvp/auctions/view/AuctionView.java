package pl.trollcraft.pvp.auctions.view;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.auctions.Auction;
import pl.trollcraft.pvp.auctions.items.AuctionItem;
import pl.trollcraft.pvp.auctions.tasks.OfflineTransaction;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.gui.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AuctionView {

    private static AuctionView instance;

    private ArrayList<Viewer> viewers;
    private Auction auction;

    public AuctionView (Auction observed) {
        instance = this;
        viewers = new ArrayList<>();
        this.auction = observed;
    }

    public static AuctionView getInstance() {
        return instance;
    }

    private void addNavigation(Player player, GUI gui, int page, int pages) {

        ItemStack noPage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);

        if (page > 0)
            gui.addItem(45,
                    new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13),
                    e -> {
                        e.setCancelled(true);
                        show(player, page-1);
                    });
        else
            gui.addItem(45, noPage.clone(), e -> e.setCancelled(true));

        if (page + 1 <= pages)
            gui.addItem(53,
                    new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13),
                    e -> {
                        e.setCancelled(true);
                        show(player, page+1);
                    });
        else
            gui.addItem(53, noPage.clone(), e -> e.setCancelled(true));

        ItemStack mine = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = mine.getItemMeta();
        itemMeta.setDisplayName(Help.color("&aMoje przedmioty"));
        mine.setItemMeta(itemMeta);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);

        for (int i = 46 ; i < 49 ; i++ )
            gui.addItem(i, glass.clone(), e -> e.setCancelled(true));

        for (int i = 50 ; i < 53 ; i++ )
            gui.addItem(i, glass.clone(), e -> e.setCancelled(true));

        gui.addItem(49, mine, e -> {
            e.setCancelled(true);
            showPanel(player, page);
        });
    }

    public Auction.Response show(Player player, int page) {
        List<AuctionItem> items = auction.getItems();
        int pages = items.size() / (54-9);

        if (page < 0 || page > pages) return Auction.Response.NO_SUCH_PAGE;

        Viewer viewer = getViewer(player);
        boolean updating = true;

        if (viewer == null) {
            updating = false;
            GUI gui = new GUI(54, Help.color("&2&lAukcja"));
            gui.setAutoClose(true);
            viewer = new Viewer(player, 0, gui);
            viewers.add(viewer);
        }
        else viewer.setPage(page);

        GUI gui = viewer.getGUI();
        gui.clear();

        addNavigation(player, gui, page, pages);

        int d = page * (54-9);

        for (int i = d ; i < d+54-9 ; i++) {

            if (items.size() <= i) break;

            AuctionItem item = items.get(i);

            if (item.getOwner().equals(player.getName())) continue;

            gui.addItem( (i == 0 || d == 0) ? i : i%d, item.getIconItemStack(), e -> {
                e.setCancelled(true);

                EconomyProfile buyerProfile = EconomyManager.get(player);

                if (!buyerProfile.has(item.getPrice())) {
                    player.sendMessage(Help.color("&cBrak wystarczajacej ilosci pieniedzy na zakup."));
                    return;
                }

                if (!player.getInventory().addItem(item.getItemStack()).isEmpty()){
                    player.sendMessage(Help.color("&cTwoj ekwipunek jest pelny!"));
                    return;
                }

                if (item.isSold()) {
                    player.sendMessage(Help.color("&cPrzedmiot zostal juz sprzedany."));
                    return;
                }

                item.setSold(true);
                buyerProfile.take(item.getPrice());
                //item.give(player);
                auction.remove(item);
                player.sendMessage(Help.color("&aZakupiono."));

                // OFFLINE item owner
                Player seller = item.getPlayer();
                if (seller == null || !seller.isOnline())
                    new OfflineTransaction(item.getOwner(), player.getName(), item.getPrice()).save();
                else {
                    EconomyProfile sellerProfile = EconomyManager.get(seller);
                    sellerProfile.give(item.getPrice());
                    seller.sendMessage(Help.color("&aGracz &e" + player.getName() + " &azakupil na aukcji Twoj przedmiot."));
                }

                item.dispose();
                update();

            });

        }

        if (updating) gui.update();
        else gui.open(player);

        return Auction.Response.OK;
    }

    private void addPanelNavigation(Player player, GUI gui, int page) {

        ItemStack back = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        ItemMeta itemMeta = back.getItemMeta();
        itemMeta.setDisplayName(Help.color("&aPowrot na aukcje"));
        back.setItemMeta(itemMeta);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);

        for (int i = 45 ; i < 49 ; i++ )
            gui.addItem(i, glass.clone(), e -> e.setCancelled(true));

        for (int i = 50 ; i < 54 ; i++ )
            gui.addItem(i, glass.clone(), e -> e.setCancelled(true));

        gui.addItem(49, back, e -> {
            e.setCancelled(true);
            show(player, page);
        });

    }

    public void showPanel(Player player, int page) {

        Viewer viewer = getViewer(player);
        if (viewer == null) {
            viewer = new Viewer(player, 0, new GUI(54, Help.color("&2&lAukcja")));
            viewers.add(viewer);
        }
        GUI gui = viewer.getGUI();
        gui.clear();

        addPanelNavigation(player, gui, page);

        List<AuctionItem> items = auction.getItems(player);

        for (int i = 0 ; i < items.size() ; i++) {
            AuctionItem item = items.get(i);
            gui.addItem(i, item.getPanelIcon(), e -> {
                e.setCancelled(true);
                auction.remove(item);
                player.getInventory().addItem(item.getItemStack());
                show(player, page);
            });
        }

    }

    public void update() {
        Iterator<Viewer> it = viewers.iterator();
        Viewer viewer;

        while (it.hasNext()) {
            viewer = it.next();
            if (show(viewer.getPlayer(), viewer.getPage()) == Auction.Response.NO_SUCH_PAGE)
                show(viewer.getPlayer(), viewer.getPage() - 1);
        }
    }

    public void close(Viewer viewer) {
        Bukkit.getConsoleSender().sendMessage("Closing " + viewer.getPlayer().getName());
        viewers.remove(viewer);
    }

    public Viewer getViewer(Player player) {
        int id = player.getEntityId();

        for (Viewer v : viewers)
            if (v.getPlayer().getEntityId() == id)
                return v;

        return null;
    }


}
