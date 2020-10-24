package pl.trollcraft.pvp.warp.portals;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.warp.Warp;

import java.util.Set;

public class PortalSetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cKomenda jedynie dla graczy."));
            return true;
        }

        if (!sender.hasPermission("pvp.admin")){
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak uprawnien."));
            return true;
        }

        if (args.length == 0) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&e/portal <new|a|b>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("new")){

            if (args.length != 3){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/portal new <nazwa> <warp>"));
                return true;
            }

            Portal portal = PortalsManager.getPortal(args[1]);
            if (portal != null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cPortal o takiej nazwie juz istnieje."));
                return true;
            }

            Warp warp = Warp.get(args[2]);
            if (warp == null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cTaki warp nie istnieje."));
                return true;
            }

            portal = new Portal(args[1], warp, null, null);
            PortalsManager.register(portal);

            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUtworzono portal."));

        }

        else if (args[0].equalsIgnoreCase("a")){

            if (args.length != 2){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/portal a <nazwa>"));
                return true;
            }

            Portal portal = PortalsManager.getPortal(args[1]);
            if (portal == null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cPortal o takiej nazwie nie istnieje."));
                return true;
            }

            Player player = (Player) sender;
            portal.setA(player.getTargetBlock( (Set<Material>) null, 5 ).getLocation());

            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUstawiono a."));

        }
        else if (args[0].equalsIgnoreCase("b")){

            if (args.length != 2){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/portal a <nazwa>"));
                return true;
            }

            Portal portal = PortalsManager.getPortal(args[1]);
            if (portal == null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cPortal o takiej nazwie nie istnieje."));
                return true;
            }

            Player player = (Player) sender;
            portal.setB(player.getTargetBlock( (Set<Material>) null, 5 ).getLocation());

            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUstawiono b."));

        }
        else if (args[0].equalsIgnoreCase("save")){

            if (args.length != 2){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/portal save <nazwa>"));
                return true;
            }

            Portal portal = PortalsManager.getPortal(args[1]);
            if (portal == null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cPortal o takiej nazwie nie istnieje."));
                return true;
            }

            PortalsManager.save(portal);
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aZapisano portal."));

        }

        else if (args[0].equalsIgnoreCase("remove")) {

            if (args.length != 2) {
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/portal remove <nazwa>"));
                return true;
            }

            Portal portal = PortalsManager.getPortal(args[1]);
            if (portal == null){
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cPortal o takiej nazwie nie istnieje."));
                return true;
            }

            PortalsManager.remove(portal);
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("&aUsunieto portal &e" + portal.getName()));

        }

        return true;

    }
}
