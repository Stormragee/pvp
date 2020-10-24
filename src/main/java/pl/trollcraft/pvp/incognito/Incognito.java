package pl.trollcraft.pvp.incognito;

import com.comphenix.protocol.wrappers.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.trollcraft.pvp.help.GeneralUtils;
import pl.trollcraft.pvp.help.packets.WrapperPlayServerEntityDestroy;
import pl.trollcraft.pvp.help.packets.WrapperPlayServerNamedEntitySpawn;
import pl.trollcraft.pvp.help.packets.WrapperPlayServerPlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Incognito {

    private static ArrayList<String> incognitoPlayers = new ArrayList<>();

    private static void set(Player player, String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getEntityId() == player.getEntityId()) continue;

            WrapperPlayServerPlayerInfo remove = new WrapperPlayServerPlayerInfo();
            remove.setAction(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
            remove.sendPacket(p);

            WrapperPlayServerPlayerInfo add = new WrapperPlayServerPlayerInfo();
            add.setAction(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
            WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player).withName(name);
            List<PlayerInfoData> data = new ArrayList<>();
            data.add(new PlayerInfoData(profile, 30, EnumWrappers.NativeGameMode.SURVIVAL,
                    WrappedChatComponent.fromText(name)));
            add.setData(data);
            add.sendPacket(p);

            WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
            destroy.setEntityIds(new int[] {player.getEntityId()});
            destroy.sendPacket(p);

            WrapperPlayServerNamedEntitySpawn spawn = new WrapperPlayServerNamedEntitySpawn();
            spawn.setEntityID(player.getEntityId());
            spawn.setPlayerUUID(player.getUniqueId());
            spawn.setX(player.getLocation().getBlockX() * 32);
            spawn.setY(player.getLocation().getBlockY() * 32);
            spawn.setZ(player.getLocation().getBlockZ() * 32);
            spawn.setYaw(player.getLocation().getYaw());
            spawn.setPitch(player.getLocation().getPitch());
            spawn.setMetadata(WrappedDataWatcher.getEntityWatcher(player));
            spawn.sendPacket(p);
        }
    }

    public static boolean isIncognito(Player player){
        return incognitoPlayers.contains(player.getName());
    }

    public static void on(Player player){
        incognitoPlayers.add(player.getName());
        randomize(player);
    }

    public static void randomize(Player player){
        set(player, GeneralUtils.randomString());
    }

    public static void off(Player player){
        incognitoPlayers.remove(player.getName());
        set(player, player.getName());
    }

}
