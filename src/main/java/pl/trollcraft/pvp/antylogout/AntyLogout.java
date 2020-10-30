package pl.trollcraft.pvp.antylogout;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class AntyLogout {

    public enum Response {
        ADDED,
        REMOVED,
        NOT_IN_COMBAT,
        UPDATED;
    }

    private long combatInterval;
    private HashMap<Player, Long> combats;

    private AntyLogout(long combatInterval) {
        this.combatInterval = combatInterval;
        combats = new HashMap<>();
    }

    private static AntyLogout INSTANCE;

    public static void newInstance(long combatInterval) {
        INSTANCE = new AntyLogout(combatInterval);
    }

    public static AntyLogout getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the combat map.
     *
     * @return combat map.
     */
    public HashMap<Player, Long> getCombats() {
        return combats;
    }

    /**
     * Marks the player he's in combat.
     *
     * @param player player, who is being added.
     * @return true if player was added.
     */
    public Response inCombat(Player player) {

        if (combats.containsKey(player)) {
            combats.replace(player, System.currentTimeMillis() + combatInterval);
            return Response.UPDATED;
        }

        combats.put(player, System.currentTimeMillis() + combatInterval);
        return Response.ADDED;

    }

    /**
     * Marks the player he's not in combat.
     * anymore.
     *
     * @param player player, who is being removed.
     * @return true if player was removed.
     */
    public Response outCombat(Player player) {
        if (!combats.containsKey(player))
            return Response.NOT_IN_COMBAT;

        combats.remove(player);
        return Response.REMOVED;
    }

    /**
     * Handles the logout situation.
     *
     * @param player
     * @return true if player was in combat.
     */
    public boolean logout(Player player) {
        if (!combats.containsKey(player))
            return false;

        long now = System.currentTimeMillis();
        long inCombat = combats.get(player);

        return now <= inCombat;
    }

    /**
     * Removes all the data from
     * the combat map.
     */
    public void flush() {
        combats.clear();
    }

}
