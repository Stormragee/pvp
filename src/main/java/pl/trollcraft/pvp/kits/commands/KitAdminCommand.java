package pl.trollcraft.pvp.kits.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.kits.Kit;
import pl.trollcraft.pvp.kits.KitsManager;

import java.util.ArrayList;

public class KitAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jedynie dla graczy online."));
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7Komendy KITow:"));
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/kitadmin new <nazwa> <uprawnienie> <czysc eq> <cooldown>"));
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/kitadmin remove <nazwa>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("new")){

            if (args.length != 5){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/kitadmin new <nazwa> <uprawnienie> <czysc eq> <cooldown>"));
                return true;
            }

            PlayerInventory inv = ((Player) sender).getInventory();

            String name = args[1];
            String permission = args[2];
            boolean clearInventory = Boolean.parseBoolean(args[3]);
            long cooldown = Long.parseLong(args[4]);

            ItemStack[] armor = inv.getArmorContents();
            ArrayList<ItemStack> items = new ArrayList<>();
            for (ItemStack i : inv){
                if (i == null || i.getType() == Material.AIR) continue;
                items.add(i);
            }

            Kit kit = new Kit(name, permission, clearInventory, cooldown, armor, items);
            KitsManager.register(kit);
            KitsManager.save(kit);

            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUtworzono i zapisano nowy kit."));

        }
        else if (args[0].equalsIgnoreCase("remove")) {

            if (args.length != 2){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/kitadmin remove <nazwa>"));
                return true;
            }

            Kit kit = KitsManager.getKit(args[0]);
            KitsManager.remove(kit);

            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUsunieto kit &e" + kit.getName()));

        }

        return true;
    }
}
