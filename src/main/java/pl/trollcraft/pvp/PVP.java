package pl.trollcraft.pvp;

import org.bukkit.plugin.java.JavaPlugin;
import pl.trollcraft.pvp.antylogout.AntyLogout;
import pl.trollcraft.pvp.antylogout.AntyLogoutData;
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
import pl.trollcraft.pvp.data.*;
import pl.trollcraft.pvp.data.levels.LevelsDebugCommand;
import pl.trollcraft.pvp.data.levels.LevelsManager;
import pl.trollcraft.pvp.data.rewards.RewardsListener;
import pl.trollcraft.pvp.data.rewards.RewardsManager;
import pl.trollcraft.pvp.death.KillsManager;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.help.*;
import pl.trollcraft.pvp.death.DeathListener;
import pl.trollcraft.pvp.economy.EconomyListener;
import pl.trollcraft.pvp.economy.commands.EconomyCommand;
import pl.trollcraft.pvp.economy.commands.MoneyCommand;
import pl.trollcraft.pvp.economy.commands.PayCommand;
import pl.trollcraft.pvp.help.flying.FlyCommand;
import pl.trollcraft.pvp.help.flying.FlyingListener;
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
import pl.trollcraft.pvp.warehouses.WarehousesListener;
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

        getLogger().log(Level.INFO, "PVP is enabling, it may take up to few minutes.");

        getLogger().log(Level.INFO, "Loading config...");
        ConfigData.load();

        getLogger().log(Level.INFO, "Loading move detect...");
        MoveDetect.listen();
        DelayedWarp.listen();

        getLogger().log(Level.INFO, "Loading kits...");
        KitsManager.load();

        getLogger().log(Level.INFO, "Loading warps...");
        Warp.load();

        getLogger().log(Level.INFO, "Loading sign shops...");
        SignShopsManager.load();

        getLogger().log(Level.INFO, "Loading portals...");
        PortalsManager.load();

        PortalsHandler.listen();

        getLogger().log(Level.INFO, "Loading auto messages...");
        AutoMessages.init();

        getLogger().log(Level.INFO, "Loading levels data...");
        LevelsManager.load();

        getLogger().log(Level.INFO, "Loading clans...");
        ClansManager.load();

        KillsManager.listen();
        Booster.listen();

        getLogger().log(Level.INFO, "Loading blocked commands...");
        CmdManager.load();

        getLogger().log(Level.INFO, "Loading auctions...");
        AuctionManager.load();

        getLogger().log(Level.INFO, "Loading offline tasks...");
        OfflineTask.load();
        AuctionItemManager.startTicker();

        getLogger().log(Level.INFO, "Loading antylogout data...");
        AntyLogoutData.load();

        getLogger().log(Level.INFO, "Loading rewards...");
        RewardsManager.load();

        getLogger().log(Level.INFO, "PVP base loaded. Loading extras.");

        AntyLogout.newInstance(1000 * 15);
        killsRanking = new KillsRanking();
        economyRanking = new EconomyRanking();
        RankingManager.register(killsRanking);
        RankingManager.register(economyRanking);
        HoloRankingsManager.load();
        FlyingListener.listen();

        new PlaceholderManager().register();

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("kitadmin").setExecutor(new KitAdminCommand());
        getCommand("kitgive").setExecutor(new KitGiveCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("portal").setExecutor(new PortalSetupCommand());
        getCommand("signshop").setExecutor(new SignShopCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("clan").setExecutor(new ClansCommand());
        getCommand("clanadmin").setExecutor(new ClansAdminCommand());

        getCommand("rankingadm").setExecutor(new RankingCommand());
        getCommand("ranking").setExecutor(new pl.trollcraft.pvp.ranking.commands.RankingCommand());

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
        getCommand("dinfo").setExecutor(new PlotInfoCommand());
        getCommand("booster").setExecutor(new BoosterCommand());
        getCommand("boosteradmin").setExecutor(new BoosterAdminCommand());
        getCommand("cmd").setExecutor(new CmdsCommand());
        getCommand("incognito").setExecutor(new IncognitoCommand());
        getCommand("aukcja").setExecutor(new AuctionCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("fp").setExecutor(new LevelsDebugCommand());

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
        getServer().getPluginManager().registerEvents(new RewardsListener(), this);

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
