package pl.trollcraft.pvp.help.tasks;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.Configs;
import pl.trollcraft.pvp.help.Help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class OfflineTask {

    private static final ArrayList<OfflineTask> offlineTasks = new ArrayList<>();

    private String id;
    private String playerName;

    public OfflineTask() { offlineTasks.add(this); }

    public OfflineTask (String id, String playerName) {
        this.id = id;
        this.playerName = playerName;
        offlineTasks.add(this);
    }

    public OfflineTask (String playerName) {
        this.id = Help.randomString(32);
        this.playerName = playerName;
        offlineTasks.add(this);
    }

    protected String getId() { return id; }
    protected String getPlayerName() { return playerName; }

    protected void setId(String id) { this.id = id; }
    protected void setPlayerName(String playerName) { this.playerName = playerName; }

    public abstract boolean execute(Player player);
    public abstract void failure(Player player);

    public abstract void save();
    public abstract void load(String id);

    public void dispose() {
        Bukkit.getConsoleSender().sendMessage("Disposing " + id);

        YamlConfiguration conf = Configs.load("tasks.yml");
        conf.set("tasks." + getId(), null);
        Configs.save(conf, "tasks.yml");
    }

    public static ArrayList<OfflineTask> getOfflineTasks() {
        return offlineTasks;
    }

    @Deprecated
    public static List<OfflineTask> get(String playerName) {
        return offlineTasks.stream()
                .filter( task -> task.playerName.equals(playerName) )
                .collect(Collectors.toList());
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("tasks.yml");
        conf.getConfigurationSection("tasks").getKeys(false).forEach( id -> {

            String className = conf.getString("tasks." + id + ".class");

            try {

                Class<?> taskClass = Class.forName(className);
                OfflineTask task = (OfflineTask) taskClass.newInstance();
                task.load(id);

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

        } );
    }

}
