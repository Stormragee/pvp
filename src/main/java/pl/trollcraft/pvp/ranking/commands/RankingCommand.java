package pl.trollcraft.pvp.ranking.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.gui.GUI;
import pl.trollcraft.pvp.rankings.DynamicPosition;
import pl.trollcraft.pvp.rankings.Position;
import pl.trollcraft.pvp.rankings.Ranking;

import java.util.Arrays;
import java.util.List;

public class RankingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;

        GUI gui = new GUI(3*9, Help.color("&a&lRankingi"));
        gui.setAutoClose(true);

        List<Ranking> rankings = PVP.getPlugin().getRankingsManager().getRankings();
        Ranking r;
        Position p, next, prev;

        int pos;

        int size = rankings.size();

        ItemStack itemStack;
        ItemMeta meta;

        for (int i = 0 ; i < rankings.size() ; i++) {

            r = rankings.get(i);
            DynamicPosition dp = r.get(player.getName()).get();

            pos = dp.getIndex();
            p = dp.getPosition();

            itemStack = new ItemStack(Material.DIAMOND);
            meta = itemStack.getItemMeta();
            meta.setDisplayName(r.getTitle());

            if (pos == 0) {
                meta.setLore(Arrays.asList(
                        "",
                        Help.color("&aJestes MISTRZEM!"),
                        Help.color("&aZajmujesz &eI miejsce!"),
                        ""
                ));
            }
            else {
                pos++;

                meta.setLore(Arrays.asList(
                        "",
                        Help.color("&aZajmujesz &e" + pos + " miejsce."),
                        ""
                ));

            }

            itemStack.setItemMeta(meta);
            player.sendMessage((9/size+1 + i*size + 9) + "");

            if (size % 2 == 0)
                gui.addItem(9/size-1 + i + 9 + zero(i), itemStack, e -> e.setCancelled(true));
            else
                gui.addItem(9/size+1 + i*size + 9 - 3, itemStack, e -> e.setCancelled(true));

        }

        gui.open(player);

        return true;
    }

    private int zero(int a) {
        return a == 0 ? 0 : 1;
    }

}
