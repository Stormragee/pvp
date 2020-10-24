package pl.trollcraft.pvp.warehouses;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.GeneralUtils;

import java.util.Collection;
import java.util.Random;

public class WarehouseCommand implements CommandExecutor {

    private final Random RAND = new Random();

    private final long WEEK = 1000 * 60 * 60 * 24 * 7;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jedynie dla graczy."));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {

            if (!WarehousesManager.openWarehouses(player))
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNie posiadasz magazynow!\n&7Mozesz wynajac magazyn" +
                        "uzywajac komendy &e/magazyn wynajmij <sloty> <nazwa>"));
            return true;

        }
        else if (args[0].equalsIgnoreCase("wynajmij")){

            if (args.length < 3){
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/magazyn wynajmij <sloty> <nazwa>\n" +
                        "&7Sloty: &e9,18,27,36,45,54."));
                return true;
            }

            int max = 3;
            if (player.hasPermission("pvp.svip")) max = 9;
            else if (player.hasPermission("pvp.vip")) max = 6;

            Collection<Warehouse> warehouses = WarehousesManager.getWarehouses(player);
            if (warehouses.size() >= max)
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cPosiadasz juz maksymalna liczbe magazynow."));
            else {
                int slots = GeneralUtils.parseInt(args[1]);
                if (slots <= 0) {
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cZla liczba slotow."));
                    return true;
                }

                if (slots % 9 != 0 || slots < 9 || slots > 54){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cZla liczba slotow.\n&7Dostepne: 9, 18, 27, 36, 45, 54."));
                    return true;
                }

                double price = slots * WarehousesManager.getSlotPrice();
                EconomyProfile profile = EconomyManager.get(player);
                if (!profile.take(price)){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak srodkow! Koszt wynajmu takiego magazynu wynosi &e" +
                            price + "TC."));
                    return true;
                }

                StringBuilder name = new StringBuilder();
                for (int i = 2 ; i < args.length ; i++) {
                    name.append(args[i]);
                    name.append(' ');
                }

                Warehouse warehouse = new Warehouse(RAND.nextInt(), name.toString(), slots, System.currentTimeMillis() + WEEK);
                WarehousesManager.register(player, warehouse);
                WarehousesManager.save(player);
                ChatUtils.sendMessage(player, "&aWynajeto magazyn na tydzien.\n&aAby otworzyc magazyn, uzyj komendy &e/magazyn");
            }

        }
        else
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Magazyny\n" +
                    "&e/magazyn -&7 otwiera Twoje magazyny," +
                    "&e/magazyn wynajmij <sloty> - &7wynajmuje magazyn"));

        return false;
    }
}
