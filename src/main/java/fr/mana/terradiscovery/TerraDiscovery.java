package fr.mana.terradiscovery;

import fr.mana.terradiscovery.loading.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerraDiscovery extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginInitializer pluginInitializer = new PluginInitializer(this);
        pluginInitializer.initialize();
    }
}
