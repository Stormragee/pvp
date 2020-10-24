package pl.trollcraft.pvp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.trollcraft.pvp.antylogout.AntyLogout;
import pl.trollcraft.pvp.antylogout.AntyLogoutListener;
import pl.trollcraft.pvp.auctions.AuctionCommand;
import pl.trollcraft.pvp.auctions.AuctionManager;
import pl.trollcraft.pvp.auctions.items.AuctionItemManager;
import pl.trollcraft.pvp.auctions.view.AuctionViewListener;
import pl.trollcraft.pvp.boosters.Booster;
import pl.trollcraft.pvp.boosters.commands.BoosterAdminCommand;
import pl.trollcraft.pvp.boosters.commands.BoosterCommand;
import pl.trollcraft.pvp.chat.*;
import pl.trollcraft.pvp.chat.messages.MessageCommand;
import pl.trollcraft.pvp.chat.messages.ResponseCommand;
import pl.trollcraft.pvp.clans.commands.ClansAdminCommand;
import pl.trollcraft.pvp.clans.commands.ClansCommand;
import pl.trollcraft.pvp.clans.ClansListener;
import pl.trollcraft.pvp.clans.ClansManager;
import pl.trollcraft.pvp.cmds.CmdListener;
import pl.trollcraft.pvp.cmds.CmdManager;
import pl.trollcraft.pvp.cmds.CmdsCommand;
import pl.trollcraft.pvp.data.LevelsManager;
import pl.trollcraft.pvp.data.WarriorsManager;
import pl.trollcraft.pvp.death.KillsManager;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.help.*;
import pl.trollcraft.pvp.data.WarriorsListener;
import pl.trollcraft.pvp.death.DeathListener;
import pl.trollcraft.pvp.economy.EconomyListener;
import pl.trollcraft.pvp.economy.commands.EconomyCommand;
import pl.trollcraft.pvp.economy.commands.MoneyCommand;
import pl.trollcraft.pvp.economy.commands.PayCommand;
import pl.trollcraft.pvp.help.gui.InventoryListener;
import pl.trollcraft.pvp.help.move.MoveDetect;
import pl.trollcraft.pvp.help.tasks.OfflineTask;
import pl.trollcraft.pvp.help.tasks.OfflineTaskListener;
import pl.trollcraft.pvp.incognito.IncognitoCommand;
import pl.trollcraft.pvp.incognito.IncognitoListener;
import pl.trollcraft.pvp.kits.commands.KitAdminCommand;
import pl.trollcraft.pvp.kits.commands.KitCommand;
import pl.trollcraft.pvp.kits.KitListener;
import pl.trollcraft.pvp.kits.KitsManager;
import pl.trollcraft.pvp.kits.commands.KitGiveCommand;
import pl.trollcraft.pvp.plots.*;
import pl.trollcraft.pvp.ranking.RankingCommand;
import pl.trollcraft.pvp.ranking.RankingListener;
import pl.trollcraft.pvp.ranking.RankingManager;
import pl.trollcraft.pvp.ranking.economy.EconomyRanking;
import pl.trollcraft.pvp.ranking.holoranking.HoloRankingListener;
import pl.trollcraft.pvp.ranking.holoranking.HoloRankingsCommand;
import pl.trollcraft.pvp.ranking.holoranking.HoloRankingsManager;
import pl.trollcraft.pvp.ranking.kills.KillsRanking;
import pl.trollcraft.pvp.scoreboard.ScoreboardListener;
import pl.trollcraft.pvp.signshop.SignShopCommand;
import pl.trollcraft.pvp.signshop.SignShopsListener;
import pl.trollcraft.pvp.signshop.SignShopsManager;
import pl.trollcraft.pvp.warehouses.WarehouseCommand;
import pl.trollcraft.pvp.warehouses.WarehousesListener;
import pl.trollcraft.pvp.warehouses.WarehousesManager;
import pl.trollcraft.pvp.warp.*;
import pl.trollcraft.pvp.warp.commands.DelWarpCommand;
import pl.trollcraft.pvp.warp.commands.SetWarpCommand;
import pl.trollcraft.pvp.warp.commands.SpawnCommand;
import pl.trollcraft.pvp.warp.commands.WarpCommand;
import pl.trollcraft.pvp.warp.portals.PortalSetupCommand;
import pl.trollcraft.pvp.warp.portals.PortalsHandler;
import pl.trollcraft.pvp.warp.portals.PortalsManager;

