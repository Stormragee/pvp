package pl.trollcraft.pvp.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.data.events.AsyncWarriorLoadEvent;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;

public class WarriorsManager {

    private static ArrayList<Warrior> warriors = new ArrayList<>();

    public static void register(Warrior warrior) { warriors.add(warrior); }
    public static void unregister(Warrior warrior) { warriors.remove(warrior); }

    public static void save(Warrior warrior) {

        new BukkitRunnable() {

            @Override
            public void run() {
                YamlConfiguration conf = Configs.load("warriors.yml");
                String name = warrior.getPlayer().getName();
                conf.set("warriors." + name + ".level", warrior.getLevel());
                conf.set("warriors." + name + ".kills", warrior.getKills());
                conf.set("warriors." + name + ".killStreak", warrior.getKillStreak());
                conf.set("warriors." + name + ".deaths", warrior.getDeaths());
                Configs.save(conf, "warriors.yml");
            }

        }.runTaskAsynchronously(PVP.getPlugin());

    }

    public static void globalSave() {
        String name;
        YamlConfiguration conf = Configs.load("warriors.yml");
        for (Warrior warrior : warriors){
            name = warrior.getPlayer().getName();
            conf.set("warriors." + name + ".level", warrior.getLevel());
            conf.set("warriors." + name + ".kills", warrior.getKills());
            conf.set("warriors." + name + ".killStreak", warrior.getKillStreak());
            conf.set("warriors." + name + ".deaths", warrior.getDeaths());
        }
        Configs.save(conf, "warriors.yml");
    }

    public static void load(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                YamlConfiguration conf = Configs.load("warriors.yml");
                String name = player.getName();

                if (!conf.contains("warriors." + name)) register(new Warrior(player, 1, 0, 0, 0));
                else {

                    int level = conf.getInt("warriors." + name + ".level");
                    int kills = conf.getInt("warriors." + name + ".kills");

                    int killStreak = 0;
                    if (conf.contains("warriors." + name + ".killStreak"))
                        killStreak = conf.getInt("warriors." + name + ".killStreak");

                    int deaths = conf.getInt("warriors." + name + ".deaths");

                    Warrior warrior = new Warrior(player, level, kills, killStreak, deaths);
                    register(warrior);

                    Bukkit.getPluginManager().callEvent(new AsyncWarriorLoadEvent(warrior));

                }
            }

        }.runTaskAsynchronously(PVP.getPlugin());

    }

    public static Warrior get(Player player) {
        int id = player.getEntityId();
        for (Warrior warrior : warriors)
            if (warrior.getPlayer().getEntityId() == id) return warrior;
        return null;
    }

}
