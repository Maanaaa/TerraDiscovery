package fr.mana.terradiscovery.loading;

import fr.mana.terradiscovery.*;

public class PluginInitializer {
    private TerraDiscovery main;
    public PluginInitializer(TerraDiscovery main) {
        this.main = main;
    }


    public void initialize(){
        main.saveDefaultConfig();
        DatabaseManager databaseManager = new DatabaseManager(main);

        databaseManager.connect();
    }



}
