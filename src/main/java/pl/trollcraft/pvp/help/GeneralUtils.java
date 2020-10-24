package pl.trollcraft.pvp.help;

import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Random;

public class GeneralUtils {

    private static Random random = new Random();
    private static DecimalFormat format = new DecimalFormat("0.00");

    public static boolean isInArray(String a, Collection<? extends String> array) {
        for (String s : array)
            if (s.equals(a)) return true;
        return false;
    }

    public static int calcMove(Location loc){
        return loc.getBlockX() + loc.getBlockY() + loc.getBlockZ();
    }

    public static String format(double d) {
        return format.format(d);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int parseInt(String num) {
        int val;
        try {
            val = Integer.parseInt(num);
        } catch (NumberFormatException e){
            return 0;
        }
        return val;
    }

    public static double parseDouble(String num) {
        double val;
        try {
            val = Double.parseDouble(num);
        } catch (NumberFormatException e){
            return 0d;
        }
        return val;
    }

    public static Location getHome(Plot plot) {
        com.intellectualcrafters.plot.object.Location loc = plot.getHome();
        World world = Bukkit.getWorld(loc.getWorld());
        int x = loc.getX();
        int y = loc.getY();
        int z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String randomString(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

}
