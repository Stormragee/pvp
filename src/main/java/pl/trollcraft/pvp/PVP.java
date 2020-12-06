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
import pl.trollcraft.pvp.data.rewards.user.RewardsUserCommand;
import pl.trollcraft.pvp.data.rewards.user.RewardsUserListener;
import pl.trollcraft.pvp.data.rewards.user.RewardsUsersManager;
import pl.trollcraft.pvp.death.KillsManager;
import pl.trollcraft.pvp.death.VoidListener;
import pl.trollcraft.pvp.economy.EconomyManager;
import pl.trollcraft.pvp.enchanting.EnchantingListener;
import pl.trollcraft.pvp.essentials.EnderchestCommand;
import pl.trollcraft.pvp.help.*;
import pl.trollcraft.pvp.death.DeathListener;
import pl.trollcraft.pvp.economy.EconomyListener;
import pl.trollcraft.pvp.economy.commands.EconomyCommand;
import pl.trollcraft.pvp.economy.commands.MoneyCommand;
import pl.trollcraft.pvp.economy.commands.PayCommand;
import pl.trollcraft.pvp.help.dropping.Drop;
import pl.trollcraft.pvp.help.flying.FlyCommand;
import pl.trollcraft.pvp.help.gui.InventoryListener;
import pl.trollcraft.pvp.help.move.MoveDetect;
import pl.trollcraft.pvp.help.tasks.OfflineTask;
import pl.trollcraft.pvp.help.tasks.OfflineTaskListener;
import pl.trollcraft.pvp.incognito.IncognitoCommand;
import pl.trollcraft.pvp.incognito.IncognitoData;
import pl.trollcraft.pvp.incognito.IncognitoListener;
import pl.trollcraft.pvp.incognito.TrueNickCommand;
import pl.trollcraft.pvp.kits.commands.KitAdminCommand;
import pl.trollcraft.pvp.kits.commands.KitCommand;
import pl.trollcraft.pvp.kits.KitListener;
import pl.trollcraft.pvp.kits.KitsManager;
import pl.trollcraft.pvp.kits.commands.KitGiveCommand;
import pl.trollcraft.pvp.plots.*;
import pl.trollcraft.pvp.ranking.commands.RankingCommand;
import pl.trollcraft.pvp.rankings.RankingListener;
import pl.trollcraft.pvp.rankings.holoranking.HoloRankingListener;
import pl.trollcraft.pvp.rankings.holoranking.HoloRankingsCommand;
import pl.trollcraft.pvp.rankings.RankingsManager;
import pl.trollcraft.pvp.rankings.commands.RankingDebugCommand;
import pl.trollcraft.pvp.rankings.holoranking.HoloRankingsManager;
import pl.trollcraft.pvp.rankings.kills.KillsRanking;
import pl.trollcraft.pvp.scoreboard.ScoreboardListener;
import pl.trollcraft.pvp.signshop.SignShopCommand;
import pl.trollcraft.pvp.signshop.SignShopsListener;
import pl.trollcraft.pvp.signshop.SignShopsManager;
import pl.trollcraft.pvp.teleport.RandomTeleportListener;
import pl.trollcraft.pvp.teleport.random.RandomTeleportsManager;
import pl.trollcraft.pvp.uniqueitems.EnchantRegister;
import pl.trollcraft.pvp.uniqueitems.UniqueItemsCommand;
import pl.trollcraft.pvp.uniqueitems.UniqueItemsController;
import pl.trollcraft.pvp.uniqueitems.UniqueItemsListener;
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

    private RankingsManager rankingsManager;

    private KillsRanking pointsRanking;

    private RandomTeleportsManager randomTeleportsManager;

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().log(Level.INFO, "PVP is enabling, it may take up to few minutes.");

        getLogger().log(Level.INFO, "Registering enchants...");
        EnchantRegister.register();

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

        getLogger().log(Level.INFO, "Loading incognito nicknames...");
        IncognitoData.load();

        getLogger().log(Level.INFO, "Loading unique items...");
        UniqueItemsController.load();

        getLogger().log(Level.INFO, "PVP base loaded. Loading extras.");

        Drop.init();

        // New ranking system
        rankingsManager = new RankingsManager();
        pointsRanking = new KillsRanking();
        rankingsManager.register(pointsRanking);
        HoloRankingsManager.load();

        AntyLogout.newInstance(1000 * 15);

        /*RankingManager.register(killsRanking);
        RankingManager.register(economyRanking);
        RankingManager.register(killStreakRanking);

        HoloRankingsManager.load();*/

        randomTeleportsManager = new RandomTeleportsManager();
        randomTeleportsManager.load();
        RandomTeleportListener.listen();

        new PlaceholderManager().register();

        VoidListener.listen();

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

        getCommand("ranking").setExecutor(new RankingCommand());

        getCommand("holoranking").setExecutor(new HoloRankingsCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("respond").setExecutor(new ResponseCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        getCommand("dzialki").setExecutor(new PlotsCommand());
        getCommand("plot").setExecutor(new PlotCommand());
        getCommand("dodaj").setExecutor(new AddToPlotCommand());
        getCommand("zaufaj").setExecutor(new AddTrustedCommand());
        getCommand("usun").setExecutor(new RemovePlayerCommand());
        getCommand("nieufaj").setExecutor(new UntrustPlayerCommand());
        getCommand("dinfo").setExecutor(new PlotInfoCommand());

        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("booster").setExecutor(new BoosterCommand());
        getCommand("boosteradmin").setExecutor(new BoosterAdminCommand());
        getCommand("cmd").setExecutor(new CmdsCommand());
        getCommand("incognito").setExecutor(new IncognitoCommand());
        getCommand("aukcja").setExecutor(new AuctionCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("fp").setExecutor(new LevelsDebugCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("truenick").setExecutor(new TrueNickCommand());

        getCommand("uniqueitems").setExecutor(new UniqueItemsCommand());
        getCommand("rewards").setExecutor(new RewardsUserCommand());

        getCommand("nranking").setExecutor(new RankingDebugCommand());

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
        getServer().getPluginManager().registerEvents(new UniqueItemsListener(), this);
        getServer().getPluginManager().registerEvents(new RewardsUserListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantingListener(), this);

        AntyLogoutListener antyLogoutListener = new AntyLogoutListener();
        antyLogoutListener.listen();
        antyLogoutListener.listenForMessage();

        getServer().getPluginManager().registerEvents(antyLogoutListener, this);

    }

    @Override
    public void onDisable() {
        EconomyManager.globalSave();
        ClansManager.globalSave();
        WarriorsManager.globalSave();
        AuctionManager.save();
        RewardsUsersManager.save();
    }

    public RankingsManager getRankingsManager() {
        return rankingsManager;
    }

    public RandomTeleportsManager getRandomTeleportsManager() {
        return randomTeleportsManager;
    }

    public static PVP getPlugin() { return plugin; }

}
