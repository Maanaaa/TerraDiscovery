package fr.mana.terradiscovery.loading;

import fr.mana.terradiscovery.*;
import fr.mana.terradiscovery.commands.*;
import fr.mana.terradiscovery.events.*;

import java.util.*;

public class PluginInitializer {
    private TerraDiscovery main;
    public PluginInitializer(TerraDiscovery main) {
        this.main = main;
    }


    public void initialize(){
        main.saveDefaultConfig();
        Objects.requireNonNull(main.getCommand("zones")).setExecutor(new Warps(main));
        Objects.requireNonNull(main.getCommand("terradiscoveryadmin")).setExecutor(new Discovery(main));
        main.getServer().getPluginManager().registerEvents(new WarpUnlockListener(main), main);
        main.getServer().getPluginManager().registerEvents(new WarpTeleportationListener(main), main);
    }
}