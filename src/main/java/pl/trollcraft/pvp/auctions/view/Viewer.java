package pl.trollcraft.pvp.auctions.view;

import org.bukkit.entity.Player;
import pl.trollcraft.pvp.help.gui.GUI;

public class Viewer {

    private Player player;
    private int page;
    private GUI gui;

    public Viewer (Player player, int page, GUI gui) {
        this.player = player;
        this.page = page;
        this.gui = gui;
    }

    public Player getPlayer() { return player; }
    public int getPage() { return page; }
    public GUI getGUI() { return gui; }

    public void setPage(int page) { this.page = page; }
}

