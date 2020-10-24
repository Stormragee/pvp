package pl.trollcraft.pvp.clans.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.pvp.clans.Clan;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.data.Warrior;
import pl.trollcraft.pvp.data.WarriorsManager;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.economy.EconomyProfile;
import pl.trollcraft.pvp.help.ChatUtils;
import pl.trollcraft.pvp.help.Help;

public class ClansCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            ChatUtils.sendMessage(sender, ChatUtils.fixColor("Komenda jedynie dla graczy."));
            return true;
        }

        Player player = (Player) sender;
        Clan clan = ClansManager.get(player);

        if (clan == null){

            if (args.length == 0){
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNie masz klanu!\n" +
                        "&eMozesz zalozyc klan uzywajac komendy /klan zaloz <nazwa>\n" +
                        "&7Aby zalozyc klan, potrzebujesz " + ClansManager.getClanMoney() + "TC &7oraz musisz zabic" +
                        "&e " + ClansManager.getClanKills() + " graczy."));
                return true;
            }
            else if (args[0].equalsIgnoreCase("zaloz")) {

                if (args.length < 2){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/klan zaloz <nazwa>"));
                    return true;
                }

                EconomyProfile profile = EconomyManager.get(player);
                Warrior warrior = WarriorsManager.get(player);

                StringBuilder name = new StringBuilder();
                for (int i = 1; i < args.length ; i++){
                    name.append(args[i]);
                    name.append(' ');
                }

                clan = ClansManager.get(name.toString());
                if (clan != null){
                    player.sendMessage(Help.color("&cKlan pod taka nazwa juz istnieje."));
                    return true;
                }

                boolean ok = true;
                if (warrior.getKills() >= ClansManager.getClanKills()){
                    if (!profile.take(ClansManager.getClanMoney())) ok = false;
                }
                else ok = false;

                if (!ok){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cAby zalozyc klan, potrzebujesz " + ClansManager.getClanMoney() + "TC &coraz musisz zabic" +
                            "&e " + ClansManager.getClanKills() + " graczy."));
                    return true;
                }

                clan = new Clan(name.toString().trim(), player.getName());

                ClansManager.register(clan);
                ClansManager.register(player, clan);
                ClansManager.save(clan);

                ChatUtils.sendMessage(player, ChatUtils.fixColor("&aKlan &e" + clan.getName() + " &azostal zalozony.\n" +
                        "&aKlanem zarzadzac mozesz pod komenda &e/klan."));
                return true;
            }
            else if (args[0].equalsIgnoreCase("dolacz")) {

                if (args.length < 2){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/klan dolacz <klan>"));
                    return true;
                }

                StringBuilder name = new StringBuilder();
                for (int i = 1; i < args.length ; i++){
                    name.append(args[i]);
                    name.append(' ');
                }

                clan = ClansManager.get(name.toString().trim());

                if (clan == null){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cKlan o takiej nazwie nie istnieje."));
                    return true;
                }

                if (clan.getOwner().equals(player.getName()) || clan.getPlayers().contains(player.getName())){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNalezysz juz do tego klanu."));
                    return true;
                }

                if (clan.getMembers() >= ClansManager.getClanMaxPlayers()){
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cKlan osiagnal max. liczbe graczy."));
                    return true;
                }

                if (ClansManager.acceptInvitation(player, clan)){
                    clan.announce("&aGracz &e" + player.getName() + "&a dolacza do klanu.", true);
                    clan.addPlayer(player);
                    ClansManager.register(player, clan);
                    ClansManager.save(clan);
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&aDolaczono do klanu &e" + clan.getName()));
                }
                else
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak zaproszenia do tego klanu."));

                return true;
            }

            else {
                ChatUtils.sendMessage(player, ChatUtils.fixColor("&cNieznana komenda klanow."));
                return true;
            }

        }

        boolean owner = clan.isOwner(player);

        if (args.length == 0){

            if (owner){
                player.sendMessage(ChatUtils.fixColor("&7Jestes wlascicielem klanu &e" + clan.getName() + ":"));
                player.sendMessage("");
                player.sendMessage(ChatUtils.fixColor("  &7Czlonkow: &e&l" + (clan.getPlayers().size() + 1) + "/5"));
                player.sendMessage(ChatUtils.fixColor("  &7Zabojstw: &e&l" + clan.getKills()));
                player.sendMessage(ChatUtils.fixColor("  &7Smierci: &e&l" + clan.getDeaths()));
                player.sendMessage("");
                player.sendMessage(ChatUtils.fixColor("&7Komendy:"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan lista"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan zapros <gracz>"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan wyrzuc <gracz>"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan msg <wiadomosc>"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan oddaj <nowy wlasciciel>"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan rozwiaz"));
                player.sendMessage("");
            }
            else {
                player.sendMessage(ChatUtils.fixColor("&7Nalezysz do klanu &e" + clan.getName() + ":"));
                player.sendMessage("");
                player.sendMessage(ChatUtils.fixColor("  &7Czlonkow: &e&l" + (clan.getPlayers().size() + 1) + "/5"));
                player.sendMessage(ChatUtils.fixColor("  &7Zabojstw: &e&l" + clan.getKills()));
                player.sendMessage(ChatUtils.fixColor("  &7Smierci: &e&l" + clan.getDeaths()));
                player.sendMessage("");
                player.sendMessage(ChatUtils.fixColor("&7Komendy:"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan lista"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan opusc"));
                player.sendMessage(ChatUtils.fixColor("  &e/klan msg <wiadomosc>"));
                player.sendMessage("");
            }

            return true;
        }
        else {

            if (owner) {

                if (args[0].equalsIgnoreCase("zapros")) {
                    if (args.length != 2){
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/klan zapros <gracz>"));
                        return true;
                    }

                    if (clan.getMembers() >= ClansManager.getClanMaxPlayers()){
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&cKlan osiagnal max. liczbe graczy " +
                                "&e(" + ClansManager.getClanMaxPlayers() + ")."));
                        return true;
                    }

                    Player invited = Bukkit.getPlayer(args[1]);
                    if (invited == null || !invited.isOnline()){
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&cGracz jest offline."));
                        return true;
                    }

                    if (ClansManager.invite(invited, clan)){
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&aZaproszono gracza &e" + args[1]));
                        ChatUtils.sendMessage(invited, ChatUtils.fixColor("&aOtrzymano zaproszenie do klanu &e" + clan.getName() + "\n" +
                                "&7Aby dolaczyc do tego klanu, wpisz &e/klan dolacz " + clan.getName()));
                    }
                    else
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&cZaproszono juz gracza do klanu."));

                    return true;
                }

                else if (args[0].equalsIgnoreCase("wyrzuc")) {

                    if (args.length != 2){
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/klan wyrzuc <gracz>"));
                        return true;
                    }

                    if (clan.kick(args[1])){
                        Player kicked = Bukkit.getPlayer(args[1]);
                        ClansManager.save(clan);
                        clan.announce("&aWyrzucono gracza &e" + args[1] + "&a z klanu.", true);
                        if (kicked == null || !kicked.isOnline()) return true;
                        ClansManager.unload(kicked);
                        ChatUtils.sendMessage(kicked, ChatUtils.fixColor("&cZostales wyrzucony z klanu &e" + clan.getName() + "."));
                    }
                    else
                        ChatUtils.sendMessage(player, ChatUtils.fixColor("&cBrak gracza w klanie."));

                    return true;

                }
                else if (args[0].equalsIgnoreCase("oddaj")) {

                    if (args.length != 2){
                        ChatUtils.sendMessage(sender, ChatUtils.fixColor("&eUzycie: &7/clan oddaj <nowy wlasciciel>"));
                        return true;
                    }

                    if (!clan.getPlayers().contains(args[1])){
                        ChatUtils.sendMessage(sender, ChatUtils.fixColor("&cGracz nie nalezy do klanu."));
                        return true;
                    }

                    clan.setOwner(args[1]);
                    clan.getPlayers().add(player.getName());
                    clan.getPlayers().remove(args[1]);
                    clan.announce("&aNowy wlasciciel klanu - &e" + args[1], true);

                    return true;

                }
                else if (args[0].equalsIgnoreCase("rozwiaz")) {
                    clan.announce("&cWlasiciel rozwiazuje gang...", true);
                    ClansManager.remove(clan);
                    return true;
                }

            }
            else {

                if (args[0].equalsIgnoreCase("opusc")) {
                    clan.removePlayer(player);
                    ClansManager.unload(player);
                    clan.announce("&cGracz &e" + player.getName() + " &copuszcza klan.", true);
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&aOpuszczono klan &e" + clan.getName()));
                }
            }

            if (args[0].equalsIgnoreCase("lista")) {
                player.sendMessage(ChatUtils.fixColor("&7Czlonkowie klanu &e" + clan.getName() + ":"));
                player.sendMessage("");
                player.sendMessage(ChatUtils.fixColor("  &7Wlasciciel: &e" + clan.getOwner()));
                player.sendMessage("");
                for (String mem : clan.getPlayers())
                    player.sendMessage(ChatUtils.fixColor("  &e- " + mem));
                player.sendMessage("");
            }

            if (args[0].equalsIgnoreCase("msg")) {

                if (args.length < 2) {
                    ChatUtils.sendMessage(player, ChatUtils.fixColor("&eUzycie: &7/klan msg <wiadomosc>"));
                    return true;
                }

                StringBuilder msg = new StringBuilder();
                for (int i = 1; i < args.length ; i++){
                    msg.append(args[i]);
                    msg.append(' ');
                }

                clan.announce("  &a&l[" + player.getName() + "] &a" + msg.toString().trim(), true);
            }
        }

        return true;
    }
}
