package pl.trollcraft.pvp.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Configs;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)){
                ChatUtils.sendMessage(sender, "Komenda dla graczy.");
                return true;
            }

            Player player = (Player) sender;
            EconomyProfile profile = EconomyManager.get(player);
            if (profile == null) {
                EconomyManager.load(player);
                player.performCommand("money");
                return true;
            }

            double money = profile.getMoney();
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&7&l[PIENIADZE] &7" + money + " TC"));

            return true;

        }
        else {

            Player player = Bukkit.getPlayer(args[0]);
            if (player == null || !player.isOnline()) {

                YamlConfiguration conf = Configs.load("economy.yml");
                if (!conf.contains("economy." + args[0]))
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cBrak gracza."));
                else {
                    String money = conf.getString("economy." + args[0] + ".money");
                    ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7&l[" + args[0] + "] &7" + money + " TC"));
                }

            }
            else {
                EconomyProfile profile = EconomyManager.get(player);
                double money = profile.getMoney();
                ChatUtils.sendMessage(sender, ChatUtils.fixColor("&7&l[" + player.getName() + "] &7" + money + " TC"));
            }

        }

        return false;
    }
}
