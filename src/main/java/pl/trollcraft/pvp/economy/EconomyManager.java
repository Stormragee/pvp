package pl.trollcraft.pvp.economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.economy.events.AsyncEconomyLoadEvent;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class EconomyManager {

    private static ArrayList<EconomyProfile> profiles = new ArrayList<>();

    public static void register(EconomyProfile profile) { profiles.add(profile); }

    public static void unregister(EconomyProfile profile) {

        new BukkitRunnable() {

            @Override
            public void run() {
                save(profile);
                profiles.remove(profile);
            }

        }.runTaskAsynchronously(PVP.getPlugin());


    }

    public static void save(EconomyProfile profile) {

        new BukkitRunnable() {

            @Override
            public void run() {
                YamlConfiguration conf = Configs.load("economy.yml");
                conf.set("economy." + profile.getPlayer().getName() + ".money", profile.getMoney());
                Configs.save(conf, "economy.yml");
            }

        }.runTaskAsynchronously(PVP.getPlugin());

    }

    public static void globalSave() {
        YamlConfiguration conf = Configs.load("economy.yml");
        for (EconomyProfile profile : profiles)
            conf.set("economy." + profile.getPlayer().getName() + ".money", profile.getMoney());
        Configs.save(conf, "economy.yml");
    }

    public static void load(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {

                YamlConfiguration conf = Configs.load("economy.yml");
                EconomyProfile profile;

                if (!conf.contains("economy." + player.getName()))
                    profile = new EconomyProfile(player, 0);
                else {
                    double money = conf.getDouble("economy." + player.getName() + ".money");
                    profile = new EconomyProfile(player, money);
                }

                register(profile);
                Bukkit.getPluginManager().callEvent(new AsyncEconomyLoadEvent(profile));

            }

        }.runTaskAsynchronously(PVP.getPlugin());

    }

    public static EconomyProfile get(Player player) {
        int id = player.getEntityId();
        for (EconomyProfile profile : profiles)
            if (profile.getPlayer().getEntityId() == id) return profile;
        return null;
    }

    public static EconomyProfile get(String player) {
        for (EconomyProfile profile : profiles)
            if (profile.getPlayer().getName().equals(player)) return profile;
        return null;
    }

    public static ArrayList<EconomyProfile> getProfiles() { return profiles; }

}
