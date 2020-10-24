package pl.trollcraft.pvp.help;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.clans.Clan;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.data.Warrior;
import pl.trollcraft.pvp.data.WarriorsManager;

import java.text.DecimalFormat;

public class PlaceholderManager extends PlaceholderExpansion {

    private static DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "pvp";
    }

    @Override
    public String getAuthor() {
        return "SkepsonSk";
    }

    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public String onPlaceholderRequest(Player player, String iden) {

        if (iden.contains("world")) {
            String[] data = iden.split("_");
            World world = Bukkit.getWorld(data[1]);
            if (world == null) return "ERR";
            return String.valueOf(world.getPlayers().size());
        }

        Warrior warrior = WarriorsManager.get(player);
        if (warrior == null) return "null";

        if (iden.contains("level"))
            return String.valueOf(warrior.getLevel());
        else if (iden.contains("kills"))
            return String.valueOf(warrior.getKills());
        else if (iden.contains("deaths"))
            return String.valueOf(warrior.getDeaths());

        else if (iden.contains("kdr"))
            return format.format(warrior.getKdr());
        else if (iden.contains("topromotion"))
            return String.valueOf(warrior.getKillsToPromotion());
        else if (iden.contains("clan")) {
            Clan clan = ClansManager.get(player);
            if (clan == null) return "";
            return clan.getName();
        }

        return "n/a";
    }

}
