package fr.mana.terradiscovery.events;

import fr.mana.terradiscovery.TerraDiscovery;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.*;

import java.util.*;

public class WarpTeleportationListener implements Listener {
    private TerraDiscovery main;

    public WarpTeleportationListener(TerraDiscovery main) {
        this.main = main;
    }

    private final Map<Player, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("§b§lMenu des zones")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            ItemMeta itemMeta = clickedItem.getItemMeta();
            if (itemMeta == null || !itemMeta.hasDisplayName()) {
                return;
            }

            if (!(clickedItem.getType().equals(Material.COMPASS))) {
                if (clickedItem.getType().equals(Material.RED_STAINED_GLASS)) {
                    player.closeInventory();
                    List<String> lines = main.getConfig().getStringList("messages.warps.locked");
                    for (String line : lines){
                        player.sendMessage(line);
                    }
                    return;
                }
                return;
            }

            String warpName = getWarpNameFromItem(clickedItem);
            String itemName = itemMeta.getDisplayName();
            System.out.println("Nom de l'item : " + itemName);
            System.out.println("Nom du warp : " + warpName);
            if (warpName != null) {
                teleportPlayerToWarp(player, warpName);
            }
        }
    }

    private String getWarpNameFromItem(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            String itemName = ChatColor.stripColor(itemMeta.getDisplayName());
            ConfigurationSection warpsSection = main.getConfig().getConfigurationSection("warps");
            if (warpsSection != null) {
                for (String warpName : warpsSection.getKeys(false)) {
                    String displayName = main.getConfig().getString("warps." + warpName + ".display-name");
                    if (displayName != null && ChatColor.stripColor(displayName).equals(itemName)) {
                        return warpName;
                    }
                }
            }
        }
        return null;
    }


    public void teleportPlayerToWarp(Player player, String warpName){

        player.closeInventory();
        String displayName = main.getConfig().getString("warps." + warpName + ".display-name");

        if (!(player.hasPermission("terradiscovery.bypass"))) {
            player.sendMessage("§bTéléportation vers la zone §7" + displayName + " §bdans 5 secondes ! §f§o(/credit pour passer outre le temps d'attente)");
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnline()) {
                        double x = main.getConfig().getDouble("warps." + warpName + ".x");
                        double y = main.getConfig().getDouble("warps." + warpName + ".y");
                        double z = main.getConfig().getDouble("warps." + warpName + ".z");
                        float yaw = main.getConfig().getInt("warps." + warpName + ".yaw");
                        float pitch = main.getConfig().getInt("warps." + warpName + ".pitch");

                        player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
                        player.sendMessage("§aTéléportation réussie vers la zone §7" + displayName);
                    }
                }
            }.runTaskLater(main, main.getConfig().getInt("warps."+warpName+".cooldown") * 20L);
        } else {
            double x = main.getConfig().getDouble("warps." + warpName + ".x");
            double y = main.getConfig().getDouble("warps." + warpName + ".y");
            double z = main.getConfig().getDouble("warps." + warpName + ".z");
            float yaw = main.getConfig().getInt("warps." + warpName + ".yaw");
            float pitch = main.getConfig().getInt("warps." + warpName + ".pitch");

            player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
            player.sendMessage("§aTéléportation réussie vers la zone §7" + displayName);
        }
    }
}
