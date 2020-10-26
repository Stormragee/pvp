package pl.trollcraft.pvp.ranking.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.pvp.help.Help;
import pl.trollcraft.pvp.help.gui.GUI;
import pl.trollcraft.pvp.ranking.Ranking;
import pl.trollcraft.pvp.ranking.RankingManager;
import pl.trollcraft.pvp.ranking.core.Position;

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

        List<Ranking> rankings = RankingManager.getRankings();
        Ranking r;
        Position p, next, prev;

        int pos;

        int size = rankings.size();

        ItemStack itemStack;
        ItemMeta meta;

        for (int i = 0 ; i < rankings.size() ; i++) {

            r = rankings.get(i);
            pos = r.getIndex(player);
            p = r.get(player);

            itemStack = new ItemStack(Material.DIAMOND);
            meta = itemStack.getItemMeta();
            meta.setDisplayName(r.getTitle());

            if (pos == 0) {
                prev = p.getPrevious();
                meta.setLore(Arrays.asList(
                        "",
                        Help.color("&aJestes MISTRZEM!"),
                        Help.color("&aZajmujesz &eI miejsce!"),
                        "",
                        Help.color("&aZa Toba jest: &e" + prev.getName()),
                        ""
                ));
            }
            else if (p.getPrevious() == null) {

                next = p.getNext();

                meta.setLore(Arrays.asList(
                        "",
                        Help.color("&aZajmujesz &eostatnie miejsce!"),
                        "",
                        Help.color("&aPrzed Toba jest: &e" + next.getName())
                ));
            }
            else {

                next = p.getNext();
                prev = p.getPrevious();
                pos++;

                meta.setLore(Arrays.asList(
                        "",
                        Help.color("&aZajmujesz &e" + pos + " miejsce."),
                        "",
                        Help.color("&aPrzed Toba jest: &e" + next.getName() + "(" + (pos-1) + ")"),
                        Help.color("&aZa Toba jest: &e" + prev.getName() + "(" + (pos+1) + ")"),
                        ""
                ));

            }

            itemStack.setItemMeta(meta);

            gui.addItem(9/size-1 + i + 9 + zero(i), itemStack, e -> e.setCancelled(true));

        }

        gui.open(player);

        return true;
    }

    private int zero(int a) {
        return a == 0 ? 0 : 1;
    }

}
