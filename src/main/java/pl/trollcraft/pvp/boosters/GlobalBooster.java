package pl.trollcraft.pvp.boosters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.ChatUtils;

public class GlobalBooster extends Booster {

    public GlobalBooster(int seconds, double bonus) {
        super(seconds, bonus);
        globalBoosters.add(this);
    }

    @Override
    public void end() {
        globalBoosters.remove(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ChatUtils.sendMessage(player, ChatUtils.fixColor("&7Globalny Booster pieniezny wyczerpal sie..."));
        }
    }
}
