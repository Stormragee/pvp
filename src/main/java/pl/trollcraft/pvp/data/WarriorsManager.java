package pl.trollcraft.pvp.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.pvp.PVP;
import pl.trollcraft.pvp.data.events.AsyncWarriorLoadEvent;
import pl.trollcraft.pvp.help.Configs;

import java.util.ArrayList;
import java.util.logging.Level;

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
                conf.set("warriors." + name + ".highestKillStreak", warrior.getHighestKillStreak());
                conf.set("warriors." + name + ".killPoints", warrior.getKillPoints());
                Configs.save(conf, "warriors.yml");
            }

        }.runTaskAsynchronously(PVP.getPlugin());

    }

    public static void globalSave() {
        String name;
        YamlConfiguration conf = Configs.load("warriors.yml");
        assert conf != null;

        for (Warrior warrior : warriors){
            name = warrior.getPlayer().getName();
            conf.set("warriors." + name + ".level", warrior.getLevel());
            conf.set("warriors." + name + ".kills", warrior.getKills());
            conf.set("warriors." + name + ".killStreak", warrior.getKillStreak());
            conf.set("warriors." + name + ".deaths", warrior.getDeaths());
            conf.set("warriors." + name + ".highestKillStreak", warrior.getHighestKillStreak());
            conf.set("warriors." + name + ".killPoints", warrior.getKillPoints());
        }
        Configs.save(conf, "warriors.yml");
    }

    public static void load(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {
                YamlConfiguration conf = Configs.load("warriors.yml");
                String name = player.getName();

                Bukkit.getLogger().log(Level.INFO, "Async loading...");

                assert conf != null;
                if (!conf.contains("warriors." + name)) {
                    Warrior warrior = new Warrior(player, 1, 0, 0, 0, 0, 500);
                    register(warrior);
                    Bukkit.getPluginManager().callEvent(new AsyncWarriorLoadEvent(warrior));
                }
                else {

                    int level = conf.getInt("warriors." + name + ".level");
                    int kills = conf.getInt("warriors." + name + ".kills");

                    int killStreak = 0;
                    if (conf.contains("warriors." + name + ".killStreak"))
                        killStreak = conf.getInt("warriors." + name + ".killStreak");

                    int deaths = conf.getInt("warriors." + name + ".deaths");

                    int highestKillStreak;
                    if (conf.contains("warriors." + name + ".highestKillStreak"))
                        highestKillStreak = conf.getInt("warriors." + name + ".highestKillStreak");
                    else
                        highestKillStreak = 0;

                    int killPoints;
                    if (conf.contains("warriors." + name + ".killPoints"))
                        killPoints = conf.getInt("warriors." + name + ".killPoints");
                    else
                        killPoints = 2 * kills;

                    Warrior warrior = new Warrior(player, level, kills, killStreak, deaths, highestKillStreak, killPoints);
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
