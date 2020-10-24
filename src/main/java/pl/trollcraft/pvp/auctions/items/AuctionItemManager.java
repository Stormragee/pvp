package pl.trollcraft.pvp.auctions.items;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.auctions.Auction;
import pl.trollcraft.pvp.auctions.AuctionManager;
import pl.trollcraft.pvp.auctions.tasks.OfflineItemsGiver;
import pl.trollcraft.pvp.auctions.view.AuctionView;
import pl.trollcraft.pvp.help.Help;

import java.util.Iterator;

public class AuctionItemManager {

    public static void startTicker() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Auction auction = AuctionManager.getAuction();
                Iterator<AuctionItem> it = auction.getItems().iterator();

                AuctionItem item;
                while (it.hasNext()) {

                    item = it.next();
                    item.tick();

                    if (item.shouldDisappear()) {

                        it.remove();
                        AuctionView.getInstance().update();

                        Player player = item.getPlayer();
                        if (player == null || !player.isOnline())
                            new OfflineItemsGiver(item.getOwner(), item.getItemStack()).save();

                        else {
                            player.sendMessage(Help.color("&cPrzedmiot, ktory wystawiles przedawnil sie."));
                            player.getInventory().addItem(item.getItemStack());
                        }

                    }

                }

            }

        }.runTaskTimerAsynchronously(PVP.getPlugin(), 20*60, 20*60);

    }

}
