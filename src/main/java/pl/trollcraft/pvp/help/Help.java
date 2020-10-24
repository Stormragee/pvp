package pl.trollcraft.pvp.help;

import org.bukkit.ChatColor;

public class Help {

    private static String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    // -------- -------- -------- --------

    public static String color(String message) {
        return message.replace('&', ChatColor.COLOR_CHAR);
    }

    public static String randomString(int n) {

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