import java.util.logging.Level;

public class PVP extends JavaPlugin {

    private static PVP plugin;

    private KillsRanking killsRanking;
    private EconomyRanking economyRanking;

    @Override
    public void onEnable() {
        plugin = this;
        saveConfig();

        getLogger().log(Level.INFO, "PVP is enabling, it may take up to few minutes.");

        MoveDetect.listen();
        DelayedWarp.listen();
        KitsManager.load();
        WarehousesManager.load();
        Warp.load();
        SignShopsManager.load();
        PortalsManager.load();
        PortalsHandler.listen();
        AutoMessages.init();
        LevelsManager.load();
        ClansManager.load();
        KillsManager.listen();
        Booster.listen();
        CmdManager.load();
        AuctionManager.load();
        OfflineTask.load();
        AuctionItemManager.startTicker();

        AntyLogout.newInstance(1000 * 15);
        killsRanking = new KillsRanking();
        economyRanking = new EconomyRanking();
        RankingManager.register(killsRanking);
        RankingManager.register(economyRanking);
        HoloRankingsManager.load();

        new PlaceholderManager().register();

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("kitadmin").setExecutor(new KitAdminCommand());
        getCommand("kitgive").setExecutor(new KitGiveCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        //getCommand("warehouse").setExecutor(new WarehouseCommand());
        getCommand("portal").setExecutor(new PortalSetupCommand());
        getCommand("signshop").setExecutor(new SignShopCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("clan").setExecutor(new ClansCommand());
        getCommand("clanadmin").setExecutor(new ClansAdminCommand());
        getCommand("ranking").setExecutor(new RankingCommand());
        getCommand("holoranking").setExecutor(new HoloRankingsCommand());
        getCommand("plot").setExecutor(new PlotCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("respond").setExecutor(new ResponseCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("dodaj").setExecutor(new AddToPlotCommand());
        getCommand("zaufaj").setExecutor(new AddTrustedCommand());
        getCommand("usun").setExecutor(new RemovePlayerCommand());
        getCommand("nieufaj").setExecutor(new UntrustPlayerCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("zablokuj").setExecutor(new LockCommand());
        getCommand("odblokuj").setExecutor(new UnlockCommand());
        getCommand("dzialki").setExecutor(new PlotsCommand());
        getCommand("dinfo").setExecutor(new PlotInfoCommand());
        getCommand("booster").setExecutor(new BoosterCommand());
        getCommand("boosteradmin").setExecutor(new BoosterAdminCommand());
        getCommand("cmd").setExecutor(new CmdsCommand());
        getCommand("incognito").setExecutor(new IncognitoCommand());
        getCommand("aukcja").setExecutor(new AuctionCommand());
        getCommand("test").setExecutor(new TestCommand());

        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new WarehousesListener(), this);
        getServer().getPluginManager().registerEvents(new SignShopsListener(), this);
        getServer().getPluginManager().registerEvents(new EconomyListener(), this);
        getServer().getPluginManager().registerEvents(new ChatManager(), this);
        getServer().getPluginManager().registerEvents(new WarriorsListener(), this);
        getServer().getPluginManager().registerEvents(new ClansListener(), this);
        getServer().getPluginManager().registerEvents(new KitListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new EnderchestFixer(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(), this);
        getServer().getPluginManager().registerEvents(new RankingListener(), this);
        getServer().getPluginManager().registerEvents(new HoloRankingListener(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        getServer().getPluginManager().registerEvents(new CmdListener(), this);
        getServer().getPluginManager().registerEvents(new IncognitoListener(), this);
        getServer().getPluginManager().registerEvents(new OfflineTaskListener(), this);
        getServer().getPluginManager().registerEvents(new AuctionViewListener(), this);

        AntyLogoutListener antyLogoutListener = new AntyLogoutListener();
        antyLogoutListener.listen();
        getServer().getPluginManager().registerEvents(antyLogoutListener, this);

    }

    @Override
    public void onDisable() {
        EconomyManager.globalSave();
        ClansManager.globalSave();
        WarriorsManager.globalSave();
        AuctionManager.save();
    }

    public KillsRanking getKillsRanking() { return killsRanking; }
    public EconomyRanking getEconomyRanking() { return economyRanking; }

    public static PVP getPlugin() { return plugin; }

}
