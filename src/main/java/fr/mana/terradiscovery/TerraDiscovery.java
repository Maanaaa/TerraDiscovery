package fr.mana.terradiscovery;

import fr.mana.terradiscovery.loading.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerraDiscovery extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginInitializer pluginInitializer = new PluginInitializer(this);
        pluginInitializer.initialize();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
